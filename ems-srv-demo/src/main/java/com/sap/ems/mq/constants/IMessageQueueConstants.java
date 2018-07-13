
package com.sap.ems.mq.constants;

/**
 * ClassName: IMessageQueueConstants <br/>
 * <br/>
 * Description: Message queue constants for the configuration keys, including queue names, exchange names and routing
 * keys
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public interface IMessageQueueConstants
{
    //exchange
    String SYNC_REDIS_EXCHANGE = "sync-redis-exchange";

    //queue names
    String INBOUND_PROCESS_WORKFLOW_QUEUE = "inbound-process-workflow-queue";

    String INBOUND_PROCESS_COMMIT_QUEUE = "inbound-process-commit-queue";
    String INBOUND_PROCESS_TRIGGER_OUTBOUND_QUEUE = "inbound-process-trigger-outbound-queue";
    String SYNC_REDIS_EXCHANGE_QUEUE = "sync-redis-exchange-queue"; 
    
    //routing key
    String INBOUND_PROCESS_WORKFLOW_ROUTING_KEY = "inbound-process-workflow-routingKey";

    String INBOUND_PROCESS_COMMIT_ROUTING_KEY = "inbound-process-commit-routingKey";
    String INBOUND_PROCESS_TRIGGER_OUTBOUND_ROUTING_KEY = "inbound-process-trigger-outbound-routingKey";
    String SYNC_REDIS_EXCHANGE_ROUTING_KEY = "sync-redis-exchange-routingKey"; 

}
