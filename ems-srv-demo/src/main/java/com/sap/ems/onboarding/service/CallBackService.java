
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:CallBackService.java <br/><br/>  
  * Description: On-boarding callback service.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jan 4, 2018 11:01:42 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.onboarding.service;

import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.ems.onboarding.dto.PayloadDataDto;

/**
  * ClassName: CallBackService <br/><br/> 
  * Description: On-boarding callback service.
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public interface CallBackService
{
    String callbackPut(String tenantId, PayloadDataDto payload) throws InstanceManagerInitException;
}
 