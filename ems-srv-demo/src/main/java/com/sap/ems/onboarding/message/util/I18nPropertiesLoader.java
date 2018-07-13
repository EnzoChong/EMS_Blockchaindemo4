
/**
 * Project Name:ems-infrastructure 
 * File Name:I18nPropertiesLoader.java <br/><br/>  
 * Description: load I18N property file
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jul 14, 2017 10:30:08 AM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.onboarding.message.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.sap.ems.conf.Config;
import com.sap.ems.conf.adapter.PropertiesAdapter;

/**
 * ClassName: I18nPropertiesLoader <br/>
 * <br/>
 * Description: It will load i18n files with different languages and store in maps. It will also provide functions to
 * get messages by different message keys and language context.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class I18nPropertiesLoader
{
    private final static Logger logger = LoggerFactory.getLogger(I18nPropertiesLoader.class);
    private static final Map<String, Config> I18N_MSG_MAP = new HashMap<>();
    private static final Map<String, Config> I18N_LABEL_MAP = new HashMap<>();
    private static final String PROPERTY_SUFFIX = "properties";
    private static final String I18N_PREFIX = "i18n";
    private static final String I18N_ORIGINAL = "original";
    private static final int LANGUAGE_LEN = 2;
    private static final String I18N_MESSAGES = "i18n/i18n_messages";
//    private static final String I18N_LABEL = "i18n/i18n_label";

    private I18nPropertiesLoader()
    {

    }

    /**
     * Description: static block to get I18N property file
     * 
     * @see
     * @since JDK 1.8
     */
    static
    {
        //load i18n messages
        initLoadingI18N(I18N_MSG_MAP, I18N_MESSAGES);
        //load i18n labels
//        initLoadingI18N(I18N_LABEL_MAP, I18N_LABEL);
    }

    /**
     * Description: get I18N property file method
     * 
     * @throws IOException
     * 
     * @see
     * @since JDK 1.8
     */
    private static void initLoadingI18N(Map<String, Config> i18nMap, String path)
    {
        i18nMap.clear();
        File rootDir = getResourceFolder(path);

        if (null != rootDir && null != rootDir.listFiles())
        {
            File[] files = rootDir.listFiles();
            
            for (File file : files)
            {
                if (file.isDirectory() || !file.exists() || !file.getName().endsWith(PROPERTY_SUFFIX) || !file.getName().startsWith(I18N_PREFIX))
                {
                    continue;
                }
                PropertiesAdapter adapter = new PropertiesAdapter();
                adapter.readProperty(file.getPath());

                String fileName = file.getName();
                if (fileName.startsWith(I18N_PREFIX + "_"))
                {
                    String prefix = I18N_PREFIX + "_";
                    int index = fileName.lastIndexOf(prefix) + prefix.length();
                    String language = fileName.substring(index, index + LANGUAGE_LEN);
                    i18nMap.put(language, adapter);
                }
                else if (fileName.startsWith(I18N_PREFIX))
                {
                    i18nMap.put(I18N_ORIGINAL, adapter);
                }
            }
        }
    }

    /**
     * Title: getI18nMsg <br/>
     * <br/>
     * Description: get I18N message by message key
     * 
     * @param msgKey
     * @return message
     * @throws IOException
     * @see
     * @since JDK 1.8
     */
    public static String getI18nMsg(String msgKey)
    {
        String language = LanguageHelper.getLanguage();
        if (I18N_MSG_MAP.containsKey(language) && null != I18N_MSG_MAP.get(language).getString(msgKey))
        {
            return I18N_MSG_MAP.get(language).getString(msgKey);
        }
        return I18N_MSG_MAP.get(I18N_ORIGINAL).getString(msgKey);
    }

    /**
     * Title: getI18nLabel <br/>
     * <br/>
     * Description: get I18N label by label key
     * 
     * @param labelKey
     * @return label description
     * @since JDK 1.8
     */
    public static String getI18nLabel(String labelKey)
    {
        String language = LanguageHelper.getLanguage();
        String labelVal;
        if (!(I18N_LABEL_MAP.containsKey(language)))
        {
            language = I18N_ORIGINAL;
        }

        if ((labelVal = I18N_LABEL_MAP.get(language).getString(labelKey)) != null)
        {
            return labelVal;
        }
        return "";
    }

    private static File getResourceFolder(String path)
    {
        File folder = null;
        try
        {
            folder = new ClassPathResource(path).getFile();
        }
        catch (IOException e)
        {
            logger.warn(e.getMessage());
        }

        return folder;
    }
}
