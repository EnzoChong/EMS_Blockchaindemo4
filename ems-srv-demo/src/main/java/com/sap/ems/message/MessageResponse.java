
/**
 * Project Name:ems-infrastructure
 * File Name:MessageResponse.java <br/><br/>  
 * Description: response message body
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jul 14, 2017 3:11:43 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.message;

import java.io.Serializable;

/**
 * ClassName: Message <br/>
 * <br/>
 * Description: It is a message form to frontend including message body, message type and reference fields.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class MessageResponse implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String message;
    private MessageType type;
    private String ref_field;

    public MessageResponse()
    {
        super();
    }
    
    public MessageResponse(final String message, final MessageType type)
    {
        this.message = message;
        this.type = type;
    }
    
    public MessageResponse(final String message)
    {
        this.message = message;
    }

    public MessageResponse(String message, MessageType type, String ref_field)
    {
        super();
        this.message = message;
        this.type = type;
        this.ref_field = ref_field;
    }

    public String getMessage()
    {

        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public MessageType getType()
    {

        return type;
    }

    public void setType(MessageType type)
    {
        this.type = type;
    }

    public String getRef_field()
    {

        return ref_field;
    }

    public void setRef_field(String ref_field)
    {
        this.ref_field = ref_field;
    }
}
