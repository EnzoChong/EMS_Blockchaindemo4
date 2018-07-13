package com.sap.summit.multitenancy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sap.summit.multitenancy.DataSourceHolder;
import com.sap.summit.multitenancy.DynamicDBDeploy;

/**
 * ClassName: DynamicDBAccessInterceptor <br/>
 * <br/>
 * Description: a interceptor to get token from request and set dataSource key of this request
 * thread
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Component
public class DynamicDBAccessInterceptor extends HandlerInterceptorAdapter {
  private final static Logger logger = LoggerFactory.getLogger(DynamicDBAccessInterceptor.class);

  @Autowired
  private Environment env;

  /**
   * Description: intercept every request to get JWTToken and determine the dataSource {@inheritDoc}
   * 
   * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse, java.lang.Object)
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // String JWTToken = request.getHeader("authorization");

    String tenant = request.getHeader("tenant");

    String userId = request.getHeader("userId");

    if (null == tenant) {

      return true;

    } else {

      if (null != env.getProperty("spring.config.name")
          || StringUtils.join(env.getActiveProfiles()).contains("local")) {
        tenant = request.getHeader("tenant");
      } else {
        tenant = StringUtils.isEmpty(tenant) ? "ems-block-chain" : tenant;
      }

      DynamicDBDeploy.setDataSource(tenant);
      request.getSession().setAttribute("userId", userId);

      return true;

    }

  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    DataSourceHolder.clearDataSource();
  }
}
