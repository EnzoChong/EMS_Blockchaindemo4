
/**
 * Project Name:summit-srv-demo 
 * File Name:EntitlementDto.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 26, 2018 5:05:54 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.dto;

import java.io.Serializable;

import com.sap.summit.demo.model.Entitlement;

/**
 * ClassName: EntitlementDto <br/>
 * <br/>
 * Description: 
 * 该DTO用来存储每一个Entitlements记录，即每一个DTO对应Module2和Module3中
 * 的一个Row，包括Quantity，Verified from/to的等信息
 * 
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class EntitlementDto implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String entitlementGuid;
    private String id;
    private String entitlementNo;
    private String quantity;
    private String status;
    private String validFrom;
    private String validTo;
    private String customerId;
    private String ownedById;
    private String description;
    //Newly Added
    private String keyValue;

    public EntitlementDto(Entitlement entitlement) 
    {
        this.entitlementGuid = entitlement.getEntitlementGuid();
        this.entitlementNo = entitlement.getEntitlementNo();
        this.status = entitlement.getStatus();
        this.customerId = entitlement.getCustomerId();
        this.id = entitlement.getId();
        this.ownedById = entitlement.getOwnedById();
        this.quantity = entitlement.getQuantity();
        this.validFrom = entitlement.getValidFrom();
        this.validTo = entitlement.getValidTo();  
        this.description = entitlement.getDescription();
        //Newly Added
        this.keyValue = entitlement.getKeyValue();
    }
    
     /**
      * create an instance: EntitlementDto. 
      * Title: EntitlementDto <br/><br/> 
      * Description: TODO
      */
    public EntitlementDto()
    {
        super();
    }

    public String getDescription()
    {
    
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getEntitlementGuid()
    {

        return entitlementGuid;
    }

    public void setEntitlementGuid(String entitlementGuid)
    {
        this.entitlementGuid = entitlementGuid;
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

    public String getStatus()
    {

        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
    
    //Newly Added
    public String getKeyValue()
    {
    	return keyValue;
    }
    
    public void setKeyValue(String keyValue)
    {
    	this.keyValue = keyValue;
    }
}
