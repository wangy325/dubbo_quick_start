### 记录dubbo + zookeeper入门demo遇到的一些问题：

[official quick start reference](http://dubbo.apache.org/en-us/docs/user/preface/background.html)

#### 关于`jar`包的引入

> dubbo.version=2.6.5; zookeeper.version=3.4.13

```pom
<dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>${curator.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
            <version>${validation-api.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-client</artifactId>
            <version>${curator-client.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```
如不引入上述jar包依赖, 那么provider启动直接报异常:

```
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/curator/RetryPolicy
	at com.alibaba.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter.connect(CuratorZookeeperTransporter.java:27)
	at com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter$Adaptive.connect(ZookeeperTransporter$Adaptive.java)
	at com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry.<init>(ZookeeperRegistry.java:69)
	at com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistryFactory.createRegistry(ZookeeperRegistryFactory.java:38)
	at com.alibaba.dubbo.registry.support.AbstractRegistryFactory.getRegistry(AbstractRegistryFactory.java:96)
	at com.alibaba.dubbo.registry.RegistryFactory$Adaptive.getRegistry(RegistryFactory$Adaptive.java)
	at com.alibaba.dubbo.registry.integration.RegistryProtocol.getRegistry(RegistryProtocol.java:203)
	at com.alibaba.dubbo.registry.integration.RegistryProtocol.export(RegistryProtocol.java:137)
	at com.alibaba.dubbo.rpc.protocol.ProtocolListenerWrapper.export(ProtocolListenerWrapper.java:55)
	at com.alibaba.dubbo.qos.protocol.QosProtocolWrapper.export(QosProtocolWrapper.java:60)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper.export(ProtocolFilterWrapper.java:98)
	at com.alibaba.dubbo.rpc.Protocol$Adaptive.export(Protocol$Adaptive.java)
	at com.alibaba.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol(ServiceConfig.java:513)
	at com.alibaba.dubbo.config.ServiceConfig.doExportUrls(ServiceConfig.java:358)
	at com.alibaba.dubbo.config.ServiceConfig.doExport(ServiceConfig.java:317)
	at com.alibaba.dubbo.config.ServiceConfig.export(ServiceConfig.java:216)
	at com.alibaba.dubbo.config.spring.ServiceBean.export(ServiceBean.java:291)
	at com.alibaba.dubbo.config.spring.ServiceBean.onApplicationEvent(ServiceBean.java:131)
	at com.alibaba.dubbo.config.spring.ServiceBean.onApplicationEvent(ServiceBean.java:53)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:393)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:347)
	at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:883)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:546)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:139)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:83)
	at wy.dubbo.provider.Provider.main(Provider.java:18)
Caused by: java.lang.ClassNotFoundException: org.apache.curator.RetryPolicy
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 29 more
```
这里需要弄明白的是, `org.apache.curator`这个包在dubbo启动中扮演的角色.

当把`dubbo.version`替换换成2.5.3之后,不需要引入上述依赖便可成功注册provider

#### provider成功注册日志

成功运行`wy.dubbo.provider.Provider.java`后的启动日志信息(部分):

```
[13/03/19 11:49:00:000 CST] main  INFO zookeeper.ZooKeeper: Initiating client connection, connectString=localhost:2181 sessionTimeout=30000 watcher=org.I0Itec.zkclient.ZkClient@69fb6037
[13/03/19 11:49:00:000 CST] main  INFO zkclient.ZkClient: Waiting for keeper state SyncConnected
[13/03/19 11:49:00:000 CST] main-SendThread(localhost:2181)  INFO zookeeper.ClientCnxn: Opening socket connection to server localhost/0:0:0:0:0:0:0:1:2181. Will not attempt to authenticate using SASL (unknown error)
[13/03/19 11:49:00:000 CST] main-SendThread(localhost:2181)  INFO zookeeper.ClientCnxn: Socket connection established to localhost/0:0:0:0:0:0:0:1:2181, initiating session
[13/03/19 11:49:00:000 CST] main-SendThread(localhost:2181)  INFO zookeeper.ClientCnxn: Session establishment complete on server localhost/0:0:0:0:0:0:0:1:2181, sessionid = 0x10051bb506d0002, negotiated timeout = 30000
[13/03/19 11:49:00:000 CST] main-EventThread  INFO zkclient.ZkClient: zookeeper state changed (SyncConnected)
[13/03/19 11:49:00:000 CST] main  INFO zookeeper.ZookeeperRegistry:  [DUBBO] Register: dubbo://192.168.11.107:20880/wy.dubbo.api.DemoService?anyhost=true&application=demo-provider&dubbo=2.5.3&interface=wy.dubbo.api.DemoService&methods=getPerssions&pid=14872&side=provider&timestamp=1552448938877, dubbo version: 2.5.3, current host: 127.0.0.1
[13/03/19 11:49:00:000 CST] main  INFO zookeeper.ZookeeperRegistry:  [DUBBO] Subscribe: provider://192.168.11.107:20880/wy.dubbo.api.DemoService?anyhost=true&application=demo-provider&category=configurators&check=false&dubbo=2.5.3&interface=wy.dubbo.api.DemoService&methods=getPerssions&pid=14872&side=provider&timestamp=1552448938877, dubbo version: 2.5.3, current host: 127.0.0.1
[13/03/19 11:49:00:000 CST] main  INFO zookeeper.ZookeeperRegistry:  [DUBBO] Notify urls for subscribe url provider://192.168.11.107:20880/wy.dubbo.api.DemoService?anyhost=true&application=demo-provider&category=configurators&check=false&dubbo=2.5.3&interface=wy.dubbo.api.DemoService&methods=getPerssions&pid=14872&side=provider&timestamp=1552448938877, urls: [empty://192.168.11.107:20880/wy.dubbo.api.DemoService?anyhost=true&application=demo-provider&category=configurators&check=false&dubbo=2.5.3&interface=wy.dubbo.api.DemoService&methods=getPerssions&pid=14872&side=provider&timestamp=1552448938877], dubbo version: 2.5.3, current host: 127.0.0.1
org.springframework.context.support.ClassPathXmlApplicationContext@ee7d9f1: here
Provider started.
```

以及zookeeper控制台的输出(部分)
```
2019-03-13 11:49:00,448 [myid:] - INFO  [NIOServerCxn.Factory:0.0.0.0/0.0.0.0:2181:NIOServerCnxnFactory@215] - Accepted socket connection from /0:0:0:0:0:0:0:1:
11701                                                                                                                                                           
2019-03-13 11:49:00,458 [myid:] - INFO  [NIOServerCxn.Factory:0.0.0.0/0.0.0.0:2181:ZooKeeperServer@949] - Client attempting to establish new session at /0:0:0:0
:0:0:0:1:11701                                                                                                                                                  
2019-03-13 11:49:00,470 [myid:] - INFO  [SyncThread:0:ZooKeeperServer@694] - Established session 0x10051bb506d0002 with negotiated timeout 30000 for client /0:0
:0:0:0:0:0:1:11701                                                                                                                                              
2019-03-13 11:49:00,505 [myid:] - INFO  [ProcessThread(sid:0 cport:2181)::PrepRequestProcessor@653] - Got user-level KeeperException when processing sessionid:0
x10051bb506d0002 type:create cxid:0x1 zxid:0xe txntype:-1 reqpath:n/a Error Path:/dubbo Error:KeeperErrorCode = NodeExists for /dubbo                           
2019-03-13 11:49:00,531 [myid:] - INFO  [ProcessThread(sid:0 cport:2181)::PrepRequestProcessor@653] - Got user-level KeeperException when processing sessionid:0
x10051bb506d0002 type:create cxid:0x2 zxid:0xf txntype:-1 reqpath:n/a Error Path:/dubbo/wy.dubbo.api.DemoService Error:KeeperErrorCode = NodeExists for /dubbo/w
y.dubbo.api.DemoService                                                                                                                                         
```
以上日志说明provider在zookeeper上注册成功，如果其他配置成功的话，那么启动`wy.dubbo.consumer.Consumer.java`后应该可以正常看见调用结果:

```
log4j:WARN No appenders could be found for logger (org.springframework.core.env.StandardEnvironment).
log4j:WARN Please initialize the log4j system properly.
[Permission_9, Permission_10, Permission_11]
```

#### 使用`multicast`作为注册中心

启动provider有一个问题：https://github.com/apache/dubbo/issues/2423

```
Caused by: java.net.SocketException: Can't assign requested address
	at java.net.PlainDatagramSocketImpl.join(Native Method)
	at java.net.AbstractPlainDatagramSocketImpl.join(AbstractPlainDatagramSocketImpl.java:178)
	at java.net.MulticastSocket.joinGroup(MulticastSocket.java:323)
	at com.alibaba.dubbo.registry.multicast.MulticastRegistry.<init>(MulticastRegistry.java:90)
	... 23 more
```
按照配置，为程序添加运行参数`-Djava.net.preferIPv4Stack=true`可解决问题

#### 在ubuntu开发环境下本地调试provider启动异常

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "0:0:0:0:0:0:1:20880"
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:580)
	at java.lang.Integer.parseInt(Integer.java:615)
	at com.alibaba.dubbo.common.URL.valueOf(URL.java:239)
	at com.alibaba.dubbo.config.ServiceConfig.exportLocal(ServiceConfig.java:503)
	at com.alibaba.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol(ServiceConfig.java:465)
	at com.alibaba.dubbo.config.ServiceConfig.doExportUrls(ServiceConfig.java:281)
	at com.alibaba.dubbo.config.ServiceConfig.doExport(ServiceConfig.java:242)
	at com.alibaba.dubbo.config.ServiceConfig.export(ServiceConfig.java:143)
	at com.alibaba.dubbo.config.spring.ServiceBean.onApplicationEvent(ServiceBean.java:109)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:393)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:347)
	at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:883)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:546)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:139)
	at org.springframework.context.support.ClassPathXmlApplicationContext.<init>(ClassPathXmlApplicationContext.java:83)
	at wy.dubbo.provider.Provider.main(Provider.java:20)
```

需要在`provider.xml`中将
```xml
 <dubbo:protocol name="dubbo" port="20880"/>
```
替换为
```xml
 <dubbo:protocol name="dubbo" port="20880" host="192.168.1.3"/>
```
其中host的值是本地ip

#### dubbo模块的版本和dubbo-admin的版本无关, 二者可以独立运行并且使用admin对服务进行监控

目前关于dubbo的版本以及duboo-admin的版本有很大更新了。破司服务器dubbo版本以及dubbo-admin分别是2.5.3和2.6.0。

gitHub上面的版本做了大量更新，并且已经在孵化新版本的admin

老版本的[dubbo-admin](https://github.com/apache/incubator-dubbo),在2.5.x的dubbo项目中存在这个`dubbo-admin`这个模块,配置好

>dubbo.properties
    
后可以直接运行

新版本的[dubbo-admin](https://github.com/apache/incubator-dubbo-admin)叫**Dubbo OPS**, 对dubbo2.6以上的版本支持最好, 这个项目还在孵化中...
同样还在孵化中的还有[incubator-dubbo-samples](https://github.com/apache/incubator-dubbo-samples)等

这里我们下载老版本的dubbo, 版本号2.5.10, 将里面的`dubbo-admin`模块单独打(war)包,放在tomcat容器中(webapps目录下),运行tomcat容器,部署项目,便可以看到当前的提供者和消费者情况了:

服务信息:
![AAxnTx.png](https://s2.ax1x.com/2019/03/14/AAxnTx.png)

信息总览:
![AAxGXd.png](https://s2.ax1x.com/2019/03/14/AAxGXd.png)

提供者信息:
![AAxB9S.png](https://s2.ax1x.com/2019/03/14/AAxB9S.png)

消费者信息:
![AAx49U.png](https://s2.ax1x.com/2019/03/14/AAx49U.png)

#### 当使用java -jar 运行provider服务注册失败的问题

当使用java -jar 运行provider服务时, dubbo service能正常启动, 但是当consumer尝试调用服务时,出现

```
Exception in thread "main" com.alibaba.dubbo.rpc.RpcException: Forbid consumer 192.168.35.1 access service wy.dubbo.api.DemoService from registry 127.0.0.1:2181 use dubbo version 2.5.3, Please check registry access list (whitelist/blacklist).
	at com.alibaba.dubbo.registry.integration.RegistryDirectory.doList(RegistryDirectory.java:579)
	at com.alibaba.dubbo.rpc.cluster.directory.AbstractDirectory.list(AbstractDirectory.java:73)
	at com.alibaba.dubbo.rpc.cluster.support.AbstractClusterInvoker.list(AbstractClusterInvoker.java:260)
	at com.alibaba.dubbo.rpc.cluster.support.AbstractClusterInvoker.invoke(AbstractClusterInvoker.java:219)
	at com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker.invoke(MockClusterInvoker.java:72)
	at com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler.invoke(InvokerInvocationHandler.java:52)
	at com.alibaba.dubbo.common.bytecode.proxy0.sayHi(proxy0.java)
	at wy.dubbo.consumer.Consumer.main(Consumer.java:24)
```
错误原因其实是provider服务[没有正常在zookeeper注册](https://blog.csdn.net/zzm628/article/details/53508974);

要想成功通过java -jar 运行 provider
