
  
 /**
  * Project Name:summit-srv-demo 
  * File Name:CallBlockChainAPIException.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Apr 30, 2018 2:14:38 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.demo.exception;

  
 /**
  * ClassName: CallBlockChainAPIException <br/><br/> 
  * Description: TODO
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class CallBlockChainAPIException extends Exception
{

    private static final long serialVersionUID = 1L;

    public CallBlockChainAPIException(String message)
    {
        super(message);
    }
    
    public CallBlockChainAPIException(String message,Throwable cause)
    {
        super(message, cause);
    }
}
 