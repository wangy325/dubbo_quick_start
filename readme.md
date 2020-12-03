这是一个spring-dubbo的快速开始

官方文档：http://dubbo.apache.org/en-us/docs/user/preface/background.html

# 使用方式

## 切换注册中心

在[provider.properties](./provider/src/main/resources/spring/provider.properties)配置文件中切换

### multicast注册中心

默认使用`multicast://224.5.6.7:1234`作为地址，需要在项目启动时配置

> -Djava.net.preferIPv4Stack=true

或者使用

```java
public class Provider {
    public static void main(String[] args) throws IOException {
            // 如果使用multicast作为注册中心, 需要如下设置
            // 或使用 java 命令行参数 -Djava.net.preferIPv4Stack=true
            System.setProperty("java.net.perferIPv4Stack","true");
           // ...
    }
}
```

2种方式中的一种来避免[issue 2423](https://github.com/apache/dubbo/issues/2423)

不需要其他额外设置

### zookeeper作为注册中心

启动zookeeper服务（standalone）(本地或docker服务)

1. 本地启动

```shell script
bin/zkServer.sh start
```

2. docker 启动

```shell script
docker run -d \
--name zookeeper \
-p 2181:2181 \
-v $HOME/docker/zoo/zoo.cfg:/apache-zookeeper-3.5.8-bin/conf/zoo.cfg \
zookeeper:3.5.8
```

需要注意的是，dubbo<sup>2.6以上的版本？</sup>需要使用[apache-curator](https://github.com/apache/curator)这个包的
依赖来建立zookeeper连接，否则可能会出现`NoClassDefFoundError`，根据异常信息，查看dubbo依赖是否缺少，缺少哪部分依赖。该项目
在启动时缺少`org.apache.curator.framework.recipes.cache.TreeCacheListener`类信息，对应的依赖为

```xml
<dependency>
<groupId>org.apache.curator</groupId>
<artifactId>curator-recipes</artifactId>
<version>${curator.version}</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
由于该依赖包含了`curator-framework`的引用，故只需要引用此依赖即可。

## 在IDE中测试

在IDE中运行测试很简单，只需要启动[Provider](./provider/src/main/java/wy.dubbo.provider/Provider.java)，控制台有
对应输出

```
22:40:16.014 [main] INFO  wy.dubbo.provider.Provider - 
    org.springframework.context.support.ClassPathXmlApplicationContext@1b68b9a4: 
    starting Provider
22:40:16.015 [main] INFO  wy.dubbo.provider.Provider - Provider started.
[2020-12-01 22:40:16] Dubbo service server started!
```

之后，运行consumer项目中的[TestConsumer](./consumer/src/test/java/wy/dubbo/consumer/TestConsumer.java)，有如下
输出即可：

```
22:40:21.156 [main] INFO  wy.dubbo.consumer.Consumer - Hi, wangy325
```

## 运行jar包测试

### mvn install

首先需要将[quick-start-dubbo](pom.xml)和[quick-start-api](./api/pom.xml)安装到本地maven库中，前者是
[quick-start-provider](./provider/pom.xml)其父模块，后者是其依赖jar。

不出意外，即可使用`mvn package`命令运行*provider*模块并成功打包，之后即可执行此jar包

```shell
cd provider
java -jar target/quick-start-provider-0.1-SNAPSHOT.jar
```
控制台输出应和IDE运行时一致。

随即即可执行consumer项目中的[TestConsumer](./consumer/src/test/java/wy/dubbo/consumer/TestConsumer.java)

> 此用例中没有将consumer项目打包，这是一种简便方法，因为如果打包consumer项目的话，需要一个新的项目去调用之，或者将其更改为mvc项目
以进行RESTful测试

# 服务监控

可以使用[dubbo-admin](https://github.com/apache/incubator-dubbo)项目对服务进行监控。

再次测试时，我使用了docker仓库中的第三方镜像[chenchuxin/dubbo-admin](https://hub.docker.com/r/chenchuxin/dubbo-admin)进行
服务监控。由于新版本的官方项目还在孵化中，使用老版本的监控同样可行

拉取镜像并执行

```shell script
docker run -d \
--name duboo-admin-2.2 \
-p 8080:8080 \
-e dubbo.registry.address=zookeeper://192.168.1.101:2181 \
-e dubbo.admin.root.password=root \
-e dubbo.admin.guest.password=guest \
chenchuxin/dubbo-admin 
```

将上面配置的注册中心地址改成对应的地址即可。进入localhost:8080即可查看注册的提供者以及消费者信息。

> 此为之前截图，并不一定和实际显示的服务提供者/消费者信息一致，但是操作是一致的。

服务信息:
![AAxnTx.png](https://s2.ax1x.com/2019/03/14/AAxnTx.png)

信息总览:
![AAxGXd.png](https://s2.ax1x.com/2019/03/14/AAxGXd.png)

提供者信息:
![AAxB9S.png](https://s2.ax1x.com/2019/03/14/AAxB9S.png)

消费者信息:
![AAx49U.png](https://s2.ax1x.com/2019/03/14/AAx49U.png)