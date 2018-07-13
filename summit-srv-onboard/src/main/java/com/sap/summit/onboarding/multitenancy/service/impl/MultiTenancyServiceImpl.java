
/**
 * Project Name:com.sap.summit.onboarding.callback 
 * File Name:MultiTenancyServiceImpl.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.onboarding.multitenancy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sap.summit.exception.BaseRuntimeException;
import com.sap.summit.message.ApiMessage;
import com.sap.summit.message.MessageType;
import com.sap.summit.multitenancy.InstanceUtil;
import com.sap.summit.multitenancy.exception.InstanceManagerInitException;
import com.sap.summit.onboarding.multitenancy.dto.CreateMultiTenancyDto;
import com.sap.summit.onboarding.multitenancy.dto.InstanceDto;
import com.sap.summit.onboarding.multitenancy.feign.DeployDBClient;
import com.sap.summit.onboarding.multitenancy.service.MultiTenancyService;
import com.sap.summit.onboarding.multitenancy.util.DeployDBJSONUtil;
import com.sap.summit.onboarding.multitenancy.util.MessageUtil;
import com.sap.xsa.core.instancemanager.client.ImClientException;
import com.sap.xsa.core.instancemanager.client.InstanceCreationOptions;
import com.sap.xsa.core.instancemanager.client.ManagedServiceInstance;


/**
 * ClassName: MultiTenancyServiceImpl <br/>
 * <br/>
 * Description: MultiTenancyServiceImpl
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Service
public class MultiTenancyServiceImpl implements MultiTenancyService
{

    @Autowired
    private DeployDBClient deployDBClient;
    //    @Autowired
    //    private MultiTenancyCustomerRepository customerRepository;

    /**
     * {@inheritDoc}
     * 
     * @throws InstanceManagerException
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#create(com.sap.csc.ems.onboarding.multitenancy.dto.CreateMultiTenancyDto)
     */
    @Override
    public ApiMessage<InstanceDto> createWithParamter(String tenantID, CreateMultiTenancyDto cMtDto) throws InstanceManagerInitException
    {
        Map<String, Object> provisioningParameters = new HashMap<>();
        provisioningParameters.put("database_id", cMtDto.getDataBase());
        InstanceCreationOptions creationOptions = new InstanceCreationOptions();
        creationOptions.withProvisioningParameters(provisioningParameters);
        try
        {
            ManagedServiceInstance msInstance = InstanceUtil.createManagedInstance(tenantID, creationOptions, 5000);
            ResponseEntity<String> res = deployDBClient.deployDB(DeployDBJSONUtil.generateDeployDBBody(msInstance.getCredentials()));
            if (!(res.getBody().contains("\"exitCode\": 0")))
            {
                throw new BaseRuntimeException("deploy failed ,please contact the administrator.");
            }
            ApiMessage<InstanceDto> apiMessage = MessageUtil.getSuccessMessage(new InstanceDto(msInstance));
            return apiMessage;
        }
        catch (ImClientException | ClassNotFoundException e)
        {
            throw new InstanceManagerInitException("createManagedInstance error.", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InstanceManagerException
     * 
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#delete(java.lang.String)
     */
    @Override
    public ApiMessage<InstanceDto> delete(String token) throws InstanceManagerInitException
    {
        try
        {
            ManagedServiceInstance msi = InstanceUtil.getManagedServiceInstance(token, true);
            if (msi == null)
            {
                throw new InstanceManagerInitException("instance don't exist.");
            }
            InstanceUtil.deleteManagedInstance(token);
            ApiMessage<InstanceDto> apiMessage = MessageUtil.getSuccessMessage(new InstanceDto(msi));
            return apiMessage;
        }
        catch (ClassNotFoundException | ImClientException | InstanceManagerInitException e)
        {
            throw new InstanceManagerInitException("delete InstanceManagerClient error : ", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InstanceManagerException
     * 
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#read(java.lang.String)
     */
    @Override
    public ApiMessage<InstanceDto> read(String token) throws InstanceManagerInitException
    {
        try
        {
            ManagedServiceInstance msi = InstanceUtil.getManagedServiceInstance(token, true);
            if (msi == null)
            {
                throw new InstanceManagerInitException("get instance don't exsit!");
            }
            ApiMessage<InstanceDto> apiMessage = MessageUtil.getSuccessMessage(new InstanceDto(msi));
            return apiMessage;
        }
        catch (ClassNotFoundException | ImClientException | InstanceManagerInitException e)
        {
            throw new InstanceManagerInitException("get instance error : ", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws InstanceManagerInitException
     * 
     * @throws InstanceManagerException
     * 
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#getAll()
     */
    @Override
    public ApiMessage<List<InstanceDto>> getAll() throws InstanceManagerInitException
    {
        try
        {
            List<ManagedServiceInstance> msis = InstanceUtil.getManagedInstances();
            List<InstanceDto> result = new ArrayList<>();
            for (ManagedServiceInstance m : msis)
            {
                result.add(new InstanceDto(m));
            }
            ApiMessage<List<InstanceDto>> apiMessage = MessageUtil.getSuccessMessage(result);
            return apiMessage;
        }
        catch (ClassNotFoundException | ImClientException | InstanceManagerInitException e)
        {
            throw new InstanceManagerInitException("get instance error : ", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#update()
     */
    @Override
    public ApiMessage<Map<String, List<InstanceDto>>> update() throws InstanceManagerInitException
    {
        Map<String, List<InstanceDto>> result = new HashMap<>();
        List<InstanceDto> success = new ArrayList<>();
        List<InstanceDto> error = new ArrayList<>();
        List<ManagedServiceInstance> msis = null;
        try
        {
            msis = InstanceUtil.getManagedInstances();
        }
        catch (Exception e)
        {
            throw new InstanceManagerInitException("update tenantDB failed in getManagedInstances : ", e);
        }
        for (ManagedServiceInstance m : msis)
        {
            try
            {
                ResponseEntity<String> res = deployDBClient.deployDB(DeployDBJSONUtil.generateDeployDBBody(m.getCredentials()));
                if (!(res.getBody().contains("\"exitCode\": 0")))
                {
                    throw new BaseRuntimeException("deploy failed ,please contact the administrator.");
                }
                success.add(new InstanceDto(m));
            }
            catch (Exception e)
            {
                InstanceDto instanceDto = new InstanceDto(m);
                instanceDto.setDeployFailedMessage(e.getMessage());
                error.add(instanceDto);
            }
        }
        result.put("SUCCESS", success);
        result.put("ERROR", error);
        ApiMessage<Map<String, List<InstanceDto>>> apiMessage;
        if (!error.isEmpty())
        {
            apiMessage = MessageUtil.constructApiMsg(result, MessageUtil.getI18NMsgText("EMS_CRUD_ERROR_MESSAGE"), MessageType.E);
        }
        else
        {
            apiMessage = MessageUtil.getSuccessMessage(result);
        }

        return apiMessage;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#update(java.lang.String)
     */
    @Override
    public ApiMessage<InstanceDto> update(String token) throws InstanceManagerInitException
    {
        ManagedServiceInstance msi = null;
        try
        {
            msi = InstanceUtil.getManagedServiceInstance(token, true);
        }
        catch (Exception e)
        {
            throw new InstanceManagerInitException("get instance don't exsit!");
        }

        ResponseEntity<String> res = deployDBClient.deployDB(DeployDBJSONUtil.generateDeployDBBody(msi.getCredentials()));
        if (!(res.getBody().contains("\"exitCode\": 0")))
        {
            throw new BaseRuntimeException("update failed ,please contact the administrator.");
        }

        ApiMessage<InstanceDto> apiMessage = MessageUtil.getSuccessMessage(new InstanceDto(msi));
        return apiMessage;

    }

    //    /**
    //     * {@inheritDoc}
    //     * 
    //     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#create()
    //     */
    //    @Override
    //    public ApiMessage<List<InstanceDto>> create() throws InstanceManagerInitException
    //    {
    //        List<InstanceDto> result = new ArrayList<>();
    //        //get all tenant in instanceManager
    //        List<ManagedServiceInstance> msis = null;
    //        //created tenant this time
    //        List<ManagedServiceInstance> created = new ArrayList<>();
    //        try
    //        {
    //            msis = InstanceUtil.getManagedInstances();
    //        }
    //        catch (Exception e)
    //        {
    //            throw new InstanceManagerInitException("getManagedInstances() error : ", e);
    //        }
    //
    //        //get all not deploy tenant in DB 
    //        List<TenantDBCustomer> allCustomerInDB = customerRepository.findByStatus(MultiTenancyServiceConstants.DEPLOY_NOT);
    //        Set<String> msisName = new HashSet<>();
    //        //add all tenant of instanceManager 
    //        for (ManagedServiceInstance msi : msis)
    //        {
    //            msisName.add(msi.getId());
    //        }
    //        //if tenant in DB but not in instanceManager , create tenant in instanceManager
    //        for (TenantDBCustomer t : allCustomerInDB)
    //        {
    //            if (!msisName.contains(t.getTenantID()))
    //            {
    //                Map<String, Object> provisioningParameters = new HashMap<>();
    //                provisioningParameters.put("database_id", t.getDbID());
    //                InstanceCreationOptions creationOptions = new InstanceCreationOptions();
    //                creationOptions.withProvisioningParameters(provisioningParameters);
    //                try
    //                {
    //                    ManagedServiceInstance msInstance = InstanceUtil.createManagedInstance(t.getTenantID(), creationOptions, 5000);
    //                    created.add(msInstance);
    //                }
    //                catch (Exception e)
    //                {
    //                    throw new InstanceManagerInitException("createManagedInstance error : ", e);
    //                }
    //            }
    //        }
    //
    //        //deploy BD for created tenant
    //        for (ManagedServiceInstance instance : created)
    //        {
    //            try
    //            {
    //                deployDBClient.deployDB(DeployDBJSONUtil.generateDeployDBBody(instance.getCredentials()));
    //                TenantDBCustomer tenantDBCustomer = customerRepository.findOne(instance.getId());
    //                tenantDBCustomer.setStatus(MultiTenancyServiceConstants.DEPLOY_SUCCESS);
    //                customerRepository.save(tenantDBCustomer);
    //                result.add(new InstanceDto(instance));
    //            }
    //            catch (Exception e)
    //            {
    //                TenantDBCustomer tenantDBCustomer = customerRepository.findOne(instance.getId());
    //                tenantDBCustomer.setStatus(MultiTenancyServiceConstants.DEPLOY_ERROR);
    //                customerRepository.save(tenantDBCustomer);
    //            }
    //        }
    //        ApiMessage<List<InstanceDto>> apiMessage = MessageUtil.getSuccessMessage(result);
    //        return apiMessage;
    //    }
    //    /**
    //     * {@inheritDoc}
    //     * 
    //     * @see com.sap.csc.ems.onboarding.multitenancy.service.MultiTenancyService#update()
    //     */
    //    @Override
    //    public ApiMessage<List<InstanceDto>> update() throws InstanceManagerInitException
    //    {
    //        List<InstanceDto> result = new ArrayList<>();
    //        List<ManagedServiceInstance> msis = null;
    //        try
    //        {
    //            msis = InstanceUtil.getManagedInstances();
    //        }
    //        catch (Exception e)
    //        {
    //            throw new InstanceManagerInitException("update tenantDB failed in getManagedInstances : ", e);
    //        }
    //        for (ManagedServiceInstance m : msis)
    //        {
    //            try
    //            {
    //                deployDBClient.deployDB(DeployDBJSONUtil.generateDeployDBBody(m.getCredentials()));
    //                result.add(new InstanceDto(m));
    //            }
    //            catch (Exception e)
    //            {
    //                TenantDBCustomer tenantDBCustomer = customerRepository.findOne(m.getId());
    //                tenantDBCustomer.setStatus(MultiTenancyServiceConstants.DEPLOY_ERROR);
    //                customerRepository.save(tenantDBCustomer);
    //            }
    //        }
    //        ApiMessage<List<InstanceDto>> apiMessage = MessageUtil.getSuccessMessage(result);
    //        return apiMessage;
    //    }
}
