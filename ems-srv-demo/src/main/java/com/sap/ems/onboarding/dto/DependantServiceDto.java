
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:DependantServiceDto.java <br/><br/>  
  * Description: Get dependencies for DevOps.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Dec 29, 2017 9:33:29 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.onboarding.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
  * ClassName: DependantServiceDto <br/><br/> 
  * Description: Dependant service Dto for DevOps getting dependencies.
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class DependantServiceDto
{
    @NotBlank
    private String appName;

    @NotBlank
    private String appId;

    public DependantServiceDto() {}

    public DependantServiceDto(String appName, String appId) {
        this.appName = appName;
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DependantServiceDto))
            return false;

        DependantServiceDto that = (DependantServiceDto) o;

        if (!appName.equals(that.appName))
            return false;
        return appId.equals(that.appId);
    }

    @Override public int hashCode() {
        int result = appName.hashCode();
        result = 31 * result + appId.hashCode();
        return result;
    }
}
 