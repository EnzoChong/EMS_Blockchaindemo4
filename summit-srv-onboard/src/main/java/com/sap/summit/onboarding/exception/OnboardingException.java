
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:OnboardingException.java <br/><br/>  
  * Description: Exceptions for onboarding process.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jan 3, 2018 11:23:43 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.onboarding.exception;

  
 /**
  * ClassName: OnboardingException <br/><br/> 
  * Description: Exception for on boarding process.
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class OnboardingException extends RuntimeException
{

    
     /**  
      * serialVersionUID:Serialized for error.  
      * @see
      * @since
      */
    private static final long serialVersionUID = -1646429947643018361L;

    /**
     * create an instance: OnboardingException. 
     * Title: OnboardingException <br/><br/> 
     * Description: Throw exceptions while on/off boarding.
     */
    public OnboardingException()
    {
        super();
    }

    /**
     * create an instance: OnboardingException. 
     * Title: OnboardingException <br/><br/> 
     * Description: Throw on boarding exception with messages.
     * @param message
     */
    public OnboardingException(String message)
    {
        super(message);
    }

}
 