
package com.sap.summit.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.summit.mq.constants.IMessageQueueConstants;

/**
 * ClassName: <br/>
 * <br/>
 * Description: 
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Configuration
public class SyncRedisDefine
{
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new JsonMessageConverter());
        return template;
    }

    @Bean
    public DirectExchange syncRedisExchange()
    {
        return new DirectExchange(IMessageQueueConstants.SYNC_REDIS_EXCHANGE);
    }

}
