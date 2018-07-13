
/**
 * Project Name:ems-srv-demo 
 * File Name:DemoServiceImpl.java <br/><br/>  
 * Description: DemoServiceImpl
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 31, 2018 3:53:33 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.summit.demo.dto.CompanyDto;
import com.sap.summit.demo.model.CompanyModel;
import com.sap.summit.demo.repositroy.DemoRepository;
import com.sap.summit.demo.repositroy.RedisRepository;
import com.sap.summit.demo.service.DemoService;
import com.sap.summit.mq.dto.MessageDto;
import com.sap.summit.mq.sender.SyncRedisSender;

/**
 * ClassName: DemoServiceImpl <br/>
 * <br/>
 * Description: DemoServiceImpl
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Service
public class DemoServiceImpl implements DemoService
{
    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private SyncRedisSender sender;

    @Autowired
    private RedisRepository redisRepositoryImpl;

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.DemoService#getAll()
     */
    @Override
    public List<CompanyDto> getAll()
    {
        List<CompanyModel> result = demoRepository.findAll();
        List<CompanyDto> out = new ArrayList<>();
        result.stream().forEach(model -> {
            out.add(new CompanyDto(model));
        });
        return out;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.DemoService#create(com.sap.summit.demo.dto.CompanyDto)
     */
    @Override
    public CompanyDto create(CompanyDto companyDto)
    {
        CompanyModel model = new CompanyModel();
        model.setAddress(companyDto.getAddress());
        model.setId(companyDto.getId());
        model.setName(companyDto.getName());
        model.setGuid(StringUtils.isEmpty(companyDto.getGuid()) ? UUID.randomUUID().toString().replace("-", "") : companyDto.getGuid());
        model = demoRepository.save(model);

        MessageDto message = new MessageDto();

        message.setGuid(companyDto.getGuid());
        message.setId(companyDto.getId());
        message.setAddress(companyDto.getAddress());
        message.setName(companyDto.getName());

        if (model != null)
        {
            sender.sendMsg(message);
        }

        return new CompanyDto(model);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.DemoService#get(java.lang.String)
     */
    @Override
    public List<CompanyDto> get(String id)
    {
        List<CompanyDto> result = new ArrayList<>();

        CompanyDto company = redisRepositoryImpl.getCompany(id);

        if (company == null)
        {
            List<CompanyModel> models = demoRepository.findById(id);

            if (models != null)
            {
                models.stream().forEach(model -> {
                    result.add(new CompanyDto(model));

                    MessageDto message = new MessageDto();

                    message.setGuid(model.getGuid());
                    message.setId(model.getId());
                    message.setName(model.getName());
                    message.setAddress(model.getAddress());

                    sender.sendMsg(message);
                });
            }

        }
        else
        {
            result.add(company);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.DemoService#delete(java.lang.String)
     */
    @Override
    public List<CompanyDto> delete(String id)
    {
        List<CompanyDto> result = new ArrayList<>();
        List<CompanyModel> models = demoRepository.findById(id);
        models.stream().forEach(model -> {
            demoRepository.delete(model);
            result.add(new CompanyDto(model));
        });
        if (result != null)
        {
            redisRepositoryImpl.deleteCompany(id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.sap.summit.demo.service.DemoService#update(com.sap.summit.demo.dto.CompanyDto)
     */
    @Override
    public CompanyDto update(CompanyDto companyDto)
    {
        CompanyModel model = demoRepository.findOne(companyDto.getGuid());
        model.setAddress(companyDto.getAddress());
        model.setId(companyDto.getId());
        model.setName(companyDto.getName());
        model = demoRepository.save(model);

        if (model != null)
        {
            redisRepositoryImpl.updateCompany(companyDto);
        }

        return new CompanyDto(model);
    }

}
