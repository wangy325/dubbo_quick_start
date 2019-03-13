package wy.dubbo.api;

import java.util.List;

/**
 * @author wangy
 * @version 1.0
 * @date 19-3-12 / 下午11:46
 */
@SuppressWarnings("all")
public interface DemoService {

    List<String> getPerssions(Long id);
}
