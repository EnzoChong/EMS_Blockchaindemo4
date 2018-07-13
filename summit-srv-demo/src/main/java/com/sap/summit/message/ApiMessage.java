package com.sap.summit.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: ApiMessage <br/>
 * <br/>
 * Description: It is a generics class which define a response form to frontend, the generics is used to define specific
 * type of data. ApiMessage contains two variables including the generics data and messages list. If the request is GET
 * method, the ApiMessage will attach the related data and the generic message. If it is a POST/ PUT method, the
 * ApiMessage will attach the updated data and updating success or error messages.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class ApiMessage<T> implements Serializable
{

    /**
     * serialVersionUID: Serial api message for return.
     * 
     * @see
     * @since
     */
    private static final long serialVersionUID = -1037670533105471447L;

    // api message data
    private T data; //NOSONAR

    //message list
    private List<MessageResponse> messages = new ArrayList<>();

    /**
     * 
     * create an instance: ApiError. Title: ApiError <br/>
     * <br/>
     * Description: constructor without parameter
     */
    public ApiMessage()
    {
        super();
    }

    /**
     * 
     * create an instance: ApiError. Title: ApiError <br/>
     * <br/>
     * Description: constructor with parameters
     * 
     * @param messages
     */
    public ApiMessage(final List<MessageResponse> messages)
    {
        super();
        this.messages = messages;
    }

    /**
     * 
     * create an instance: ApiError. Title: ApiError <br/>
     * <br/>
     * Description: constructor with parameters
     * 
     * @param data
     * @param messages
     */
    public ApiMessage(final T data, final List<MessageResponse> messages)
    {
        super();
        this.data = data;
        this.messages = messages;
    }

    /**
     * Get the data
     * 
     * @return the data
     */
    public T getData()
    {

        return data;
    }

    /**
     * Set the data
     */
    public void setData(T data)
    {
        this.data = data;
    }

    public List<MessageResponse> getMessages()
    {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages)
    {
        this.messages = messages;
    }

    public void addMessage(MessageResponse message)
    {
        messages.add(message);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
