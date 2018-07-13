
  
 /**
  * Project Name:summit-srv-demo 
  * File Name:Filter.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Apr 27, 2018 11:21:41 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.demo.dto;

  
 /**
  * ClassName: Filter <br/><br/> 
  * Description: 
  * 一个SearchFilter中用到的过滤模块，同样用于设置搜检条件
  * 具体的配合方式细看代码
  * 
  * 
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class SearchFilter
{
    private String key;
    private String value;
    public String getKey()
    {
    
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public String getValue()
    {
    
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
 