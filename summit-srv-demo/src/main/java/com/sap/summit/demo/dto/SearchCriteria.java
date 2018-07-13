
  
 /**
  * Project Name:summit-srv-demo 
  * File Name:SearchFilter.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Apr 27, 2018 11:16:11 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.demo.dto;

import java.util.List;

/**
  * ClassName: SearchFilter <br/><br/> 
  * Description: 
  * SearchCriteria包括以下两个信息，
  * (1)Drop down下拉菜单栏中选择的筛选条件，即公司
  * (2)另一个是到底是Module2的请求还是Module3的请求
  * 两者之间的差异:
  * Module3中只能看到在Module2中Publish了之后的Entitlements
  * Module2中可以看到所有的Module1中创建出来的Entitlements
  * 条件为active/unactive & company name
  * 
  * 
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class SearchCriteria
{

    private List<SearchFilter> filters;
    
    private String type;
    
    public List<SearchFilter> getFilters()
    {
        return filters;
    }

    public void setFilters(List<SearchFilter> filters)
    {
        this.filters = filters;
    }

    public String getType()
    {
    
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
 