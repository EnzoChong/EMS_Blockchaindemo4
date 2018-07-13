
  
 /**
  * Project Name:com.sap.summit.onboarding.callback 
  * File Name:DepolyDBClientConfig.java <br/><br/>  
  * Description: feign config
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Jun 23, 2017 2:08:51 PM
  * @version 
  * @see
  * @since 
  */
  package com.sap.summit.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

/**
  * ClassName: DepolyDBClientConfig <br/><br/> 
  * Description: 
  * @author SAP
  * @version 
  * @see
  * @since 
  */
@Configuration
public class DepolyDBClientFeignConfig
{
    @Value("${hdi_dynamic_deploy_user}")
    private String username;
    @Value("${hdi_dynamic_deploy_password}")
    private String password;
    
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
    
    public String getUsername()
    {
    
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
    
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
 