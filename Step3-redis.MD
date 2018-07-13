# Overview
* Redis Visualization Tool

## Jedis implement

* Create company information in the redis to cache data

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/company/redisUtil

Example

Headers

```
{
  Content-Type:application/json
}
```
Body

```
{
	"guid":"50",
	"id":"50",
	"name":"Kevin",
	"address":"Chengdu"
}
```
* Get company via https://ems-srv-demo.cfapps.sap.hana.ondemand.com/company/redisUtil?key=50

Example

Headers
```
{
  Content-Type:application/json
}
```

## Spring data Redis 

* Create company information in the redis to cache data

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/company/redis

* Get company 

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/company/redis?key=50

## Set proxy from cloud foundry to the local

* Pre-requisite

	* ssh support is enabled in space 'Perf'
	```
	cf space-ssh-allowed Perf
	```
	* if ssh don't support ,so enable it
	```
	cf allow-space-ssh Perf
	```
	* Exactlt command ,please refer help
	```
	cf help -a
	```
* Check redis env information

```
cf env ems-srv-demo

"redis": [
   {
    "binding_name": null,
    "credentials": {
     "hostname": "10.11.241.63",
     "password": "k3-NgMzYbuzfZ0xl",
     "port": "43661",
     "ports": {
      "6379/tcp": "43661"
     }
    },
    "instance_name": "ems-srv-demo-redis",
    "label": "redis",
    "name": "ems-srv-demo-redis",
    "plan": "v3.0-dev-large",
    "provider": null,
    "syslog_drain_url": null,
    "tags": [
     "redis",
     "keyvalue"
    ],
    "volume_mounts": []
   }
  ],

cf ssh -N -T -L 6379:10.11.241.63:43661 ems-srv-demo

```

* Open redis client(Redis Desktop Manager)


	* Download Redis Desktop Manager:https://redisdesktop.com/download

![Redis Desktop Manager](/picture/Redis-desktop-manager.png "Optional title") 

	* Utilze  redos client
	
	redis-cli -h <host> -p <43661> -a <password>
	
	```
	redis-cli -h localhost -p 43661 -a k3-NgMzYbuzfZ0xl
	```
**parameter,please refer to above section**
