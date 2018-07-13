
/**
 * Project Name:ems-srv-demo 
 * File Name:DemoController.java <br/><br/>  
 * Description: DemoController
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 31, 2018 3:50:43 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ems.demo.dto.CompanyDto;
import com.sap.ems.demo.repositroy.RedisRepository;
import com.sap.ems.demo.service.DemoService;
import com.sap.ems.mq.sender.SyncRedisSender;
import com.sap.ems.redis.RedisUtil;

/**
 * ClassName: DemoController <br/>
 * <br/>
 * Description: DemoController
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@RestController
@RequestMapping(value = "/company")
public class CompanyController
{
    @Autowired
    private DemoService demoService;
    
    @Autowired
    private RedisRepository redisRepositoryImpl;
    private RedisUtil redisUtil = RedisUtil.getInstence();

    @GetMapping("/all")
    public List<CompanyDto> getAllCompanies()
    {
        return demoService.getAll();
    }

    @GetMapping("/{id}")
    public List<CompanyDto> getCompany(@PathVariable("id") String id)
    {
        return demoService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto)
    {
        return demoService.create(companyDto);
    }

    @DeleteMapping("/{id}")
    public List<CompanyDto> deleteCompany(@PathVariable("id") String id)
    {
        return demoService.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompanyDto updateCompany(@RequestBody CompanyDto companyDto)
    {
        return demoService.update(companyDto);
    }

    @PostMapping("redis")
    public String setRedis(@RequestBody CompanyDto companyDto)
    {
        redisRepositoryImpl.saveCompany(companyDto);
        return "OK";
    }

    @GetMapping("redis")
    public CompanyDto getRedis(@RequestParam("key") String key)
    {
        return redisRepositoryImpl.getCompany(key);
    }
    @GetMapping("redisUtil")
    public CompanyDto getRedisUtil(@RequestParam("key") String key)
    {
        return (CompanyDto) redisUtil.hget("Company", key);
    }

    @PostMapping("redisUtil")
    public String setRedisUtil(@RequestBody CompanyDto companyDto)
    {
        redisUtil.hset("Company", companyDto.getGuid(), companyDto, true);
        return "OK";
    }
}
