
/**
 * Project Name:ems-infrastructure 
 * File Name:EMSBaseExceptionHandler.java <br/><br/>  
 * Description: Intercept all common exception filters, it will be inherited by other exception handler of different handler.
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Aug 14, 2017 1:32:56 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sap.summit.message.ApiMessage;
import com.sap.summit.message.MessageResponse;
import com.sap.summit.message.MessageType;
import com.sap.summit.message.constant.I18nMessageKeys;
import com.sap.summit.message.util.MessageUtil;
import com.sap.summit.multitenancy.exception.InstanceManagerInitException;

/**
 * ClassName: EMSBaseExceptionHandler <br/>
 * <br/>
 * Description: Intercept all common exception filters, it will be inherited by other exception handler of different handler.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@ControllerAdvice
public class EMSBaseExceptionHandler extends ResponseEntityExceptionHandler
{
    private final static Logger logger = LoggerFactory.getLogger(EMSBaseExceptionHandler.class);
    @Value("${spring.application.name}")
    private String appName;
    
    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        final StringBuilder errors = new StringBuilder();
        for (final FieldError error : ex.getBindingResult().getFieldErrors())
        {
            errors.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors())
        {
            errors.append(error.getObjectName() + ": " + error.getDefaultMessage() + "\n");
        }

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(errors.toString(), MessageType.E, ""));
        messages.setData("");
        return this.handleExceptionInternal(ex, messages, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleBindException(org.springframework.validation.BindException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        final StringBuilder errors = new StringBuilder();
        for (final FieldError error : ex.getBindingResult().getFieldErrors())
        {
            errors.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors())
        {
            errors.append(error.getObjectName() + ": " + error.getDefaultMessage() + "\n");
        }

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(errors.toString(), MessageType.E, ""));
        messages.setData("");
        return handleExceptionInternal(ex, messages, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleTypeMismatch(org.springframework.beans.TypeMismatchException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        List<Object> params = new ArrayList<>();
        params.add(ex.getPropertyName());
        params.add(ex.getRequiredType());
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_TYPE_MISS_MATCH), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMissingServletRequestPart(org.springframework.web.multipart.support.MissingServletRequestPartException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        List<Object> params = new ArrayList<>();
        params.add(ex.getRequestPartName());
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_MISS_REQUEST_PART), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMissingServletRequestParameter(org.springframework.web.bind.MissingServletRequestParameterException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        List<Object> params = new ArrayList<>();
        params.add(ex.getParameterName());
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_MISS_REQUEST_PART), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Title: handleMethodArgumentTypeMismatch <br/>
     * <br/>
     * Description: Handle method argument type mismatch exception
     * 
     * @param ex
     * @param request
     * @return ResponseEntity with exception messages
     * @see
     * @since
     */
    @ExceptionHandler(
    { MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        List<Object> params = new ArrayList<>();
        params.add(ex.getName());
        params.add(ex.getRequiredType().getName());
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_TYPE_MISS_MATCH), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleNoHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_404_NOT_FOUND), ex.getHttpMethod(),
            ex.getRequestURL());

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleHttpRequestMethodNotSupported(org.springframework.web.HttpRequestMethodNotSupportedException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        final StringBuilder supportedMethods = new StringBuilder();
        ex.getSupportedHttpMethods().forEach(t -> supportedMethods.append(t + " "));

        List<Object> params = new ArrayList<>();
        params.add(ex.getMethod());
        params.add(supportedMethods);
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_METHOD_TYPE_NOT_SUPPORTED), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleHttpMediaTypeNotSupported(org.springframework.web.HttpMediaTypeNotSupportedException,
     *      org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
     *      org.springframework.web.context.request.WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request)
    {
        logger.info(ex.getClass().getName());

        final StringBuilder supportedMedias = new StringBuilder();
        ex.getSupportedMediaTypes().forEach(t -> supportedMedias.append(t + " "));

        List<Object> params = new ArrayList<>();
        params.add(ex.getContentType());
        params.add(supportedMedias.toString());
        final String error = MessageFormat.format(MessageUtil.getI18NMsgText(I18nMessageKeys.EXCEPTION_HANDLER_MEDIA_TYPE_NOT_SUPPORTED), params);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(error, MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Title: handleBaseException <br/>
     * <br/>
     * Description: Handle base Exception with reference field and message body to frontend
     * 
     * @param ex
     * @param request
     * @return ResponseEntity with exception messages
     * @see
     * @since
     */
    @ExceptionHandler(
    { BaseException.class })
    public ResponseEntity<Object> handleBaseException(final BaseException ex, final WebRequest request)
    {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(ex.getLocalizedMessage(), MessageType.E, ex.getRefField()));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Title: handleBaseRuntimeException <br/>
     * <br/>
     * Description: Handle base RuntimeException with reference field and message body to frontend
     * 
     * @param ex
     * @param request
     * @return ResponseEntity with exception messages
     * @see
     * @since
     */
    @ExceptionHandler(
    { BaseRuntimeException.class })
    public ResponseEntity<Object> handleBaseRuntimeException(final BaseRuntimeException ex, final WebRequest request)
    {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(ex.getLocalizedMessage(), MessageType.E, ex.getRefField()));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(
    { InstanceManagerInitException.class })
    public ResponseEntity<Object> handleInstanceManagerInitException(final InstanceManagerInitException ex, final WebRequest request)
    {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(ex.getLocalizedMessage(), MessageType.E));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Title: handleSaveException <br/><br/>  
     * Description: handle multiple messages exception for JPA validator a
     * @param ex
     * @return response entity
     * @since
     */
   @ExceptionHandler({MutiMessageException.class})
   public ResponseEntity<Object> handleSaveException(final MutiMessageException ex)
   {
       logger.error(Arrays.toString(ex.getStackTrace()), ex);
       ApiMessage<String> apiMsg = new ApiMessage<>(ex.getMessageList());
       return new ResponseEntity<>(apiMsg, HttpStatus.BAD_REQUEST);
   }

    // 500
    /**
     * Title: handleAll <br/>
     * <br/>
     * Description: Handle the other exceptions which are not handled specifically, <br/>
     * ex. NullPointer Exception, DB connection refused exception and so on
     * 
     * @param ex
     * @param request
     * @return ResponseEntity with exception messages
     * @see
     * @since
     */
    @ExceptionHandler(
    { Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request)
    {
        logger.info(ex.getClass().getName());
        logger.error(ex.getLocalizedMessage(), ex);
        
        ApiMessage<String> messages = new ApiMessage<>();
        messages.addMessage(new MessageResponse(MessageUtil.getI18NMsgText(I18nMessageKeys.MSG_APP_EXCEPTION_TECH_ERROR, Arrays.asList(appName)), MessageType.E, ""));
        messages.setData("");
        return new ResponseEntity<>(messages, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
