
/**
 * Project Name:ems-srv-integration 
 * File Name:OutboundMQReceiver.java <br/><br/>  
 * Description: Receiver for handling with out bound message queue.
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 5, 2018 1:25:07 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.mq.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.summit.demo.dto.CompanyDto;
import com.sap.summit.demo.repositroy.RedisRepository;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * ClassName: OutboundMQReceiver <br/>
 * <br/>
 * Description: Receiver for handling with out bound message queue.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Component
public class SyncRedisMQReceiver implements MessageListener
{

    @Autowired
    private RedisRepository redisRepositoryImpl;
    
    private CompanyDto company;
    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.amqp.core.MessageListener#onMessage(org.springframework.amqp.core.Message)
     */
    @Override
    public void onMessage(Message message)
    
    {
//        ObjectMapper objectMapper = new ObjectMapper();

        JSONObject messageBody = JSONObject.fromObject(new String(message.getBody()));
        String id = messageBody.get("id") instanceof JSONNull ? null : messageBody.getString("id");
        String name = messageBody.get("name") instanceof JSONNull ? null : messageBody.getString("name");
        String address = messageBody.get("address") instanceof JSONNull ? null : messageBody.getString("address");
        String guid = messageBody.get("guid") instanceof JSONNull ? null : messageBody.getString("guid");
        
        company = new CompanyDto();
        
        company.setGuid(guid);
        company.setId(id);
        company.setName(name);
        company.setAddress(address);
        
        redisRepositoryImpl.saveCompany(company);
        
    }

}
