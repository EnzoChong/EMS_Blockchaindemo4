
/**
 * Title: ClassUtils.java
 * Package com.sap.csc.ems.conf.util
 * Description:
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Mar 22, 2017 10:12:11 AM
 * @version V1.0
 */

package com.sap.ems.conf.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ClassName: ClassUtils Description:
 * 
 * @author SAP
 */

public class ClassUtils //NOSONAR
{
    private final static Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * Title: createObject Description:
     * 
     * @return Object
     * @param clazz
     */
    public static Object createObject(Class<?> clazz)
    {
        try
        {
            return clazz.newInstance();
        }
        catch (InstantiationException e)
        {
            logger.error("instance exception in processing!", e);
        }
        catch (IllegalAccessException e)
        {
            logger.error("illeagel accessing exception in processing!", e);
        }

        return null;
    }

    /**
     * Title: newInstance Description:
     * 
     * @return T
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz)
    {
        try
        {
            Object object = clazz.newInstance();
            return (T) object;
        }
        catch (InstantiationException e)
        {
            logger.error("instance exception in processing!", e);
        }
        catch (IllegalAccessException e)
        {
            logger.error("illeagel accessing exception in processing!", e);
        }

        return null;
    }
    /**
      * Title: getAllFields <br/><br/>  
      * Description: get all fields from class
      * @param boClazz
      * @return List
      * @since
     */
    public static List<Field> getAllFields(Class<?> boClazz)
    {
        List<Field> fieldList = new ArrayList<>();
        try
        {
            while (null != boClazz)
            {
                Field[] fieldArray = boClazz.getDeclaredFields();
                for (Field field : fieldArray)
                {
                    fieldList.add(field);
                }
                boClazz = boClazz.getSuperclass(); //NOSONAR
            }
        }
        catch (SecurityException e)
        {
            logger.error("SecurityException happens.", e);
        }
        return fieldList;
    }

    /**
     * 
      * Title: getAllGetMethods <br/><br/>  
      * Description: all methods from model, getxxxx,setxxx,....
      * @param boClazz
      * @param prefix
      * @return List
      * @since
     */
    public static List<Method> getAllPrefixMethods(Class<?> boClazz, String prefix)
    {
        List<Method> methodList = new ArrayList<>();
        try
        {
            while (null != boClazz)
            {
                Method[] methodArray = boClazz.getDeclaredMethods();
                for (Method method : methodArray)
                {
                    if(method.getName().startsWith(prefix))
                    {
                        methodList.add(method);
                    }
                }
                boClazz = boClazz.getSuperclass(); //NOSONAR
            }
        }
        catch (SecurityException e)
        {
            logger.error("SecurityException happens.", e);
        }
        return methodList;
    }
}
