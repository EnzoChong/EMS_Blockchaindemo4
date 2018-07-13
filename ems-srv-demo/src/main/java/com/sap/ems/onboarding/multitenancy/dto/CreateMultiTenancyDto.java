
/**
 * Project Name:com.sap.ems.onboarding.callback 
 * File Name:CreateMultiTenancyDto.java <br/><br/>  
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
package com.sap.ems.onboarding.multitenancy.dto;

import java.io.Serializable;

/**
 * ClassName: CreateMultiTenancyDto <br/>
 * <br/>
 * Description: CreateMultiTenancyDto
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class CreateMultiTenancyDto implements Serializable
{

    /**
     * @see
     * @since
     */
    private static final long serialVersionUID = 1L;

    private String dataBase;

    public String getDataBase()
    {

        return dataBase;
    }

    public void setDataBase(String dataBase)
    {
        this.dataBase = dataBase;
    }
}
