
/**
 * Project Name:ems-srv-integration 
 * File Name:ProvisioningMQConfig.java <br/><br/>  
 * Description: Message Queue for provisioning.
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 5, 2018 1:11:15 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.summit.mq.constants.IMessageQueueConstants;
import com.sap.summit.mq.receiver.SyncRedisMQReceiver;

/**
 * ClassName: ProvisioningMQConfig <br/>
 * <br/>
 * Description: Configuration of message queue for provisioning.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */

@Configuration
public class SyncRedisConfig {
	@Bean
	public Queue syncRedisQueue() {
		return new Queue(IMessageQueueConstants.SYNC_REDIS_EXCHANGE_QUEUE, false);
	}

	@Bean
	public Binding provisioningBinding(@Qualifier("syncRedisQueue") Queue queue,
			@Qualifier("syncRedisExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(IMessageQueueConstants.SYNC_REDIS_EXCHANGE_ROUTING_KEY);
	}

	@Bean
	public MessageListenerAdapter syncRedisListenerAdapter(SyncRedisMQReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public SimpleMessageListenerContainer outboundContainer(ConnectionFactory connectionFactory,
			@Qualifier("syncRedisListenerAdapter") MessageListenerAdapter listenerAdapter) {
		
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(IMessageQueueConstants.SYNC_REDIS_EXCHANGE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

}
