package wy.dubbo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import wy.dubbo.api.DemoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 运行此方法以调用provider提供的远程服务
 *
 * @author wangy
 * @version 1.0
 * @date 19-3-13 / 上午12:41
 */
public class Consumer {
    @Autowired
    private static DemoService demoService;


    public static void main(String[] args) throws IOException {
        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring/consumer.xml");
        context.start();
        // Obtaining a remote service proxy
        DemoService demoService = (DemoService) context.getBean("demoService");
        // Executing remote methods
//        List<String> hello = demoService.getPermission(10L);
        String hello = demoService.sayHi("Lucy");
        System.out.println(hello);*/

        Consumer.testWithSpring();

    }

    static void testWithSpring(){
        List<String> permission = demoService.getPermission(10L);
        System.out.println(permission);
    }
}
