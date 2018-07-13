
  
 /**
  * Project Name:ems-srv-demo 
  * File Name:DemoService.java <br/><br/>  
  * Description: DemoService
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jan 31, 2018 3:53:55 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.demo.service;

import java.util.List;

import com.sap.ems.demo.dto.CompanyDto;

/**
  * ClassName: DemoService <br/><br/> 
  * Description: DemoService
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public interface DemoService
{

    
     /**
      * Title: getAll <br/><br/>  
      * Description: getAll
      * @return getAll
      * @see
      * @since
      */
    List<CompanyDto> getAll();

    
     /**
      * Title: create <br/><br/>  
      * Description: getAll
      * @param companyDto
      * @return getAll
      * @see
      * @since
      */
    CompanyDto create(CompanyDto companyDto);


    
     /**
      * Title: get <br/><br/>  
      * Description: getAll
      * @param id
      * @return getAll
      * @see
      * @since
      */
    List<CompanyDto> get(String id);


    
     /**
      * Title: delete <br/><br/>  
      * Description: getAll
      * @param id
      * @return getAll
      * @see
      * @since
      */
    List<CompanyDto> delete(String id);


    
     /**
      * Title: update <br/><br/>  
      * Description: getAll
      * @param companyDto
      * @return getAll
      * @see
      * @since
      */
    CompanyDto update(CompanyDto companyDto);

}
 