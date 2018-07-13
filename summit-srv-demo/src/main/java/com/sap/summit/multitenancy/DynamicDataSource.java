package com.sap.summit.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;


/**
 * ClassName: DynamicDatasource <br/>
 * <br/>
 * Description: Use local thread to determine the dataSource for each request
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class DynamicDataSource extends AbstractDataSource implements InitializingBean
{
    private final static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    /*
     * the cache of dataSource
     */
    private static Map<Object, DataSource> targetDataSources = new ConcurrentHashMap<>();
    private Object defaultTargetDataSource;

    private boolean lenientFallback = true;

    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    private DataSource resolvedDefaultDataSource;

    public static Map<Object, DataSource> getTargetDataSources()
    {

        return targetDataSources;
    }

    public void setDefaultTargetDataSource(Object defaultTargetDataSource)
    {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public void setLenientFallback(boolean lenientFallback)
    {
        this.lenientFallback = lenientFallback;
    }

    public void setDataSourceLookup(DataSourceLookup dataSourceLookup)
    {
        this.dataSourceLookup = (dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
    }

    @Override
    public void afterPropertiesSet()
    {
        if (this.defaultTargetDataSource != null)
        {
            this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
        }
        else
        {
            throw new IllegalArgumentException("Property 'defaultTargetDataSource' is required");
        }
    }

    /**
     * Title: resolveSpecifiedLookupKey <br/>
     * <br/>
     * Description:
     * 
     * @param lookupKey
     * @return
     * @see
     * @since
     */
    protected Object resolveSpecifiedLookupKey(Object lookupKey)
    {
        return lookupKey;
    }

    /**
     * Title: resolveSpecifiedDataSource <br/>
     * <br/>
     * Description:checkd the defaultDataSource
     * 
     * @param dataSource
     * @return
     * @throws IllegalArgumentException
     * @see
     * @since
     */
    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException
    {
        if (dataSource instanceof DataSource)
        {
            return (DataSource) dataSource;
        }
        else if (dataSource instanceof String)
        {
            return this.dataSourceLookup.getDataSource((String) dataSource);
        }
        else
        {
            throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.sql.DataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException
    {
        try
        {
            Connection con = null;
            con = determineTargetDataSource().getConnection();
            boolean isV = con.isValid(500);
            if (isV == false)
            {
                determineTargetDataSource().getConnection().close();
                Object lookupKey = determineCurrentLookupKey();
                DynamicDBDeploy.forceUpdateDatasource((String) lookupKey);
                return targetDataSources.get(lookupKey).getConnection();
            }
            return con;
        }
        catch (Exception e)
        {
            //update the cache ,but still can't connect to DB,maybe there is some wrong in instanceManager
            throw new SQLException("can not get connection,please check the instance : " + e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException
    {
        try
        {
            Connection con = null;
            con = determineTargetDataSource().getConnection();
            boolean isV = con.isValid(500);
            if (isV == false)
            {
                determineTargetDataSource().getConnection().close();
                Object lookupKey = determineCurrentLookupKey();
                DynamicDBDeploy.forceUpdateDatasource((String) lookupKey);
                return targetDataSources.get(lookupKey).getConnection();
            }
            return con;
        }
        catch (Exception e)
        {
            //update the cache ,but still can't connect to DB,maybe there is some wrong in instanceManager
            throw new SQLException("can not get connection,please check the instance : " + e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException
    {
        if (iface.isInstance(this))
        {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException
    {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    /**
     * Title: determineTargetDataSource <br/>
     * <br/>
     * Description: determine which dataSource to hana
     * 
     * @return the right dataSource that the request needed
     * @see
     * @since
     */
    protected DataSource determineTargetDataSource()
    {
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = null;
        if (lookupKey != null)
        {
            logger.info("use the datasource : " + lookupKey);
            dataSource = targetDataSources.get(lookupKey);
        }
        if (dataSource == null && (this.lenientFallback || lookupKey == null))
        {
            logger.info("use the default datasource");
            dataSource = this.resolvedDefaultDataSource;
        }
        if (dataSource == null)
        {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    /**
     * Title: determineCurrentLookupKey <br/>
     * <br/>
     * Description: get dataSource key of cache in this request thread
     * 
     * @return the key of dataSource cache
     * @see
     * @since
     */
    protected Object determineCurrentLookupKey()
    {
        return DataSourceHolder.getDataSource();
    }
}
