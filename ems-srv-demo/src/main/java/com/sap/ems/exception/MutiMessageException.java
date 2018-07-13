/**
 * Project Name:ems-infrastructure
 * File Name:MutiMessageException.java <br/><br/>  
 * Description: define a spec exception
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jul 5, 2017 11:15:04 AM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.exception;

import java.util.ArrayList;
import java.util.List;

import com.sap.ems.message.MessageResponse;

/**
 * ClassName: MutiMessageException <br/>
 * <br/>
 * Description: It is an exception with multiple messages and reference fields which will be caught by the exception
 * handler and send a related message to frontend. Now it is used to throw an exception by the
 * EMSRequestValidationIntercepter which will catch the validation errors in the DTOs.
 * 
 * @author SAP
 * @version
 * @since
 */
public class MutiMessageException extends RuntimeException
{
    /**
     * serialVersionUID:
     * 
     * @see
     * @since
     */
    private static final long serialVersionUID = 2188215790802610554L;
    private List<MessageResponse> messageList = new ArrayList<>(); //NOSONAR

    /**
     * 
     * create an instance: MutiMessageException. Title: MutiMessageException <br/>
     * <br/>
     * Description: constructor without parameter
     */
    public MutiMessageException()
    {
        super();
    }

    /**
     * 
     * create an instance: MutiMessageException. Title: MutiMessageException <br/>
     * <br/>
     * Description: constructor without parameter msg
     * 
     * @param msg
     *            error msg from i18n.properties
     */
    public MutiMessageException(String msg)
    {
        super(msg);
    }

    public MutiMessageException(final List<MessageResponse> messageList)
    {
        this.messageList = messageList;
    }

    /**
     * Get the messageList
     * 
     * @return the messageList
     */
    public List<MessageResponse> getMessageList()
    {
        return messageList;
    }

    /**
     * Set the messageList
     */
    public void setMessageList(final List<MessageResponse> messageList)
    {
        this.messageList = messageList;
    }

}
