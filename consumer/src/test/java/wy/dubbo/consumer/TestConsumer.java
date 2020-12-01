package wy.dubbo.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wy.dubbo.api.DemoService;

import java.io.IOException;
import java.util.List;

/**
 * @author wangy
 * @version 1.0
 * @date 2019/3/15 / 10:50
 */
@ContextConfiguration(locations = {"/spring/consumer.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestConsumer {

    @Autowired
    private Consumer consumer;

    @Test
    public void testDemo() throws IOException {
        consumer.helloDubbo("wangy325");
//        System.in.read();
    }
}
