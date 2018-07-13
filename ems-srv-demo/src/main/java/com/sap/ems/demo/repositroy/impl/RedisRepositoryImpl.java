
/**
 * Project Name:ems-srv-demo 
 * File Name:RedisRepositoryImpl.java <br/><br/>  
 * Description: RedisRepositoryImpl
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Feb 7, 2018 1:55:12 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.demo.repositroy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.sap.ems.demo.dto.CompanyDto;
import com.sap.ems.demo.repositroy.RedisRepository;

/**
 * ClassName: RedisRepositoryImpl <br/>
 * <br/>
 * Description: this is a simple implements.for more references：
 * https://docs.spring.io/spring-data/redis/docs/2.0.3.RELEASE/reference/html/
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Repository
public class RedisRepositoryImpl implements RedisRepository {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public Long deleteCompany(String guid) {
		Long count = redisTemplate.opsForHash().delete("Company", guid);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateCompany(CompanyDto dto) {
		redisTemplate.opsForHash().put("Company", dto.getGuid(), dto);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.sap.ems.demo.repositroy.RedisRepository#saveCompany()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveCompany(CompanyDto dto) {
		redisTemplate.opsForHash().put("Company", dto.getGuid(), dto);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.sap.ems.demo.repositroy.RedisRepository#getCompany()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CompanyDto getCompany(String guid) {
		return (CompanyDto) redisTemplate.opsForHash().get("Company", guid);
	}

}
