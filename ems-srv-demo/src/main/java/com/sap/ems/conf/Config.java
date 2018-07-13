
/**
 * Title: Config.java
 * Package com.sap.csc.ems.conf
 * Description:
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Mar 21, 2017 5:15:23 PM
 * @version V1.0
 */

package com.sap.ems.conf;

/**
 * ClassName: Config Description:
 * 
 * @author SAP
 */

public interface Config
{

    /**
     * Title: getString Description: get String value of the key
     * @return String null or String
     * @param key
     */

    String getString(String key);

    Integer getInt(String key);

    Long getLong(String key);

    Boolean getBoolean(String key);

    Double getDouble(String key);

    <T> T get(Class<T> type);
}
