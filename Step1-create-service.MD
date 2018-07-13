# Overview
* Create managed-hana instance 
  * refer to https://github.wdf.sap.corp/Hands-On/srv-Instance-manager
* Create hdi-share instance 
  * refer to https://github.wdf.sap.corp/Hands-On/srv-hdi-share-eclipse

* Create redis instance
```
cf create-service redis v3.0-dev-large ems-srv-demo-redis
```
* Create rabbitmq instance
```
cf create-service rabbitmq v3.6-dev ems-srv-demo-mq
```
**Service instance are also created in the cockpit**



Useful link: http://cli.cloudfoundry.org/en-US/cf/

