

## eureka-example  
### example-001 
    @EnableEurekaServer 开启自动化配合 EurekaServer 
    eureka 单机版，会存在单点问题
    配置说明
    server.port，注册中心服务启动的端口
    eureka.client.register-with-eureka：是否将自己注册到该server ，应该设置false 不需要自己注册自己
    eureka.client.fetch-registry： 是否从eureka-server 获得注册信息，默认为true
    eureka.client.service-url.defaultZone：默认的eureka-server 地址，多个地址用逗号相隔 
    
### example-002  
    eureka 集群模式 ,三个实例 application-one.yml , application-two.yml，application-three.yml
    启动：在spring-cloud-example 下 
    mvn clean package -Dmaven.test.skip=true
    在eureka-example 的 example-002/target 执行下面三个，分别启动各个的注册服务
    java -jar example-002-1.0.0-SNAPSHOT.jar --spring.profiles.active=one
    java -jar example-002-1.0.0-SNAPSHOT.jar --spring.profiles.active=two
    java -jar example-002-1.0.0-SNAPSHOT.jar --spring.profiles.active=three
### example-003 
    服务注册
    只要引入了spring-cloud-starter-netflix-eureka-client，启动就会自动进行client相关配置了
    如果想禁用服务注册与发现 设置 eureka.client.enabled=false
    采用example-001 注册中心注册服务 
### example-004 
    服务注册
    只要引入了spring-cloud-starter-netflix-eureka-client，启动就会自动进行client相关配置了
    如果想禁用服务注册与发现 设置 eureka.client.enabled=false
    采用example-001 注册中心注册服务 
### example-005  
    服务发现
    LoadBalancerClient 进行负载，spring boot 会自动配置
    RestTemplate  进行调用
    先启动 example-003  example-004 
### example-006 
    服务发现
    RestTemplate 和 @LoadBalanced【默认轮询，url会Ribbon 的拦截器拦截，替换成ip】 进行调用
    先启动 example-003  example-004 

 
## openfeign-example 
    spring-cloud-starter-openfeign,通过集成Ribbon或Eureka实现负载均衡的HTTP客户端
    在Spring Cloud OpenFeign中，除了OpenFeign自身提供的标注（annotation）之外，
    还可以使用JAX-RS标注，或者Spring MVC标注。
    两个重要的注解：EnableFeignClients，FeignClient
    开启  @EnableFeignClients   ,EnableFeignClients标注用于修饰Spring Boot应用的入口类，
    以通知Spring Boot启动应用时，扫描应用中声明的Feign客户端可访问的Web服务。
    @FeignClient 标注用于声明Feign客户端可访问的Web服务
    FeignClient 参数说明
            name, value (默认"")，两者等价，服务名称
            qualifier (默认"")
            url (默认"")，可以指定某一个实例
            decode404 (默认false)
            configuration (默认FeignClientsConfiguration.class)，可以配置 HttpMessageConverters，Logger等
            fallback (默认void.class) ，fallback设置
            fallbackFactory (默认void.class)
            path (默认"")，接口前缀
            primary (默认true)
    
###  openfeign-example-001
     简单事例
     开启  @EnableFeignClients 
     定义UserService： @FeignClient(value = "eureka-producer")  
     启动 eureka-example/example-001 注册中心注册服务 
     启动 eureka-example/example-003
     启动 openfeign-example-001
     访问：http://localhost:8001/user/getName        
###  openfeign-example-002  
     path (默认"")，接口前缀demo
     @FeignClient(
             value = "eureka-producer",
             path = "user"
     )
    UserService 内的方法Mapping不用加user 签名，直接写getName
    @GetMapping("getName")
    String getName();
    
     启动 eureka-example/example-001 注册中心注册服务 
     启动 eureka-example/example-003
     启动 openfeign-example-002
     访问：http://localhost:8002/getName     