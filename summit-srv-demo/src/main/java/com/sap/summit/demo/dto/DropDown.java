
  
 /**
  * Project Name:summit-srv-demo 
  * File Name:CompanyDropDown.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Apr 27, 2018 8:26:27 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.demo.dto;

import java.io.Serializable;

/**
  * ClassName: CompanyDropDown <br/><br/> 
  * Description: 
  * DropDown DTO用于存储以下信息：
  * EMS BlockChain App内的Company下拉菜单栏，显示分发给各公司的Entitlements
  * Customer App内的Customer下拉菜单栏，显示各个公司购买的Entitlements
  * 
  * 
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class DropDown implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String key;

    private String text;

    public String getKey()
    {
    
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getText()
    {
    
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
 