package wy.dubbo.provider;

import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.container.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 运行此main方法以注册provider
 *
 * @author wangy
 * @version 1.0
 * @date 19-3-13 / 上午12:11
 */
@SuppressWarnings("all")
@Log4j2
public class Provider {
    public static void main(String[] args) throws IOException {
        // 如果使用multicast作为注册中心, 需要如下设置
        // 或使用 java 命令行参数 -Djava.net.preferIPv4Stack=true
        System.setProperty("java.net.perferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath*:/spring/provider.xml");
        log.info("{}: starting Provider", context.getDisplayName());
        context.start();
        log.info("Provider started.");
//        System.in.read(); // press any key to exit
        Main.main(args);
    }
}
