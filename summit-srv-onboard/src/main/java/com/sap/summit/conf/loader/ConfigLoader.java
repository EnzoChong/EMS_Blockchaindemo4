
/**
 * Title: ConfigLoader.java
 * Package com.sap.csc.ems.conf.loader
 * Description:
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Mar 22, 2017 10:09:04 AM
 * @version V1.0
 */

package com.sap.summit.conf.loader;

import com.sap.summit.conf.Config;
import com.sap.summit.conf.adapter.ConfigAdapter;
import com.sap.summit.conf.adapter.PropertiesAdapter;
import com.sap.summit.conf.util.ClassUtils;

/**
 * ClassName: ConfigLoader Description:
 * @author SAP
 */

public class ConfigLoader //NOSONAR
{
    
     /**
      * Title: loadProperties
      * Description: 
      * @return Config 
      * @param conf
      */
     
    public static Config loadProperties(String conf)
    {
        return load(conf, PropertiesAdapter.class);
    }

    
     /**
      * Title: load
      * Description:
      * @return Config 
      * @param conf
      * @param adapter
      */
     
    public static Config load(String conf, Class<? extends ConfigAdapter> adapter)
    {
        if (null == conf || "".equals(conf))
        {
            return null;
        }

        if (null == adapter)
        {
            return null;
        }

        ConfigAdapter configAdapter = ClassUtils.newInstance(adapter);
        return configAdapter.read(conf);
    }
}
