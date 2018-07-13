# Overview
**Database explore** </br>

https://hana-cockpit.cfapps.sap.hana.ondemand.com/sap/hana/cst/catalog/index.html</br>
All database container are managed by instance manager

![instance manager](/picture/Instance-manager-v1.png "Optional title") 

* Create database container in the tenant1db

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/callback/v1.0/multi-tenancy/perf-dev-t1

Example 

container id = perf-dev-t1

body

```
{
  "dataBase" : "8d6caa9e-7f69-44c1-9860-3dd7e9118b2c:tenant1db"
}
```


https://ems-srv-demo.cfapps.sap.hana.ondemand.com/callback/v1.0/multi-tenancy/perf-dev-t2


Example 

container id = perf-dev-t2

body

```
{
  "dataBase" : "8d6caa9e-7f69-44c1-9860-3dd7e9118b2c:tenant2db"
}
```


![Check](/picture/Database-on-boarding-check.png "Optional title") 


* Get database container in the tenant1db

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/callback/v1.0/multi-tenancy/perf-dev-t1

Example

container id = perf-dev-t1

* Get all database container 

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/callback/v1.0/multi-tenancy/all

* Delete database container  

https://ems-srv-demo.cfapps.sap.hana.ondemand.com/callback/v1.0/multi-tenancy/perf-dev-t1

Example

container id = perf-dev-t1

**csc-ems-t1 is our ems database container ,so you dont' delete it**
