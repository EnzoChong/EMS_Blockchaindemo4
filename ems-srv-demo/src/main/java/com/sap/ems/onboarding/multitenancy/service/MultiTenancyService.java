
/**
 * Project Name:com.sap.ems.onboarding.callback 
 * File Name:MultiTenancyService.java <br/><br/>  
 * Description: service
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.onboarding.multitenancy.service;

import java.util.List;
import java.util.Map;

import com.sap.ems.message.ApiMessage;
import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.ems.onboarding.multitenancy.dto.CreateMultiTenancyDto;
import com.sap.ems.onboarding.multitenancy.dto.InstanceDto;

/**
 * ClassName: MultiTenancyService <br/>
 * <br/>
 * Description: MultiTenancyService
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public interface MultiTenancyService
{
//
//    /**
//     * Title: create <br/>
//     * <br/>
//     * Description: create instance for all customers in database who don't hava created instance
//     * 
//     * @return ApiMessage
//     * @see
//     * @since
//     */
//    ApiMessage<List<InstanceDto>> create() throws InstanceManagerInitException;

    /**
     * Title: delete <br/>
     * <br/>
     * Description: delete a isntance
     * 
     * @param token
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    ApiMessage<InstanceDto> delete(String token) throws InstanceManagerInitException;

    /**
     * Title: read <br/>
     * <br/>
     * Description: get a instance with token
     * 
     * @param token
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    ApiMessage<InstanceDto> read(String token) throws InstanceManagerInitException;

    /**
     * Title: getAll <br/>
     * <br/>
     * Description: get all instance
     * 
     * @return
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    ApiMessage<List<InstanceDto>> getAll() throws InstanceManagerInitException;

    
     /**
      * Title: createWithParamter <br/><br/>  
      * Description: create an instance
     * @param tenantID 
      * @param cMtDto
      * @return
      * @see
      * @since
      */
    ApiMessage<InstanceDto> createWithParamter(String tenantID, CreateMultiTenancyDto cMtDto) throws InstanceManagerInitException;

    
     /**
      * Title: update <br/><br/>  
      * Description: update
      * @return update
      * @see
      * @since
      */
    ApiMessage<Map<String,List<InstanceDto>>> update() throws InstanceManagerInitException;
    
    
    
     /**
      * Title: update <br/><br/>  
      * Description: update 
      * @param token
      * @return
      * @see
      * @since
      */
    ApiMessage<InstanceDto> update(String token) throws InstanceManagerInitException;

}
