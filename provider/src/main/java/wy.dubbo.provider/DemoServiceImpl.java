package wy.dubbo.provider;

import org.springframework.stereotype.Service;
import wy.dubbo.api.DemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>这是正儿八经的dubbo provider module</p>
 * <p>通常, dubbo服务的功能更新是通过修改项目下的逻辑实现的</p>
 * @author wangy
 * @version 1.0
 * @date 19-3-12 / 下午11:55
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public List<String> getPermission(Long id) {
        List<String> demo = new ArrayList<>();
        demo.add(String.format("Permission_%d",id-1));
        demo.add(String.format("Permission_%d",id));
        demo.add(String.format("Permission_%d",id+1));
        return demo;
    }

    @Override
    public String sayHi(String name) {
       return "Hi, " + name;
    }
}
