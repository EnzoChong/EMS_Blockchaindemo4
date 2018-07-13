
/**
 * Title: PropertiesAdapter.java
 * Package com.sap.csc.ems.conf.adapter
 * Description:
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Mar 21, 2017 5:43:47 PM
 * @version V1.0
 */

package com.sap.ems.conf.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.sap.ems.conf.Config;

/**
 * ClassName: PropertiesAdapter Description:
 * 
 * @author SAP
 */

public class PropertiesAdapter extends ConfigAdapter
{

    /*
     * Title: read Description: read config file
     * 
     * @param conf
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.adapter.ConfigAdapter#read(java.lang.String)
     */

    @Override
    public Config read(String conf)
    {
        InputStream inputSource = null;
        try
        {        
            inputSource = Thread.currentThread().getContextClassLoader().getResourceAsStream(conf);
            parseProperty(inputSource );
            return this;
        }
        catch (FileNotFoundException fnfe)
        {
        }
        catch (IOException e)
        {
        }
        finally
        {
            try
            {
                inputSource.close();
            }
            catch (IOException e)
            {
                 e.printStackTrace();  
            }
        }

        return null;

    }

    /*
     * Title: read Description: read property file
     * 
     * @param propertyPath
     * 
     * @return (descrip the param)
     * 
     * @see com.sap.csc.ems.conf.adapter.ConfigAdapter#read(java.lang.String)
     */

    @Override
    public Config readProperty(String propertyPath)
    {
        if (null == propertyPath)
        {
            return null;
        }
        try (InputStream inputStream = new FileInputStream(propertyPath))
        {
            parseProperty(inputStream);
            return this;
        }
        catch (FileNotFoundException fnfe)
        {
        }
        catch (IOException e)
        {
        }
        return null;
    }

    private void parseProperty(InputStream inputStream) throws IOException
    {
        Properties props = new Properties();
        if (null != inputStream)
        {
            props.load(inputStream);
        }
        Set<Entry<Object, Object>> set = props.entrySet();
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        while (it.hasNext())
        {
            Entry<Object, Object> entry = it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString().trim();
            String fulKey = getWildKey(value);
            if (null != fulKey && null != props.get(fulKey))
            {
                String fuValue = props.get(fulKey).toString();
                value = value.replaceAll("\\$\\{" + fulKey + "\\}", fuValue);
            }
            configMap.put(key, value);
        }
    }

    private String getWildKey(String str)
    {
        if (null != str && str.indexOf("${") != -1)
        {
            int start = str.indexOf("${");
            int end = str.indexOf('}');
            if (start != -1 && end != -1)
            {
                return str.substring(start + 2, end);
            }
        }
        return null;
    }
}
