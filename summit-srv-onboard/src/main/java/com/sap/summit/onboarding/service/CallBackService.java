
  
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
  package com.sap.summit.onboarding.service;

import com.sap.summit.multitenancy.exception.InstanceManagerInitException;
import com.sap.summit.onboarding.dto.PayloadDataDto;

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
 