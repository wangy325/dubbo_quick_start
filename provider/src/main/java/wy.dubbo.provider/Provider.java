package wy.dubbo.provider;

import org.slf4j.Logger;
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
public class Provider {
    public static void main(String[] args) throws IOException {
        // 如果使用multicast作为注册中心, 需要如下设置
//        System.setProperty("java.net.perferIPv4Stack","true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring/provider.xml");
        System.out.println(context.getDisplayName() + ": here");
        context.start();
        System.out.println("Provider started.");
        System.in.read(); // press any key to exit
    }
}
