
/**
 * Project Name:ems-infrastructure 
 * File Name:I18nMessageKeys.java <br/><br/>  
 * Description: Common i18n message keys constants
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Aug 14, 2017 10:33:34 AM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.onboarding.message.constant;

/**
 * ClassName: I18nMessageKeys <br/>
 * <br/>
 * Description: Common i18n message keys constants
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public interface I18nMessageKeys
{
    /* Messages for common exception handler */
    String EXCEPTION_HANDLER_404_NOT_FOUND = "EXCEPTION_HANDLER_404_NOT_FOUND";
    String EXCEPTION_HANDLER_TYPE_MISS_MATCH = "EXCEPTION_HANDLER_TYPE_MISS_MATCH";
    String EXCEPTION_HANDLER_MISS_REQUEST_PART = "EXCEPTION_HANDLER_MISS_REQUEST_PART";
    String EXCEPTION_HANDLER_METHOD_TYPE_NOT_SUPPORTED = "EXCEPTION_HANDLER_METHOD_TYPE_NOT_SUPPORTED";
    String EXCEPTION_HANDLER_MEDIA_TYPE_NOT_SUPPORTED = "EXCEPTION_HANDLER_MEDIA_TYPE_NOT_SUPPORTED";
    String MSG_EXCEPTION_TECH_ERROR = "MSG_EXCEPTION_TECH_ERROR";
    String MSG_APP_EXCEPTION_TECH_ERROR = "MSG_APP_EXCEPTION_TECH_ERROR";

    /* Messages for JPA validation field error exception handler */
    String VALIDATOR_INVALID_FIELD_INPUT = "VALIDATOR_INVALID_FIELD_INPUT";

    /* Messages for generating a general CRUD operation success message */
    String EMS_CRUD_SUCCESS_MESSAGE = "EMS_CRUD_SUCCESS_MESSAGE";

    /* Message for lock table */
    String OBJECT_LOCK_IS_USED_BY_OTHER = "OBJECT_LOCK_IS_USED_BY_OTHER";
    String OBJECT_UNLOCK_EXPIRED = "OBJECT_UNLOCK_EXPIRED";
    String OBJECT_LOCK_IS_USED_BY_OTHER_WITH_MULTIPLE_OBJECTS = "OBJECT_LOCK_IS_USED_BY_OTHER_WITH_MULTIPLE_OBJECTS";

}
