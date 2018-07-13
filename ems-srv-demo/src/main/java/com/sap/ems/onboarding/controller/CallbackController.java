
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:CallbackController.java <br/><br/>  
  * Description: Callback API for DevOps.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Dec 29, 2017 9:30:14 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.onboarding.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.ems.onboarding.dto.DependantServiceDto;
import com.sap.ems.onboarding.dto.PayloadDataDto;
import com.sap.ems.onboarding.service.CallBackService;


/**
  * ClassName: CallbackController <br/><br/> 
  * Description: Callback controller for DevOps.
  * @author SAP
  * @version 
  * @see
  * @since 
  */

@RestController
public class CallbackController
{
    
    @Autowired
    private CallBackService callBackService;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @PutMapping(value = "/callback/v1.0/tenants/{tenantID}")
    public String callbackPut(@PathVariable(value = "tenantID") String tenantID, @RequestBody PayloadDataDto payload)
        throws IOException, InstanceManagerInitException {
        return callBackService.callbackPut(tenantID, payload);
    }
    
    
    @GetMapping(value = "/callback/v1.0/dependencies")
    public List<DependantServiceDto> callbackGet() {
        String logMessage = "callback service successfully called with RequestMethod = GET for tenant ";
        logger.info(logMessage);
        List<DependantServiceDto> dependenciesList = new ArrayList<>();
        return dependenciesList;
    }

    // This Endpoint is called when a tenant unsubscribes from the application
    @DeleteMapping(value = "/callback/v1.0/tenants/{tenantID}")
    public void callbackDelete(@PathVariable(value = "tenantID") String tenantID, @RequestBody PayloadDataDto payload) {
        String logMessage = "callback service successfully called with RequestMethod = DELETE for tenant " + tenantID + " with payload message = " + payload.toString();
        logger.info(logMessage);
    }
}
 
