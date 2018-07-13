
/**
 * Project Name:com.sap.ems.config 
 * File Name:DynamicDBUtil.java <br/><br/>  
 * Description: generate dataSource from instance
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.multitenancy;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.xsa.core.instancemanager.client.ImClientException;
import com.sap.xsa.core.instancemanager.client.ManagedServiceInstance;

/**
 * ClassName: DynamicDBUtil <br/>
 * <br/>
 * Description: use it can deploy which dataSource we use in this request
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class DynamicDBDeploy
{
    private final static Logger logger = LoggerFactory.getLogger(DynamicDBDeploy.class);
    /**
     * Title: setDataSource <br/>
     * <br/>
     * Description: add and set the dataSource or this thread
     * 
     * @param token
     * @throws ClassNotFoundException
     * @throws ImClientException
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    public static void setDataSource(String token) throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        addDataSource(token);
        DataSourceHolder.setDataSource(token);
    }

    /**
     * Title: addDataSource <br/>
     * <br/>
     * Description: add dataSource by token
     * 
     * @param token
     * @throws InstanceManagerInitException
     * @throws ImClientException
     * @throws ClassNotFoundException
     * @see
     * @since
     */
    private static void addDataSource(String token) throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        Map<Object, DataSource> map = DynamicDataSource.getTargetDataSources();
        if (!map.containsKey(token))
        {
            logger.info("Adding the datasource to Cache: " + token);
            ManagedServiceInstance managedServiceInstance = InstanceUtil.getManagedServiceInstance(token);
            if(null == managedServiceInstance){
                throw new ImClientException("The token："+token+" is not existing, please make sure that you have already onboarded and retry！");
            }
            DataSource ds = generateDataSourceFromManagedServiceInstance(managedServiceInstance);
            if (ds != null)
            {
                map.put(token, ds);
            }
        }
    }

    /**
     * Title: generateDataSourceFromManagedServiceInstance <br/>
     * <br/>
     * Description: generate DataSource from ManageredServiceInstance
     * 
     * @return DataSource
     * @throws ImClientException
     * @see
     * @since
     */
    public static DataSource generateDataSourceFromManagedServiceInstance(ManagedServiceInstance msi) throws ImClientException
    {
        Map<String, Object> map = msi.getCredentials();
        String driver = (String) map.get("driver");
        String dbUrl = (String) map.get("url");
        if (driver == null || driver.isEmpty())
        {
            throw new ImClientException("The JDBC driver is not specified in the managed service properties");
        }
        if (dbUrl == null || dbUrl.isEmpty())
        {
            throw new ImClientException("The JDBC url is not specified in the managed service properties");
        }
        if (!dbUrl.startsWith("jdbc"))
        {
            throw new ImClientException("The DB url given in the managed service properties is not a JDBC url: " + dbUrl);
        }
        String user = (String) map.get("user");
        String pwd = (String) map.get("password");
        if (user == null || pwd == null || user.isEmpty() || pwd.isEmpty())
        {
            throw new ImClientException("The JDBC user or password is not specified or null in the managed service properties");
        }
        DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driver).url(dbUrl).username(user).password(pwd);
        return factory.build();
    }

    /**
     * Title: forceUpdateDatasource <br/>
     * <br/>
     * Description: force update dataSource cache include to update the instance cache and dataSource cache
     * 
     * @param token
     * @throws ClassNotFoundException
     * @throws ImClientException
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    public static void forceUpdateDatasource(String token) throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        logger.info("force update datasource to Cache : " + token);
        Map<Object, DataSource> map = DynamicDataSource.getTargetDataSources();
        ManagedServiceInstance managedServiceInstance = InstanceUtil.getManagedServiceInstance(token, true);
        DataSource ds = generateDataSourceFromManagedServiceInstance(managedServiceInstance);
        if (ds != null)
        {
            map.put(token, ds);
        }
    }

}
