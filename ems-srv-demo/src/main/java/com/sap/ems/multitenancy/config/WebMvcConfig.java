package com.sap.ems.multitenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ClassName: WebMvcConfig <br/>
 * <br/>
 * Description config interceptor of controller
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private HandlerInterceptor dynamicDBAccessInterceptor;

    
     /**
      * {@inheritDoc} 
      * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
      */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(dynamicDBAccessInterceptor).excludePathPatterns("/callback/**");
    }
}
