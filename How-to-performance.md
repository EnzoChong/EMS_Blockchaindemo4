# Overview
* Increasing Developer Productivity with the SAP JVM Tools:
http://sapjvm.wdf.sap.corp:1080/tools/ </br>
![Alt text](/picture/SAP_JVM_Install.png "Optional title") 
* Download visualvm:http://visualvm.github.io/download.html</br>
[attachment](https://github.wdf.sap.corp/EMS/SolutionDesign/blob/master/software/visualvm_139.zip "Title") inline link.
* Download CMDER:http://cmder.net/ or https://github.com/cmderdev/cmder/releases/tag/v1.3.4
![Alt text](/document/picture/CMDER.png "Optional title") 
* Download SAP JVM
https://tools.hana.ondemand.com/#cloud</br>
![Alt text](/picture/SAP_JVM.png "Optional title") 
* Configuration SAP JVM
  * Update Installed JREs
![Alt text](/picture/Installed_SAP_JVM.png "Optional title") 
  * Set VM Explorer in the Eclipse</br>
![Alt text](/picture/SetVmExplorer.png "Optional title") 
* Run Admin Service
![Alt text](/picture/RunningSprintBoot1.png "Optional title") 
![Alt text](/picture/RunningSprintBoot2.png "Optional title") 
![Alt text](/picture/RunningSprintBoot3.png "Optional title") 
**comments: .pathMapping(appPath);**
* http://localhost:9011/swagger-ui.html#/
## SowapUI
* Download:https://www.soapui.org/downloads/thank-you-for-downloading-soapui.html
## Visualvm
## Cloud Foundry Commands
## Grinder
## How to active Jcontrol and VsiualVm
* Cloud Foundry Commands
```

C:\Users\i325291
λ set http_proxy=http://proxy.wdf.sap.corp:8080

C:\Users\i325291
λ cf login
API endpoint: https://api.cf.sap.hana.ondemand.com

User ID> i325291

Password>
Authenticating...
OK

Select an org (or press enter to skip):
1. EMS
2. EMS-DEMO_USDEMO

Org> 2
Targeted org EMS-DEMO_USDEMO

Targeted space USDEMO-SPACES1



API endpoint:   https://api.cf.sap.hana.ondemand.com (API version: 2.99.0)
User:           i325291
Org:            EMS-DEMO_USDEMO
Space:          USDEMO-SPACES1

C:\Users\i325291
λ

```
* How to trigger GC with command
This page will guide you how to perform gc with cmd 
  * Execute: cf ssh <<>your application name></br>
  please make sure ssh is enabled for your application or space or org. if not, you can use cmd "cf enable-ssh <<your application name>> " to enable ssh for your application
```
C:\Users\i325291
λ cf ssh ems-admin-service
vcap@db02b93d-8e19-49e7-77f7-5e86:~$ /home/vcap/app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon\
>

   InstID/VmID       Pid       CPU[ms]   VmTag / Java arguments / working directory
+--------------+---------+-------------+--------------------------------------------

                      55       2635950   null.USDEMO-SPACES1.ems-admin-service
                                         org.springframework.boot.loader.JarLauncher
                                         /home/vcap/app

+--------------+---------+-------------+--------------------------------------------
$ force gcc
$ 
```
  * execute "exit" </br>
  * Trigger gc: execute "/home/vcap/app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid 55 -c 'force gc'"</br>
* How to use JConsole in CF
  * enable JMX</br>
  cf set-env <<Your Application>> JBP_CONFIG_JAVA_OPTS "{\"java_opts\":\" -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=5000 -Dcom.sun.management.jmxremote.rmi.port=5000 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+GCHistory -XX:+DumpGCHistoryOnOutOfMemory -XX:+DumpDetailedClassStatisticOnOutOfMemory -XX:+PrintConcurrentLocks -XX:LogGcMaxFileSize=5000000\"}"
```
C:\Users\i325291
λ cf env ems-admin-service

User-Provided:
JBP_CONFIG_JAVA_OPTS: {"java_opts":" -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=5000 -Dcom.sun.management.jmxremote.rmi.port=5000 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+GCHistory -XX:+DumpGCHistoryOnOutOfMemory -XX:+DumpDetailedClassStatisticOnOutOfMemory -XX:+PrintConcurrentLocks -XX:LogGcMaxFileSize=5000000"}
JBP_CONFIG_JMX: {"enabled": "true"}

C:\Users\i325291
λ cf ssh ems-admin-service
vcap@db02b93d-8e19-49e7-77f7-5e86:~$ netstat -ntlp
(Not all processes could be identified, non-owned process info
 will not be shown, you would have to be root to see it all.)
Active Internet connections (only servers)
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
tcp        0      0 0.0.0.0:41315           0.0.0.0:*               LISTEN      55/java
tcp        0      0 0.0.0.0:5000            0.0.0.0:*               LISTEN      55/java
tcp        0      0 0.0.0.0:2222            0.0.0.0:*               LISTEN      12/diego-sshd
tcp        0      0 0.0.0.0:8080            0.0.0.0:*               LISTEN      55/java
vcap@db02b93d-8e19-49e7-77f7-5e86:~$exit
```
**check whether the port 5000 is listening**
  * Open ssh tunnel</br>
```
C:\Users\i325291
λ cf ssh -N -T -L 5000:localhost:5000 ems-admin-service
```
  * Jconsole </br>
```
C:\Users\i325291
λ Jconsole localhost:5000
```
![Alt text](/picture/Jconsole.png "Optional title") </br>
[Wiki](https://wiki.wdf.sap.corp/wiki/display/SAPEMS/How+to+use+JConsole+in+CF "Title") inline link.</br>
* How to use VisualVM 
![Alt text](/picture/add-JMX.png "Optional title") 
![Alt text](/picture/VisualVM.png "Optional title")
## Performance Issu
* wiki
https://wiki.wdf.sap.corp/wiki/display/SAPEMS/Potential+performance+problems
## Helpful link
https://wiki.wdf.sap.corp/wiki/display/SAPEMS/Performance

