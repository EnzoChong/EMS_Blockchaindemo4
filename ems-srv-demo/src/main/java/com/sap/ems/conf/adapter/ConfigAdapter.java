
/**
 * Title: ConfigAdapter.java
 * Package com.sap.csc.ems.conf.adapter
 * Description: 
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Mar 21, 2017 5:21:11 PM
 * @version V1.0
 */

package com.sap.ems.conf.adapter;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.sap.ems.conf.Config;

/**
 * ClassName: ConfigAdapter Description:
 * 
 * @author SAP
 * @version
 * @since
 */
public abstract class ConfigAdapter implements Config
{
    //store config key and value
    protected Map<String, Object> configMap = new HashMap<>();
    /*
     * Title: getString Description
     * 
     * @param key
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.Config#getString(java.lang.String)
     */

    @Override
    public String getString(String key)
    {
        Object value = configMap.get(key);
        if (null != value)
        {
            return value.toString();
        }
        return null;
    }

    /*
     * Title: getInt Description:
     * 
     * @param key
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.Config#getInt(java.lang.String)
     */

    @Override
    public Integer getInt(String key)
    {
        String value = this.getString(key);
        if (null != value)
        {
            return Integer.parseInt(value);
        }
        return null;
    }

    /*
     * Title: getLong Description:
     * 
     * @param key
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.Config#getLong(java.lang.String)
     */

    @Override
    public Long getLong(String key)
    {
        String value = this.getString(key);
        if (null != value)
        {
            return Long.parseLong(value);
        }
        return null;
    }

    /*
     * Title: getBoolean Description:
     * 
     * @param key
     * 
     * @return (descrip the param) default return false
     * 
     * @see com.sap.csc.ems.conf.Config#getBoolean(java.lang.String)
     */

    @Override
    public Boolean getBoolean(String key)
    {
        String value = this.getString(key);
        if (null != value)
        {
            return Boolean.parseBoolean(value);
        }
        return false;
    }

    /*
     * Title: getDouble Description:
     * 
     * @param key
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.Config#getDouble(java.lang.String)
     */

    @Override
    public Double getDouble(String key)
    {
        String value = this.getString(key);
        if (null != value)
        {
            return Double.parseDouble(value);
        }
        return null;
    }

    /*
     * Title: get Description:
     * 
     * @param type
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.Config#get(java.lang.Class)
     */

    @Override
    public <T> T get(Class<T> type)
    {

        try
        {
            @SuppressWarnings("unchecked")
            T tb = (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]
            { type }, (proxy, method, args) -> {
                String methodName = method.getName();
                Class<?> returnClazz = method.getReturnType();
                return returnInvo(methodName, returnClazz);
            });
            return tb;
        }
        catch (IllegalArgumentException e)
        {
        }

        return null;
    }

    /**
     * Title: returnInvo Description: return instance of base date type
     * 
     * @exception @see
     * @since
     * @param methodName:
     *            method name
     * @param returnClazz:
     *            return type of method
     * @return Object
     */
    @SuppressWarnings("javadoc")
    private Object returnInvo(String methodName, Class<?> returnClazz)
    {
        if (returnClazz == String.class)
        {
            return ConfigAdapter.this.getString(methodName);
        }

        if (returnClazz == Integer.class || returnClazz == int.class)
        {
            return ConfigAdapter.this.getInt(methodName);
        }

        if (returnClazz == Long.class || returnClazz == long.class)
        {
            return ConfigAdapter.this.getLong(methodName);
        }

        if (returnClazz == Double.class || returnClazz == double.class)
        {
            return ConfigAdapter.this.getDouble(methodName);
        }

        if (returnClazz == Boolean.class || returnClazz == boolean.class)
        {
            return ConfigAdapter.this.getBoolean(methodName);
        }

        return null;
    }

    /**
     * Title: read Description:
     * 
     * @param conf
     * @since
     * @return Config
     */

    public abstract Config read(String conf);
    
    /**
     * Title:readProperty
     * 
     * @param propery path
     * @since
     * @return Config
     */
    public abstract Config readProperty(String propertyPath);
    
    
    
     /**
      * Title: getConfigMap <br/><br/>  
      * Description: get config map
      * @return property map
      */
    public Map<String, Object> getConfigMap()
    {
        return configMap;
    }
    
}
