package wy.dubbo.api;

import java.util.List;

/**
 * <p>这是一个单独的jar包, 是定义dubbo provider各种服务的接口
 * <p>如果是线上环境,那么这个jar被mvn depoly,install到私服之后, jenkins构建其他依赖此jar
 * 包的项目时,应该自动拉取最新版本的jar包依赖
 * </p>
 * <p>一旦provider注册成功,这个类里面的所有方法都会被zookeeper管理</p>
 * @author wangy
 * @version 1.0
 * @date 19-3-12 / 下午11:46
 */
@SuppressWarnings("all")
public interface DemoService {

    List<String> getPermission(Long id);

    String sayHi(String name);

}
