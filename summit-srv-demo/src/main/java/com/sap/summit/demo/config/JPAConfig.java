
/**
 * Project Name:com.sap.summit.integration 
 * File Name:Configuration.java
 * Description: 
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 22, 2017 3:27:42 PM
 * @version 
 * @see
 * @since 
 */

package com.sap.summit.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * ClassName: Configuration Description: Used for configure JPA with powered by EclipseLink without persistemce.xml
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Configuration
public class JPAConfig extends JpaBaseConfiguration
{
	 @Autowired
	 @Qualifier("dataSource")
	 private DataSource dataSource;
	 /**
	  * create an instance: JPAConfig. 
	  * Title: JPAConfig <br/><br/> 
	  * Description: TODO
	  * @param dataSource
	  * @param properties
	  * @param jtaTransactionManager
	  * @param transactionManagerCustomizers
	  */
	protected JPAConfig(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager,
		ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers)
	{
		 super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);  
	}

	/**
	 * Title: createJpaVendorAdapter Description:
	 * 
	 * @return (describe the param)
	 * @see org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration#createJpaVendorAdapter()
	 */

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter()
	{
		return new EclipseLinkJpaVendorAdapter();
	}

	@Override
	protected Map<String, Object> getVendorProperties()
	{
		return new HashMap<>();
	}
}
