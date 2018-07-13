# Overview

* RabbitMQ Visualization Tool

* Prerequisites
  * ems-srv-demo is already bind ems-srv-demo-mq service instance 
```
cf env ems-srv-demo

"rabbitmq": [
   {
    "binding_name": null,
    "credentials": {
     "hostname": "10.11.241.61",
     "password": "zguIZD_wIyvaRRhr",
     "port": "56594",
     "ports": {
      "15672/tcp": "40366",
      "5672/tcp": "56594"
     },
     "uri": "amqp://AstmF4FIM2zgtpde:zguIZD_wIyvaRRhr@10.11.241.61:56594",
     "username": "AstmF4FIM2zgtpde"
    },
    "instance_name": "ems-srv-demo-mq",
    "label": "rabbitmq",
    "name": "ems-srv-demo-mq",
    "plan": "v3.6-dev",
    "provider": null,
    "syslog_drain_url": null,
    "tags": [
     "rabbitmq",
     "mbus",
     "pubsub",
     "amqp"
    ],
    "volume_mounts": []
   }
  ],
```
  * proxy port to local 
```
cf ssh -N -L 16672:10.11.241.61:40366 ems-srv-demo
```
* Login RabbitMQ Management 
http://localhost:16672 

![Alt text](/picture/MQ.png "Optional title") 

