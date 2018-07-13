
/**
 * Project Name:ems-infrastructure 
 * File Name:BaseRuntimeException.java <br/><br/>  
 * Description: The base runtime exception for api response with messages and reference field
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

import com.sap.summit.onboarding.multitenancy.util.MessageUtil;



/**
 * ClassName: BaseRuntimeException <br/>
 * Description: It is a runtime exception with single message key and reference field which will be caught by the
 * exception handler and send a related message to frontend.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class BaseRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private String errMsgKey;
    private String ref_field;//for error info to frontend
    private final transient List<Object> params = new ArrayList<>();

    public BaseRuntimeException(String msgKey, List<Object> params, String refField)
    {
        this.errMsgKey = msgKey;
        this.params.addAll(params);
        this.ref_field = refField;
    }

    public BaseRuntimeException(String msgKdy)
    {
        this(msgKdy, Collections.emptyList(), "");
    }

    public BaseRuntimeException(String msgKdy, List<Object> params)
    {
        this(msgKdy, params, "");
    }

    public BaseRuntimeException(String msgKdy, String refField)
    {
        this(msgKdy, Collections.emptyList(), refField);
    }

    public BaseRuntimeException()
    {
        super();
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
        if (params == null || params.isEmpty())
        {
            errMsg = MessageUtil.getI18NMsgText(errMsgKey);
        }
        else
        {
            errMsg = MessageFormat.format(MessageUtil.getI18NMsgText(errMsgKey), params.toArray());
        }
        if (errMsg == null)
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

    public String getErrMsgKey()
    {
        return errMsgKey;
    }

    public void setErrMsgKey(String errMsgKey)
    {
        this.errMsgKey = errMsgKey;
    }
}
