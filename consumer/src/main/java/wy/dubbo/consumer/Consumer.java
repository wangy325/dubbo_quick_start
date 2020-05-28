package wy.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
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
@Slf4j
@Repository
public class Consumer {

    @Autowired
    private DemoService demoService;


    public void helloDubbo(String name){
        String s = demoService.sayHi(name);
        log.info(s);
    }

}
