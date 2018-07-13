
/**
 * Project Name:summit-srv-demo 
 * File Name:EntitlementRepo.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 26, 2018 4:12:31 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sap.summit.demo.model.Entitlement;

/**
 * ClassName: EntitlementRepo <br/>
 * <br/>
 * Description: TODO
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, String>, JpaSpecificationExecutor<Entitlement>
{
    List<Entitlement> findById(String id);

    List<Entitlement> findByStatus(String status);

    List<Entitlement> findByStatusAndOwnedById(String status, String ownedById);

    List<Entitlement> findByOwnedById(String ownedById);

    List<Entitlement> findByStatusAndCustomerId(String status, String customerId);

    List<Entitlement> findByCustomerId(String customerId);

    List<Entitlement> findByCustomerIdAndOwnedById(String customerId, String ownedById);
}
