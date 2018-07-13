
  
 /**
  * Project Name:summit-srv-demo 
  * File Name:Transaction.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Apr 26, 2018 5:46:45 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.demo.dto;

import java.io.Serializable;

/**
  * ClassName: Transaction <br/><br/> 
  * Description: 
  * 该DTO用来存储Transaction交易信息，就是在Module2中我们每次Split之后publish，形成一笔交易，
  * 之后Customer方Purchase了之后又形成了一笔Entitlements交易，这里的Transaction就是用来存储点击相应的Entitlements
  * 后弹出的窗口内显示的交易信息
  * 
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class TransactionDto implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String newEntitlementGuid;
    private String newOwnedBy;
    private String oldEntitlementGuid;
    private String oldOwnedBy;
    private String entitlementNo;
    private String customerId;
    private String quantity;
    private String type;
    private String userId;
    public String getNewEntitlementGuid()
    {
        return newEntitlementGuid;
    }
    public void setNewEntitlementGuid(String newEntitlementGuid)
    {
        this.newEntitlementGuid = newEntitlementGuid;
    }
    public String getOldEntitlementGuid()
    {
    
        return oldEntitlementGuid;
    }
    public void setOldEntitlementGuid(String oldEntitlementGuid)
    {
        this.oldEntitlementGuid = oldEntitlementGuid;
    }
    public String getQuantity()
    {
    
        return quantity;
    }
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    public String getType()
    {
    
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getUserId()
    {
    
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getNewOwnedBy()
    {
    
        return newOwnedBy;
    }
    public void setNewOwnedBy(String newOwnedBy)
    {
        this.newOwnedBy = newOwnedBy;
    }
    public String getOldOwnedBy()
    {
    
        return oldOwnedBy;
    }
    public void setOldOwnedBy(String oldOwnedBy)
    {
        this.oldOwnedBy = oldOwnedBy;
    }
    public String getEntitlementNo()
    {
    
        return entitlementNo;
    }
    public void setEntitlementNo(String entitlementNo)
    {
        this.entitlementNo = entitlementNo;
    }
    public String getCustomerId()
    {
    
        return customerId;
    }
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
}
 