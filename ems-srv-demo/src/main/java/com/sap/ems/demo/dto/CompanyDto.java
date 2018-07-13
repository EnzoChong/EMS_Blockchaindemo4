
/**
 * Project Name:ems-srv-demo 
 * File Name:Company.java <br/><br/>  
 * Description: CompanyDto
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 31, 2018 3:51:35 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.demo.dto;

import java.io.Serializable;

import com.sap.ems.demo.model.CompanyModel;

/**
 * ClassName: CompanyDto <br/>
 * <br/>
 * Description: CompanyDto
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class CompanyDto implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String guid;
    private String id;
    private String name;
    private String address;

    /**
     * create an instance: CompanyDto. Title: CompanyDto <br/>
     * <br/>
     * Description: CompanyDto
     * 
     * @param model
     */
    public CompanyDto(CompanyModel model)
    {
        this.guid = model.getGuid();
        this.id = model.getId();
        this.name = model.getName();
        this.address = model.getAddress();
    }

    /**
     * create an instance: CompanyDto. Title: CompanyDto <br/>
     * <br/>
     * Description: CompanyDto
     * 
     * @param guid
     * @param id
     * @param name
     * @param address
     */
    public CompanyDto(String guid, String id, String name, String address)
    {
        super();
        this.guid = guid;
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * create an instance: CompanyDto. Title: CompanyDto <br/>
     * <br/>
     * Description: CompanyDto
     */
    public CompanyDto()
    {
        super();
    }

    public String getGuid()
    {

        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getId()
    {

        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {

        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {

        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

}
