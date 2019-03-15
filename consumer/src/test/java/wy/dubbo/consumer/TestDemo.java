package wy.dubbo.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wy.dubbo.api.DemoService;

import java.util.List;

/**
 * @author wangy
 * @version 1.0
 * @date 2019/3/15 / 10:50
 */
@ContextConfiguration(locations = {"/spring/application-context.xml", "/spring/consumer.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDemo {

    @Autowired
    private DemoService demoService;

    @Test
    public void testDemo(){
        List<String> permission = demoService.getPermission(10L);
        System.out.println(permission);
    }
}
