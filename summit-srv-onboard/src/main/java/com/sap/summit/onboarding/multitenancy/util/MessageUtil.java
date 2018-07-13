
/**
 * Project Name:ems-infrastructure 
 * File Name:MessageUtil.java <br/><br/>  
 * Description: Message util for i18n messages
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jul 14, 2017 10:27:49 AM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.onboarding.multitenancy.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

import com.sap.summit.message.ApiMessage;
import com.sap.summit.message.MessageResponse;
import com.sap.summit.message.MessageType;
import com.sap.summit.onboarding.message.constant.I18nMessageKeys;
import com.sap.summit.onboarding.message.util.I18nPropertiesLoader;


/**
 * ClassName: MessageUtil <br/>
 * <br/>
 * Description: MessageUtil provides some functions for getting messages or generating messages.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class MessageUtil
{
    
     /**
      * Title: getI18NMsgText <br/><br/>  
      * Description: get i18n message from related language i18n file
      * @param msgKey
      * @return
      * @see
      * @since
      */
    public static String getI18NMsgText(String msgKey)
    {
        String msg = I18nPropertiesLoader.getI18nMsg(msgKey);
        if (StringUtils.isEmpty(msg))
        {
            return msgKey;
        }

        return msg;
    }
    
    
     /**
      * Title: getI18NMsgText <br/><br/>  
      * Description: get i18n message with parameters from related language i18n file
      * @param msgKey
      * @param params
      * @return 
      * @see
      * @since
      */
    public static String getI18NMsgText(String msgKey, List<String> params)
    {
        return MessageFormat.format(getI18NMsgText(msgKey), params.toArray());
    }

    /**
     * A common method for generate the error response for field validation errors.
     * 
     * @param errors
     *            the field validation errors.
     * @return the {@link ApiMessage} entity which contains errors information.
     */
    public static <T> ApiMessage<T> generateErrorMessageResponse(List<FieldError> errors)
    {
        ApiMessage<T> message = new ApiMessage<>();
        if (CollectionUtils.isNotEmpty(errors))
        {
            for (FieldError error : errors)
            {
                String errorMessage = getI18NMsgText(error.getDefaultMessage());
                if (StringUtils.isEmpty(errorMessage))
                {
                    errorMessage = error.getDefaultMessage();
                }
                MessageResponse response = new MessageResponse(errorMessage, MessageType.E, error.getField());
                message.addMessage(response);
            }
        }
        return message;
    }

    /**
     * A common method for generate the error response for field validation errors.
     * 
     * @param errors
     *            the field validation errors.
     * @return the {@link ApiMessage} entity which contains errors information.
     */
    public static <T> ApiMessage<T> generateErrorMessageResponse(String errorMessage, String field)
    {
        ApiMessage<T> message = new ApiMessage<>();
        MessageResponse response = new MessageResponse(errorMessage, MessageType.E, field);
        message.addMessage(response);

        return message;
    }

    /**
     * Title: constructMsgResList Description: construct error message by text, message type and field
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param text
     * @param type
     * @param refField
     * @return return error messages
     */
    public static List<MessageResponse> constructMsgResList(String text, MessageType type, String refField)
    {
        List<MessageResponse> messageList = new ArrayList<>();
        MessageResponse message = new MessageResponse(text, type, refField);
        messageList.add(message);
        return messageList;
    }

    /**
     * 
     * Title: getSuccessMessage Description: This message method used to return success message to frontend
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param data
     * @return ApiMessage instance
     */
    public static <T> ApiMessage<T> getSuccessMessage(T data)
    {
        return getSuccessMessage(data, "");
    }

    /**
     * Title: getSuccessMessage <br/>
     * <br/>
     * Description: his message method used to return success message to frontend with ref_field
     * 
     * @param data
     * @param ref_field
     * @return ApiMessage instance
     * @see
     * @since
     */
    public static <T> ApiMessage<T> getSuccessMessage(T data, String ref_field)
    {
        ApiMessage<T> apiMessage = new ApiMessage<>();
        apiMessage.setData(data);
        MessageResponse mr = new MessageResponse();
        String successMsg = MessageUtil.getI18NMsgText(I18nMessageKeys.EMS_CRUD_SUCCESS_MESSAGE);
        mr.setMessage(successMsg);
        mr.setType(MessageType.S);
        mr.setRef_field(ref_field);
        List<MessageResponse> messageList = new ArrayList<>();
        messageList.add(mr);
        apiMessage.setMessages(messageList);
        return apiMessage;
    }

    /**
     * Title: getSuccessMessage <br/>
     * <br/>
     * Description: his message method used to return success message to frontend with ref_field and message key.
     * 
     * @param data
     * @param ref_field
     * @return ApiMessage instance
     * @see
     * @since
     */
    public static <T> ApiMessage<T> getSuccessMessage(T data, String ref_field, String messageKey)
    {
        ApiMessage<T> apiMessage = new ApiMessage<>();
        apiMessage.setData(data);
        MessageResponse mr = new MessageResponse();
        String successMsg = MessageUtil.getI18NMsgText(messageKey);
        mr.setMessage(successMsg);
        mr.setType(MessageType.S);
        mr.setRef_field(ref_field);
        List<MessageResponse> messageList = new ArrayList<>();
        messageList.add(mr);
        apiMessage.setMessages(messageList);
        return apiMessage;
    }

    /**
     * Title: constructApiMsg <br/>
     * <br/>
     * Description:
     * 
     * @param data
     * @param text
     * @param type
     * @return
     * @see
     * @since
     */
    public static ApiMessage constructApiMsg(Object data, String text, MessageType type)
    {
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setData(data);
        apiMessage.setMessages(constructMsgResList(text, type));
        return apiMessage;
    }

    /**
     * Title: constructMsgResList Description: construct error message by text, message type
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param text
     * @param type
     * @return return error messages
     */
    public static List<MessageResponse> constructMsgResList(String text, MessageType type)
    {
        List<MessageResponse> messageList = new ArrayList<>();
        MessageResponse message = new MessageResponse(text, type);
        messageList.add(message);
        return messageList;
    }
}
