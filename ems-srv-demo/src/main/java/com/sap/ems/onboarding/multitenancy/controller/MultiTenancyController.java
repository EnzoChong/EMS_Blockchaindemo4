
/**
 * Project Name:com.sap.ems.onboarding.callback 
 * File Name:MultiTennacyController.java <br/><br/>  
 * Description: controller for onborading
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.onboarding.multitenancy.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ems.message.ApiMessage;
import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.ems.onboarding.multitenancy.dto.CreateMultiTenancyDto;
import com.sap.ems.onboarding.multitenancy.dto.InstanceDto;
import com.sap.ems.onboarding.multitenancy.service.MultiTenancyService;

/**
 * ClassName: MultiTennacyController <br/>
 * <br/>
 * Description: MultiTennacyController
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@RestController
public class MultiTenancyController
{
    @Autowired
    private MultiTenancyService mtService;

    /**
     * Title: createMultiTenancy <br/>
     * <br/>
     * Description: create MultiTenancy
     * 
     * @param cMtDto
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @PostMapping(value = "/callback/v1.0/multi-tenancy/{tenantID}")
    public ResponseEntity<ApiMessage<InstanceDto>> createMultiTenancyWithParamter(@PathVariable("tenantID") String tenantID,@Valid @RequestBody CreateMultiTenancyDto cMtDto)
        throws InstanceManagerInitException
    {
        ApiMessage<InstanceDto> apiMessage = mtService.createWithParamter(tenantID,cMtDto);
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    /**
     * Title: readMultiTenancy <br/>
     * <br/>
     * Description: readMultiTenancy
     * 
     * @param token
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @GetMapping(value = "/callback/v1.0/multi-tenancy/{tenantID}")
    public ResponseEntity<ApiMessage<InstanceDto>> readMultiTenancy(@PathVariable("tenantID") String tenantID) throws InstanceManagerInitException
    {
        ApiMessage<InstanceDto> apiMessage = mtService.read(tenantID);
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    /**
     * Title: deleteMultiTenancy <br/>
     * <br/>
     * Description: deleteMultiTenancy
     * 
     * @param token
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @DeleteMapping(value = "/callback/v1.0/multi-tenancy/{tenantID}")
    public ResponseEntity<ApiMessage<InstanceDto>> deleteMultiTenancy(@PathVariable("tenantID") String tenantID) throws InstanceManagerInitException
    {
        ApiMessage<InstanceDto> apiMessage = mtService.delete(tenantID);
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    /**
     * Title: deleteMultiTenancy <br/>
     * <br/>
     * Description: deleteMultiTenancy
     * 
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @GetMapping(value = "/callback/v1.0/multi-tenancy/all")
    public ResponseEntity<ApiMessage<List<InstanceDto>>> getAllMultiTenancy() throws InstanceManagerInitException
    {
        ApiMessage<List<InstanceDto>> apiMessage = mtService.getAll();
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    /**
     * Title: updateMultiTenancy <br/>
     * <br/>
     * Description: update the db of all customer
     * 
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @PutMapping(value = "/callback/v1.0/multi-tenancy/all")
    public ResponseEntity<ApiMessage<Map<String, List<InstanceDto>>>> updateMultiTenancys() throws InstanceManagerInitException
    {
        ApiMessage<Map<String, List<InstanceDto>>> apiMessage = mtService.update();
        if(!apiMessage.getData().get("ERROR").isEmpty()){
            return new ResponseEntity<>(apiMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    /**
     * Title: updateMultiTenancy <br/>
     * <br/>
     * Description: redeploy multitanency with token
     * 
     * @param token
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    @PutMapping(value = "/callback/v1.0/multi-tenancy/{tenantID}")
    public ResponseEntity<ApiMessage<InstanceDto>> updateMultiTenancy(@PathVariable("tenantID") String tenantID) throws InstanceManagerInitException
    {
        ApiMessage<InstanceDto> apiMessage = mtService.update(tenantID);
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

}
