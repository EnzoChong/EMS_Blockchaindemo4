/**
  * Project Name:ems-infrastructure-service 
  * File Name:LanguageHelper.java <br/><br/>  
  * Description: 
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Aug 3, 2017 4:36:30 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.onboarding.message.util;

import org.springframework.context.i18n.LocaleContextHolder;

/**
  * ClassName: LanguageHelper <br/><br/> 
  * Description: define a util to get language 
  * @author SAP
  * @version 
  * @since 
  */
public class LanguageHelper //NOSONAR
{

    /**
     * 
      * Title: getLanguage <br/><br/>  
      * Description: get local language
      * @return language type: like "en", "de" ...
      * @since
     */
    public static String getLanguage()
    {
        return LocaleContextHolder.getLocale().getLanguage();
    }
}
 