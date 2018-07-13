package com.sap.summit.multitenancy;

/**
 * ClassName: DataSourceHolder <br/>
 * <br/>
 * Description: make the token (dataSource) thread local
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class DataSourceHolder
{
    //thread local Environmental
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    public static void setDataSource(String token)
    {
        dataSources.set(token);
    }

    //get dataSource
    public static String getDataSource()
    {
        return dataSources.get();
    }

    //clear DataSource
    public static void clearDataSource()
    {
        dataSources.remove();
    }
}
