  package com.sap.ems.onboarding.service.impl;
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:CallBackServiceImpl.java <br/><br/>  
  * Description: Implementation for callback service.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jan 4, 2018 11:03:33 AM
  * @version 
  * @see
  * @since 
  */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sap.ems.message.ApiMessage;
import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.ems.onboarding.dto.PayloadDataDto;
import com.sap.ems.onboarding.exception.OnboardingException;
import com.sap.ems.onboarding.multitenancy.dto.CreateMultiTenancyDto;
import com.sap.ems.onboarding.multitenancy.dto.InstanceDto;
import com.sap.ems.onboarding.multitenancy.service.MultiTenancyService;
import com.sap.ems.onboarding.service.CallBackService;
import com.sap.xsa.core.instancemanager.model.OperationStatus;

/**
  * ClassName: CallBackServiceImpl <br/><br/> 
  * Description: Service implementation for callback.
  * @author SAP
  * @version 
  * @see
  * @since 
  */
@Service
public class CallBackServiceImpl implements CallBackService
{
    
    @Autowired
    private Environment env;
    
    @Autowired
    private MultiTenancyService mtService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * {@inheritDoc} 
     * @throws InstanceManagerInitException 
     * @see com.sap.csc.ems.onboarding.service.CallBackService#callbackPut(java.lang.String, com.sap.csc.ems.onboarding.dto.PayloadDataDto)
     */
    @Override
    public String callbackPut(String tenantId, PayloadDataDto payload) throws InstanceManagerInitException
    {
        String logMessage = "callback service successfully called with RequestMethod = PUT for tenant " + tenantId + " with payload message = " + payload.toString();
        logger.info(logMessage);
        //Fetch value of CF_SPACE from environment variable.
        String vcap = System.getenv("VCAP_APPLICATION");
        if(vcap == null){
            return null;
        }
        
        JSONObject jsonObject = JSON.parseObject(vcap);
        
        String spaceGuid = "";
        String hostUrl = "";
        String spacePrefix = "";
        if(jsonObject != null){
            spaceGuid = jsonObject.getString("space_id");
            hostUrl = jsonObject.getString("cf_api");
            spacePrefix = jsonObject.getString("space_name").toLowerCase();
        }
        
        logger.info("Space guid: {}", spaceGuid);
        //Call HANA Instance Manager to create tenant database and schema.
        CreateMultiTenancyDto cmtDto = new CreateMultiTenancyDto();
        cmtDto.setDataBase(spaceGuid + ":" + payload.getSubscribedSubdomain().replace("-", ""));
        
        //Query instance database first.
        try{
            ApiMessage<InstanceDto> apiMessage = mtService.read(tenantId);
            InstanceDto instance = apiMessage.getData();
            if(instance != null){
                if(instance.getStatus().equals(OperationStatus.CREATION_SUCCEEDED)){
                    //Already have created database successfully before, just return the URL directly.
                    return hostUrl.replace("api.cf", payload.getSubscribedSubdomain() + spacePrefix + ".cfapps");
                }
                else{
                    //Delete the failed database.
                    mtService.delete(tenantId);
                }
            }
        }catch(InstanceManagerInitException error){
            //Instance database may not exist.
            logger.error("Instance database may not exist:" + error.getMessage());
        }
        //Create the database.
        try
        {
            mtService.createWithParamter(tenantId, cmtDto);
        }
        catch (InstanceManagerInitException e)
        {
             logger.error("Instance Manager Error in callback service:{}", e.getMessage());
             //Delete database.
             mtService.delete(tenantId);
             throw new OnboardingException("Instance Manager Error in callback service with message " + e.getMessage() + "with payload :" + payload.toString() + " with space prefix:" + spacePrefix);
        }
        return hostUrl.replace("api.cf", payload.getSubscribedSubdomain() + spacePrefix + ".cfapps");
    }

}
 