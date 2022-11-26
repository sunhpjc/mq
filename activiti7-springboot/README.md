### 1.报错解决
```java
Parameter 0 of method userGroupManager in org.activiti.core.common.spring.identity.config.ActivitiSpringIdentityAutoConfiguration required a bean of type 'org.springframework.security.core.userdetails.UserDetailsService' that could not be found.
```
缺少依赖，加入以下依赖解决，版本可以和springboot保持一致

或者排除启动类以下SecurityAutoConfiguration

```java
@Slf4j
@EnableSwagger2
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableAutoConfiguration 这个需要注释掉不然上边的排除操作无效
public class JeecgApplication {

  public static void main(String[] args) throws UnknownHostException {
```
```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.3.4.RELEASE</version>
</dependency>
```

### 2.
```pom
自动部署验证设置:true-开启（默认）、false-关闭
check-process-definitions: false
```
```
spring: activiti: deployment-mode: never-fail # 关闭 SpringAutoDeployment

以上配置可以关闭自动部署名为SpringAutoDeployment的部署记录

另外还有几个值可以配置：

default: 
    源码是注释：Default implementation of {@link AutoDeploymentStrategy} that groups all {@link Resource}s into a single deployment. 
    This implementation is equivalent to the previously used implementation.
    尝试翻译：意思是默认的AutoDeploymentStrategy实现，会把所有的资源 分组到一个单独的部署，这个实现和以前使用的实现是一样的
    
fail-on-no-process：FailOnNoProcessAutoDeploymentStrategy

never-fail：NeverFailAutoDeploymentStrategy

single-resource：
    源码是注释：Implementation of {@link AutoDeploymentStrategy} that performs a separate deployment for each resource by name.
    尝试翻译: 就是会为每一个资源根据名字单独进行部署
    
resource-parent-folder：
    源码是注释：Implementation of {@link AutoDeploymentStrategy} that performs a separate deployment for each set of {@link Resource}s that share the same parent folder. The namehint is used to prefix the names of
    deployments. If the parent folder for a {@link Resource} cannot be determined, the resource's name is used.
    尝试翻译：就是会为同一个父目录下的所有资源进行单独的部署，目录的名字会被用作部署名称的前缀，如果不能确定资源父目录，就用资源的名称替代
原文链接：https://blog.csdn.net/weixin_42286658/article/details/122967421
```