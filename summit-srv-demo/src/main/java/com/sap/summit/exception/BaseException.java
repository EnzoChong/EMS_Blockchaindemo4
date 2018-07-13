
/**
 * Project Name:ems-infrastructure 
 * File Name:BaseException.java <br/><br/>  
 * Description: The base exception for api response with messages and reference field
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jul 14, 2017 10:22:11 AM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.sap.summit.message.util.MessageUtil;


/**
 * ClassName: BaseException <br/>
 * Description: It is an exception with single message key and reference field which will be caught by the exception
 * handler and send a related message to frontend.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class BaseException extends Exception
{
    private static final long serialVersionUID = 1L;
    private String errMsgKey;
    private String ref_field;//for error info to frontend
    private final transient List<Object> params = new ArrayList<>();

    public BaseException(String msgKey, List<Object> params, String refField)
    {
        this.errMsgKey = msgKey;
        this.params.addAll(params);
        this.ref_field = refField;
    }

    public BaseException(String msgKdy)
    {
        this(msgKdy, Collections.emptyList(), "");
    }

    public BaseException(String msgKdy, List<Object> params)
    {
        this(msgKdy, params, "");
    }

    public BaseException(String msgKdy, String refField)
    {
        this(msgKdy, Collections.emptyList(), refField);
    }

    /**
     * {@inheritDoc} <br/>
     * Description: override the localized message getting logic
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage()
    {
        String errMsg;
        if (CollectionUtils.isEmpty(params))
        {
            errMsg = MessageUtil.getI18NMsgText(errMsgKey);
        }
        else
        {
            errMsg = MessageFormat.format(MessageUtil.getI18NMsgText(errMsgKey), params.toArray());
        }
        if (StringUtils.isEmpty(errMsg))
        {
            return errMsgKey;
        }
        return errMsg;
    }

    @Override
    public String getMessage()
    {
        return getLocalizedMessage();
    }

    public String getRefField()
    {

        return ref_field;
    }

    public void setRefField(String refField)
    {
        this.ref_field = refField;
    }
}
