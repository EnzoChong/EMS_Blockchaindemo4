
/**
 * Project  
 * File Name: 
 * Description: 
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 5, 2018 1:44:29 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.mq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.ems.mq.constants.IMessageQueueConstants;
import com.sap.ems.mq.dto.MessageDto;

/**
 * ClassName: 
 * <br/>
 * Description:
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Component
public class SyncRedisSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMsg(MessageDto message) {
		rabbitTemplate.convertAndSend(IMessageQueueConstants.SYNC_REDIS_EXCHANGE,
				IMessageQueueConstants.SYNC_REDIS_EXCHANGE_ROUTING_KEY, message);
	}
}
