
/**
 * Project Name:summit-srv-demo 
 * File Name:EntitlementServiceImpl.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 26, 2018 4:17:13 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sap.summit.demo.dto.DropDown;
import com.sap.summit.demo.dto.EntitlementDto;
import com.sap.summit.demo.dto.SearchCriteria;
import com.sap.summit.demo.dto.SearchFilter;
import com.sap.summit.demo.dto.TransactionDto;
import com.sap.summit.demo.model.CompanyModel;
import com.sap.summit.demo.model.Entitlement;
import com.sap.summit.demo.repositroy.DemoRepository;
import com.sap.summit.demo.repositroy.EntitlementRepository;
import com.sap.summit.demo.service.EntitlementService;

/**
 * ClassName: EntitlementServiceImpl <br/>
 * <br/>
 * Description: TODO
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Service
@Transactional
public class EntitlementServiceImpl implements EntitlementService
{
    @Autowired
    private EntitlementRepository entitlementRepository;

    @Autowired
    private DemoRepository companyRepo;

    @Autowired
    private RestTemplate restTemplate;

    //    @Value("${blockchain.api.host}")
    //    private String host;
    String host = "https://hyperledger-fabric.cfapps.sap.hana.ondemand.com/api/v1/chaincodes/f3758a10-4e9d-4d9d-9f34-5c7ddccb742f-com-sap-ems-emsDemo/latest";

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#publish(com.sap.summit.demo.dto.EntitlementDto)
     */
    @Override
    public EntitlementDto publish(EntitlementDto entitlementDto)
    {
        Entitlement entitlement = entitlementRepository.findOne(entitlementDto.getEntitlementGuid());
        entitlement.setStatus("active");
        String url = host + "/createEntitlement/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("guid", entitlementDto.getEntitlementGuid());
        params.add("entitlementno", entitlement.getEntitlementNo());
        params.add("quantity", entitlement.getQuantity());
        params.add("validFrom", entitlement.getValidFrom());
        params.add("validTo", entitlement.getValidTo());
        params.add("status", entitlement.getStatus());
        params.add("customerID", entitlement.getCustomerId());
        // Newly Added
        params.add("keyValue", entitlement.getKeyValue());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.POST, requestEntity,
            String.class);
        if (response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is5xxServerError())
        {
            entitlementRepository.save(entitlement);
        }
        return new EntitlementDto(entitlement);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#transaction(com.sap.summit.demo.dto.EntitlementDto)
     */
    @Override
    public List<EntitlementDto> transaction(EntitlementDto entitlementDto, String type)
    {
        List<EntitlementDto> result = new ArrayList<>();

        if (validateEntitlement(entitlementDto))
        {
            String guid = entitlementDto.getEntitlementGuid();
            Entitlement oldEntitlement = entitlementRepository.findOne(guid);
            String assginCompanyUrl = host + "/assignEntitlement/";
            String buyEntitlementUrl = host + "/buyEntitlement/";
            String url = null;

            TransactionDto dto = new TransactionDto();
            dto.setNewEntitlementGuid(UUID.randomUUID().toString().replace("-", ""));
            dto.setOldEntitlementGuid(guid);
            dto.setUserId(entitlementDto.getOwnedById());
            dto.setQuantity(entitlementDto.getQuantity());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("oldEntitlementGUID", dto.getOldEntitlementGuid());
            params.add("newEntitlementGUID", dto.getNewEntitlementGuid());

            String newEntitlementNo = String.valueOf(Instant.now().toEpochMilli() / 1000);
            params.add("newEntitlementNo", newEntitlementNo);

            if ("company".equals(type))
            {
                url = assginCompanyUrl;
                params.add("company", dto.getUserId());
            }
            else
            {
                url = buyEntitlementUrl;
                params.add("user", dto.getUserId());
            }
            params.add("quantity", dto.getQuantity());
            // Newly Added
            params.add("keyValue", entitlementDto.getKeyValue());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toUri(), HttpMethod.PUT,
                requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is5xxServerError())
            {
                Entitlement newEntitlement = convertEntitlement(new EntitlementDto(oldEntitlement));
                newEntitlement.setEntitlementGuid(dto.getNewEntitlementGuid());
                newEntitlement.setQuantity(entitlementDto.getQuantity());
                newEntitlement.setOwnedById(dto.getUserId());
                newEntitlement.setEntitlementNo(newEntitlementNo);
                newEntitlement.setDescription(oldEntitlement.getDescription());
                newEntitlement.setId(oldEntitlement.getId() + "_" + newEntitlement.getOwnedById());
                // Newly Added 
                newEntitlement.setKeyValue(entitlementDto.getKeyValue());

                Double oldQuantity = Double.parseDouble(oldEntitlement.getQuantity()) - Double.parseDouble(dto.getQuantity());
                String quantityString = oldQuantity.toString().endsWith(".0") ? oldQuantity.toString().replace(".0", "") : oldQuantity.toString();
                oldEntitlement.setQuantity(quantityString);
                entitlementRepository.save(newEntitlement);
                entitlementRepository.save(oldEntitlement);

                result.add(new EntitlementDto(oldEntitlement));
                result.add(new EntitlementDto(newEntitlement));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#getTransactionDetail()
     */
    @Override
    public List<TransactionDto> getTransactionDetail(String guid)
    {
        List<TransactionDto> results = new ArrayList<>();
        String url = host + "/transactions/" + guid;
        ResponseEntity<String> response = restTemplate
            .exchange(new RequestEntity<Object>(HttpMethod.GET, UriComponentsBuilder.fromHttpUrl(url).build().toUri()), String.class);
        if (response.getStatusCode().is2xxSuccessful())
        {
            String body = response.getBody().toString();
            JSONArray objects = JSONArray.parseArray(body);
            if (objects.size() > 0)
            {
                for (int i = 0; i < objects.size(); i++)
                {
                    JSONObject object = objects.getJSONObject(i);
                    results.add(convertTransactionDtoFromBlockChain(object.getJSONObject("Record")));
                }
            }
        }

        return results;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#validateEntitlement(com.sap.summit.demo.model.Entitlement)
     */
    @Override
    public boolean validateEntitlement(EntitlementDto entitlementDto)
    {
        String guid = entitlementDto.getEntitlementGuid();
        Entitlement entitlement = entitlementRepository.findOne(guid);

        //TODO call api
        if (null == entitlement || StringUtils.isEmpty(entitlement.getQuantity()))
        {
            return false;
        }
        return true;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#validateBilateralEntitlement(com.sap.summit.demo.model.Entitlement)
     */
    @Override
    public boolean validateBilateralEntitlement(EntitlementDto entitlementDto)
    {
    	String guid = entitlementDto.getEntitlementGuid();
    	Entitlement DBEntitlement = entitlementRepository.findOne(guid);
    	String DBId = DBEntitlement.getId();
    	String DBKeyValue = DBEntitlement.getKeyValue();
    	// convert it to JSON string for further possible use, such as frontend callback, encapsulated into an object, etc
    	String DBJSONFormat = "{\"id\"：" + DBId +"," + "\"keyValue\"：" + DBKeyValue +"}";
    	
    	List<Entitlement> entitlements = new ArrayList<>();
        String url = host + "/entitlement/" + guid;	
    	RequestEntity<Object> requestEntity = new RequestEntity<Object>(HttpMethod.GET, UriComponentsBuilder.fromHttpUrl(url).build().toUri());
    	ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful())
        {
            String body = response.getBody().toString();
            JSONArray objects = JSONArray.parseArray(body);
            if (objects.size() > 0)
            {
                for (int i = 0; i < objects.size(); i++)
                {
                    JSONObject object = objects.getJSONObject(i);
                    entitlements.add(convertEntitlementFromBlockChain(object.getJSONObject("Record")));
                }
            }
        }
        Entitlement chainEntitlement = entitlements.get(0);
        String chainid = chainEntitlement.getId();
    	String chainkeyValue = chainEntitlement.getKeyValue();
    	String chainJSONFormat = "{\"id\"：" + chainid +"," + "\"keyValue\"：" + chainkeyValue +"}";
    	// if there is entitlements-to-yourself(change) info in the callback message, we're able to track up to the latest transaction to verify the quantity
    	// check the callback info by calling API on Swagger to verify what exactly does the chain API offer to backend
    	
    	if(chainJSONFormat == DBJSONFormat)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#create(com.sap.summit.demo.model.Entitlement)
     */
    @Override
    public EntitlementDto create(EntitlementDto entitlementDto)
    {
        entitlementDto.setStatus("inactive");
        entitlementDto.setEntitlementGuid(UUID.randomUUID().toString().replace("-", ""));
        entitlementDto.setEntitlementNo(String.valueOf(Instant.now().toEpochMilli() / 1000));
        entitlementDto.setOwnedById("SAP");
        entitlementDto.setCustomerId(entitlementDto.getOwnedById());
        Entitlement entitlement = convertEntitlement(entitlementDto);
        entitlementRepository.save(entitlement);

        return new EntitlementDto(entitlement);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#update(com.sap.summit.demo.model.Entitlement)
     */
    @Override
    public EntitlementDto update(EntitlementDto entitlementDto)
    {
        Entitlement entitlement = convertEntitlement(entitlementDto);

        entitlementRepository.save(entitlement);
        return entitlementDto;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#get(java.lang.String)
     */
    @Override
    public EntitlementDto get(String guid)
    {
        return new EntitlementDto(entitlementRepository.findOne(guid));
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#delete(java.lang.String)
     */
    @Override
    public EntitlementDto delete(String guid)
    {
        Entitlement entitlement = entitlementRepository.findOne(guid);
        entitlementRepository.delete(entitlement);
        return new EntitlementDto(entitlement);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#getAll()
     */
    @Override
    public List<EntitlementDto> getAll(SearchCriteria searchFilter)
    {
        List<EntitlementDto> result = new ArrayList<>();
        List<Entitlement> entitlements = new ArrayList<>();
        Map<String, String> criteria = new HashMap<>();
        List<SearchFilter> filters = searchFilter.getFilters();
        if (CollectionUtils.isNotEmpty(filters))
        {
            for (SearchFilter filter : filters)
            {
                if (StringUtils.isNotEmpty(filter.getKey()))
                {
                    criteria.put(filter.getKey(), filter.getValue());
                }
            }
        }

        Set<String> keySets = criteria.keySet();
        if (keySets.contains("ownedById") && keySets.contains("customerId"))
        {
            //TODO
            entitlements.addAll(entitlementRepository.findByCustomerIdAndOwnedById(criteria.get("customerId"), criteria.get("ownedById")));
        }
        else if (keySets.contains("ownedById") && StringUtils.isNotEmpty(criteria.get("ownedById")))
        {
            String value = criteria.get("ownedById");
            entitlements.addAll(entitlementRepository.findByOwnedById(value));
        }
        else if (keySets.contains("customerId") && StringUtils.isNotEmpty(criteria.get("customerId")))
        {
            String value = criteria.get("customerId");
            entitlements.addAll(entitlementRepository.findByCustomerId(value));
        }
        else
        {
            Set<String> companies = getCompanies().stream().map(DropDown::getKey).collect(Collectors.toSet());
            List<Entitlement> allEntitlements = entitlementRepository.findAll();
            List<Entitlement> companyEntitlements = allEntitlements.stream().filter(entitlement -> companies.contains(entitlement.getOwnedById()))
                .collect(Collectors.toList());

            if ("user".equals(searchFilter.getType()))
            {
                allEntitlements.removeAll(companyEntitlements);
                entitlements.addAll(allEntitlements);
            }
            else if ("company".equals(searchFilter.getType()))
            {
                entitlements.addAll(companyEntitlements);
            }
            else
            {
                List<Entitlement> results = allEntitlements.stream().filter(entl -> "active".equals(entl.getStatus())).collect(Collectors.toList());
                entitlements.addAll(results);
            }

        }

        entitlements.stream().forEach(entitlement -> {
            result.add(new EntitlementDto(entitlement));
        });
        return result;
    }

    private List<EntitlementDto> getEntitlementsFromBlockChain(String url)
    {
        List<Entitlement> entitlements = new ArrayList<>();
        String body = restTemplate
            .exchange(new RequestEntity<Object>(HttpMethod.GET, UriComponentsBuilder.fromHttpUrl(url).build().toUri()), String.class).getBody()
            .toString();
        JSONArray objects = JSONArray.parseArray(body);
        if (objects.size() > 0)
        {
            for (int i = 0; i < objects.size(); i++)
            {
                JSONObject object = objects.getJSONObject(i);
                entitlements.add(convertEntitlementFromBlockChain(object.getJSONObject("Record")));
            }
        }
        List<EntitlementDto> result = new ArrayList<>();
        entitlements.stream().forEach(entitlement -> {
            result.add(new EntitlementDto(entitlement));
        });
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#getCompanies()
     */
    @Override
    public List<DropDown> getCompanies()
    {
        List<CompanyModel> companies = companyRepo.findAll();
        List<DropDown> result = new ArrayList<>();
        companies.stream().forEach(company -> {
            DropDown dropDown = new DropDown();
            dropDown.setKey(company.getId());
            dropDown.setText(company.getName());
            result.add(dropDown);
        });
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#getUsers()
     */
    @Override
    public List<DropDown> getUsers()
    {
        Set<String> allOwners = entitlementRepository.findAll().stream().map(Entitlement::getOwnedById).collect(Collectors.toSet());
        Set<String> companies = getCompanies().stream().map(DropDown::getKey).collect(Collectors.toSet());
        Set<String> users = allOwners.stream().filter(owner -> !companies.contains(owner)).collect(Collectors.toSet());
        List<DropDown> result = new ArrayList<>();
        users.stream().forEach(user -> {
            DropDown dropDown = new DropDown();
            dropDown.setKey(user);
            dropDown.setText(user);
            result.add(dropDown);
        });
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#deleteById(java.lang.String)
     */
    @Override
    public List<EntitlementDto> deleteById(String id)
    {
        List<Entitlement> entitlements = entitlementRepository.findById(id);
        List<EntitlementDto> result = new ArrayList<>();
        entitlements.stream().forEach(entitlement -> {
            entitlementRepository.delete(entitlement);
            result.add(new EntitlementDto(entitlement));
        });
        return result;
    }

    private Entitlement convertEntitlement(EntitlementDto entitlementDto)
    {
        Entitlement newEntitlement = new Entitlement();
        newEntitlement.setEntitlementGuid(entitlementDto.getEntitlementGuid());
        newEntitlement.setId(entitlementDto.getId());
        newEntitlement.setCustomerId(entitlementDto.getCustomerId());
        newEntitlement.setEntitlementNo(entitlementDto.getEntitlementNo());
        newEntitlement.setOwnedById(entitlementDto.getOwnedById());
        newEntitlement.setQuantity(entitlementDto.getQuantity());
        newEntitlement.setStatus(entitlementDto.getStatus());
        newEntitlement.setValidFrom(entitlementDto.getValidFrom());
        newEntitlement.setValidTo(entitlementDto.getValidTo());
        newEntitlement.setDescription(entitlementDto.getDescription());
        // Newly Added
        newEntitlement.setKeyValue(entitlementDto.getKeyValue());
        return newEntitlement;
    }

    private Entitlement convertEntitlementFromBlockChain(JSONObject object)
    {
        Entitlement entitlement = new Entitlement();
        entitlement.setCustomerId(removeOwnedByIdPrefix(object.getString("CustomerID")));

        String guid = object.getString("EntitlementGUID");
        if (guid.startsWith("Entitlement:"))
        {
            guid = guid.substring(12);
        }
        entitlement.setEntitlementGuid(guid);
        entitlement.setEntitlementNo(object.getString("EntitlementNo"));

        entitlement.setOwnedById(removeOwnedByIdPrefix(object.getString("OwnedByID")));
        entitlement.setQuantity(String.valueOf(object.get("Quantity")));
        entitlement.setStatus(object.getString("Status"));
        entitlement.setValidFrom(object.getString("ValidFrom"));
        entitlement.setValidTo(object.getString("ValidTo"));
        return entitlement;
    }

    private TransactionDto convertTransactionDtoFromBlockChain(JSONObject object)
    {
        TransactionDto dto = new TransactionDto();
        dto.setNewEntitlementGuid(object.getString("NewEntitlementGUID"));
        Entitlement newEntitlement = entitlementRepository.findOne(dto.getNewEntitlementGuid());
        if (null != newEntitlement)
        {
            dto.setNewOwnedBy(newEntitlement.getOwnedById());
        }

        dto.setOldEntitlementGuid(object.getString("OldEntitlementGUID"));
        Entitlement oldEntitlement = entitlementRepository.findOne(dto.getOldEntitlementGuid());
        if (null != oldEntitlement)
        {
            dto.setOldOwnedBy(oldEntitlement.getOwnedById());
            dto.setCustomerId(oldEntitlement.getCustomerId());
            dto.setEntitlementNo(oldEntitlement.getEntitlementNo());
        }

        dto.setQuantity(String.valueOf(object.get("Quantity")));
        dto.setType(object.getString("Type"));
        dto.setUserId(object.getString("UserId"));
        return dto;
    }

    private String removeOwnedByIdPrefix(String ownedById)
    {
        if (ownedById.startsWith("User:"))
        {
            ownedById = ownedById.substring(5);
        }
        else if (ownedById.startsWith("Company:"))
        {
            ownedById = ownedById.substring(8);
        }
        return ownedById;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.EntitlementService#testBlockChainApi(java.lang.String)
     */
    @Override
    public List<EntitlementDto> testBlockChainApi(String url)
    {
        return getEntitlementsFromBlockChain(host + "/" + url + "/");
    }
}
