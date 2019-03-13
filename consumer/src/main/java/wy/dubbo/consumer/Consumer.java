package wy.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import wy.dubbo.api.DemoService;

import java.util.List;

/**
 * 运行此方法以调用provider提供的远程服务
 *
 * @author wangy
 * @version 1.0
 * @date 19-3-13 / 上午12:41
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/consumer.xml");
        context.start();
        // Obtaining a remote service proxy
        DemoService demoService = (DemoService) context.getBean("demoService");
        // Executing remote methods
        List<String> hello = demoService.getPerssions(10L);
        // Display the call result
        System.out.println(hello);
    }
}
