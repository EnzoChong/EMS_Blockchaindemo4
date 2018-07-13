package com.sap.ems.multitenancy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sap.ems.exception.BaseRuntimeException;
import com.sap.ems.multitenancy.DataSourceHolder;
import com.sap.ems.multitenancy.DynamicDBDeploy;
import com.sap.ems.multitenancy.config.DynamicDBconfig;

/**
 * ClassName: DynamicDBAccessInterceptor <br/>
 * <br/>
 * Description: a interceptor to get token from request and set dataSource key
 * of this request thread
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
	private DynamicDBconfig config;

	/**
	 * Description: intercept every request to get JWTToken and determine the
	 * dataSource {@inheritDoc}
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

		logger.info("------------Simulation Tenant id------------" + tenant);

		logger.info("------------Simulation userId id------------" + tenant);

		if (null == tenant) {

			return true;

		} else {

			DynamicDBDeploy.setDataSource(tenant);
			request.getSession().setAttribute("userId", userId);

			return true;

		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		DataSourceHolder.clearDataSource();
	}
}
