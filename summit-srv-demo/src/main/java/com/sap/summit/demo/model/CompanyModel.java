
/**
 * Project Name:ems-srv-demo 
 * File Name:CompanyModel.java <br/><br/>  
 * Description: CompanyModel
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 31, 2018 3:53:06 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ClassName: CompanyModel <br/>
 * <br/>
 * Description: CompanyModel
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Entity
@Table(name = "\"com.sap.ems.db.demo.company.data::CompanyModel\"")
public class CompanyModel implements Serializable
{
    public static final String GUID = "\"GUID\"";
    public static final String ID = "\"ID\"";
    public static final String NAME = "\"Name\"";
    public static final String ADDRESS = "\"Address\"";

    /**
     * serialVersionUID(describe the field in a word).
     * 
     * @see
     * @since
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = GUID)
    private String guid;
    @Column(name = ID)
    private String id;
    @Column(name = NAME)
    private String name;
    @Column(name = ADDRESS)
    private String address;

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
