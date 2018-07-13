
  
 /**
  * Project Name:ems-srv-demo 
  * File Name:RedisRepository.java <br/><br/>  
  * Description: RedisRepository
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Feb 7, 2018 1:52:38 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.demo.repositroy;

import com.sap.ems.demo.dto.CompanyDto;

/**
  * ClassName: RedisRepository <br/><br/> 
  * Description: RedisRepository
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public interface RedisRepository
{
    void saveCompany(CompanyDto dto);
    CompanyDto getCompany(String guid);
    Long deleteCompany(String guid);
    void updateCompany(CompanyDto dto);
}
 