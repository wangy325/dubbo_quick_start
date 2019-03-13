package wy.dubbo.provider;

import org.springframework.stereotype.Service;
import wy.dubbo.api.DemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangy
 * @version 1.0
 * @date 19-3-12 / 下午11:55
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public List<String> getPerssions(Long id) {
        List<String> demo = new ArrayList<>();
        demo.add(String.format("Permission_%d",id-1));
        demo.add(String.format("Permission_%d",id));
        demo.add(String.format("Permission_%d",id+1));
        return demo;
    }
}
