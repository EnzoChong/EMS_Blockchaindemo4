
/**
 * Project Name:summit-srv-demo 
 * File Name:Entitlement.java <br/><br/>  
 * Description: Entitlement.java
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 26, 2018 1:26:59 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ClassName: Entitlement <br/>
 * <br/>
 * Description: Entitlement
 * Model中的信息与DTO中的信息相互转化，前端接口不会通过Model直接从JPA数据库中获取数据
 * 
 * 前端接口通常是将输入的信息映射到DTO上，再通过Controller调用Service模块中相应的转换函数将DTO通过Model映射到DB中
 * 后端接口通常是将数据通过Model取出，再通过Controller条用Service模块中相应的转换函数将数据映射到DTO中发往前台
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Entity
@Table(name = "\"com.sap.ems.db.demo.company.data::Entitlement\"")
public class Entitlement implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String ENTITLEMENT_GUID = "\"EntitlementGUID\"";
    public static final String ID = "\"ID\"";
    public static final String ENTITLEMENT_NO = "\"EntitlementNo\"";
    public static final String QUANTITY = "\"Quantity\"";
    public static final String VALID_FROM = "\"ValidFrom\"";
    public static final String VALID_TO = "\"ValidTo\"";
    public static final String STATUS = "\"Status\"";
    public static final String CUSTOMER_ID = "\"CustomerID\"";
    public static final String OWNEDBY_ID = "\"OwnedByID\"";
    public static final String DESCRIPTION = "\"Description\"";
    // Newly Added
    public static final String KEY_VALUE = "\"KeyValue\"";
    
    // JPA stuff, we set up the Column's name in the table as static variables and then put them in 
    // the name properties of Column annotations to build the mapping relationship
    @Id
    @Column(name = ENTITLEMENT_GUID)
    private String entitlementGuid;

    @Column(name = ID)
    private String id;

    @Column(name = ENTITLEMENT_NO)
    private String entitlementNo;

    @Column(name = QUANTITY)
    private String quantity;

    @Column(name = VALID_FROM)
    private String validFrom;

    @Column(name = VALID_TO)
    private String validTo;

    @Column(name = STATUS)
    private String status;

    @Column(name = CUSTOMER_ID)
    private String customerId;

    @Column(name = OWNEDBY_ID)
    private String ownedById;
    
    // Newly Added
    @Column(name = KEY_VALUE)
    private String keyValue;
    
    @Column(name = DESCRIPTION)
    private String description;
    
    public String getDescription()
    {
    
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEntitlementNo()
    {

        return entitlementNo;
    }

    public void setEntitlementNo(String entitlementNo)
    {
        this.entitlementNo = entitlementNo;
    }

    public String getQuantity()
    {

        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getValidFrom()
    {

        return validFrom;
    }

    public void setValidFrom(String validFrom)
    {
        this.validFrom = validFrom;
    }

    public String getValidTo()
    {

        return validTo;
    }

    public void setValidTo(String validTo)
    {
        this.validTo = validTo;
    }

    public String getStatus()
    {

        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCustomerId()
    {

        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public String getOwnedById()
    {

        return ownedById;
    }

    public void setOwnedById(String ownedById)
    {
        this.ownedById = ownedById;
    }

    public String getEntitlementGuid()
    {
    
        return entitlementGuid;
    }

    public void setEntitlementGuid(String entitlementGuid)
    {
        this.entitlementGuid = entitlementGuid;
    }
    
    // Newly Added
    public String getKeyValue()
    {
    	return keyValue;
    }
    
    public void setKeyValue(String keyValue)
    {
    	this.keyValue = keyValue;
    }

}
