
  
 /**
  * Project Name:ems-srv-demo 
  * File Name:DemoRepository.java <br/><br/>  
  * Description: DemoRepository
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jan 31, 2018 4:15:50 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.demo.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.ems.demo.model.CompanyModel;

/**
  * ClassName: DemoRepository <br/><br/> 
  * Description: DemoRepository
  * @author SAP
  * @version 
  * @see
  * @since 
  */
@Repository
public interface DemoRepository extends JpaRepository<CompanyModel, String>
{

    List<CompanyModel> findById(String id);

}
 