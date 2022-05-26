[![Maven central](https://maven-badges.herokuapp.com/maven-central/io.github.flashvayne/rbac-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.flashvayne/rbac-spring-boot-starter)
# rbac-spring-boot-starter

基于RBAC (Resource-Based Access Control) 的用户资源权限管理轻量级组件  
详情参照博客 [blog](https://vayne.cc/2022/02/06/rbac-spring-boot-starter)
# 使用说明：

## 1.登录/Token生成：
1.注入AuthUserService，调用authUserService.authentication(userId,password)方法可校验用户名密码，
如检验通过，可通过rbacTokenService.generateTokenInfo(authUserInfo)方法生成Token信息,调用rbacTokenService.doGenerateToken(rbacTokenInfo)将token存入redis（或其他容器）。实例代码如下：
```java
@Autowired
private AuthUserService authUserService;

@Autowired
private RbacTokenService RbacTokenService;

@PostMapping("/login")
    public ResponseModel login(@RequestBody @Valid LoginDTO loginDTO) {
        if (!rbacAuthUserService.authentication(loginDTO.getUserId(), loginDTO.getPassword())) {
            return ResponseModel.fail(401, "用户名或密码错误", null);
        }
        AuthUserDTO authUserInfo = rbacAuthUserService.getAuthUserInfo(loginDTO.getUserId());

        if (authUserInfo.getStatus() == 1) {
            return ResponseModel.fail("登录失败，账号已被锁定");
        }
        //如果用户/角色/资源/Token等信息有额外信息 可扩展到相应addition属性
//        demoService.setAdditionProperties(authUserInfo);
        RbacTokenInfo rbacTokenInfo = rbacTokenService.generateTokenInfo(authUserInfo);
        if (rbacTokenService.doGenerateToken(rbacTokenInfo)) {
            return ResponseModel.success("登录成功", rbacTokenInfo);
        } else {
            return ResponseModel.fail(500, "请求失败", null);
        }
    }
```

## 2.鉴权：
本组件基于Rbac用户-角色-资源三者进行权限管理。
鉴权切入点是@RbacAuthorization注解，在需要鉴权的方法上使用此注解。
此注解会检查token是否合法并将根据rbac_role_resource、rbac_user_role中配置的资源关系检查当前用户是否具有使用资源的权限（以请求的uri最为资源标识）。
方法中可直接调用AuthUserUtils.get()获取当前用户信息。
```java
@RbacAuthorization
@GetMapping("/list")
public void list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
        String param1,String param2){
    log.info("当前用户：{}",AuthUserUtils.get());
    Page.start(pageNum,pageSize);
    log.info(testService.list(param1,param2));
}
```
如当前用户token不合法则返回Http 401 Unauthorized.
如token合法但不具有"/list"接口资源权限，则返回Http 403 Forbidden.


# 配置方法
1.加载maven依赖
```pom
<dependency>
    <groupId>io.github.flashvayne</groupId>
    <artifactId>rbac-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```
2.配置项：
```yml
rbac:
  token-expire-time: 7200  #无操作2小时过期
  token-name: authorization  #request header中token的变量名
  redis-key-prefix: 'rbac:'
spring:
  redis:  #配置Redis信息（本组件默认使用Redis作为Token存储的中间件）
    host: 127.0.0.1
    port: 6379
    timeout: 5000
```
3.执行本项目的schema中对应的SQL脚本建Rbac基础表  
4.将BaseRbacMapper加入Mybatis扫描：  
可通过继承基础Mapper并加入Mybatis扫描。
如果是MySQL数据源可直接将resources/mapper/RbacMapper-MySQL.xml复制到你的mapper-locations路径。
其他数据源可参照RbacMapper-MySQL.xml进行实现
```java
@Mapper
public interface TestRbacMapper extends BaseRbacMapper {
}
```

# Demo项目：
[Rbac-Demo](https://github.com/flashvayne/rbac-demo)

# 扩展性：
本组件使用@ConditionalOnMissingBean条件注解进行自动装配，所以开发者可直接重写本组件中的Bean并注入容器实现功能的重构。
+ 可通过继承RbacAuthorizationAspect修改拦截器的实现。
+ 可通过继承DefaultAuthUserServiceImpl或实现AuthUserService接口，来重写用户信息相关功能。
+ 可通过继承DefaultTokenServiceImpl或实现TokenService接口，来重写Token服务相关功能。
+ 用户/角色/资源/Token等DTO预留了addition属性，可扩展业务系统需要添加的信息（比如用户所属区县、角色所属机构等）

# Tips：
+ 本项目使用Redis作为Token保存的中间件，此举是为了直接分布式多台机器共享Token，如开发者不需要多级部署，可重写Token服务相关功能，重写方法见上方章节“扩展性”

# 作者信息
flashvayne

邮箱: flashvayne@gmail.com

博客: https://vayne.cc
