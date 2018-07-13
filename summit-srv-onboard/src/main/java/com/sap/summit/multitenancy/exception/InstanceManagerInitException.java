package com.sap.summit.multitenancy.exception;

/**
 * ClassName: InstanceManagerInitException <br/>
 * <br/>
 * Description: InstanceManagerInitException
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class InstanceManagerInitException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * create an instance: InstanceManagerInitException. Title: InstanceManagerInitException <br/>
     * <br/>
     * 
     * @param string
     */
    public InstanceManagerInitException(String message)
    {
        super(message);
    }

    public InstanceManagerInitException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
