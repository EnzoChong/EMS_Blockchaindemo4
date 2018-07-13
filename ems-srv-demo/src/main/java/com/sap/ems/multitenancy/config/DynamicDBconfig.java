
  
 /**
  * Project Name:ems-infrastructure 
  * File Name:DynamicDBconfig.java <br/><br/>  
  * Description: Turn up or down the dynamic database
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Sep 27, 2017 11:15:34 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.multitenancy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
  * ClassName: DynamicDBconfig <br/><br/> 
  * Description: Turn up or down the dynamic database
  * @author SAP
  * @version 
  * @see
  * @since 
  */
@Component
@ConfigurationProperties(prefix = "sap.ems.dynamic.datasource")
public class DynamicDBconfig
{
    private boolean swi;

    public boolean isSwi()
    {
        return swi;
    }

    public void setSwi(boolean swi)
    {
        this.swi = swi;
    }
    
}
 