
/**
 * Project Name:summit-srv-demo 
 * File Name:EntitlementService.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 26, 2018 4:16:48 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.service;

import java.util.List;

import com.sap.summit.demo.dto.DropDown;
import com.sap.summit.demo.dto.EntitlementDto;
import com.sap.summit.demo.dto.SearchCriteria;
import com.sap.summit.demo.dto.TransactionDto;

/**
 * ClassName: EntitlementService <br/>
 * <br/>
 * Description: TODO
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public interface EntitlementService
{

    EntitlementDto publish(EntitlementDto entitlementDto);

    List<EntitlementDto> transaction(EntitlementDto entitlementDto, String type);

    boolean validateEntitlement(EntitlementDto entitlementDto);
    
    // Newly Added
    boolean validateBilateralEntitlement(EntitlementDto entitlementDto);

    List<TransactionDto> getTransactionDetail(String guid);
    
    List<DropDown> getCompanies();
    
    List<DropDown> getUsers();

    EntitlementDto create(EntitlementDto entitlementDto);

    EntitlementDto update(EntitlementDto entitlementDto);

    EntitlementDto get(String guid);

    EntitlementDto delete(String guid);

    List<EntitlementDto> getAll(SearchCriteria filters);

    List<EntitlementDto> deleteById(String id);
    
    List<EntitlementDto> testBlockChainApi(String url);
    
}
