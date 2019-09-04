

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
    spring-cloud-starter-openfeign,通过集成Ribbon实现负载均衡的HTTP客户端
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
###  openfeign-example-003       
    fallback demo
    开启：feign:
              hystrix:
                 enabled: true  #打开callback
                 
     @FeignClient(value = "eureka-producer",fallback = UserCallBack.class)
     public interface UserService    
     UserCallBack 为callback 实现，这里服务端会sleep 一段时间，让客户端超时，然后走callback
     通过ribbon 设置 client 的超时
     
      启动 eureka-example/example-001 注册中心注册服务 
      启动 eureka-example/example-003
      启动 openfeign-example-003
      访问：
      http://localhost:8003/say/jufeng         
###  openfeign-example-004
    替换JDK默认的URLConnection为okhttp
    修改openfeign 客户端的实现 ，使用OkHttp
    加入：
    <dependency>
       <groupId>io.github.openfeign</groupId>
       <artifactId>feign-okhttp</artifactId>
    </dependency>
    开启：
    feign:
      okhttp:
        enabled: true  
    必要时可以自己可以定时OkHttpClient,不定制也可以使用默认的 ,本例采用定制化
      启动 eureka-example/example-001 注册中心注册服务 
      启动 eureka-example/example-003
      启动 openfeign-example-004
      访问：
      http://localhost:8004/user/getName           
###  openfeign-example-005
    开启压缩
    feign:
      compression:
        request:
          enabled: true
          mime-types: text/xml,application/xml,application/json
          min-request-size: 2048
        response:
          enabled: true    
###  openfeign-example-006
    超时设置
    openfeign 是基于ribbon 设置超时
    全局：
    ribbon:
      connectTimeout: 2000
      readTimeout: 3000 
    局部针对某个服务：
    eureka-producer:
      ribbon:
        ConnectTimeout: 2000 #请求连接超时时间
        ReadTimeout: 3000 #请求处理的超时时间
      
      启动 eureka-example/example-001 注册中心注册服务 
      启动 eureka-example/example-003
      启动 openfeign-example-006
      访问：
      http://localhost:8004/say/33,看后台报time out                  

###  openfeign-example-007 
    配置文件修改日志级别
    loggerLevel: BASIC ，FULL，HEADERS，NONE  
    NONE:默认值 ，什么都不打印
    BASIC：仅记录请求方法、URL、响应状态码以及执行时间   
    HEADERS：在BASIC基础上，记录请求和响应的header
    FULL：记录请求和响应的header、body 、元数据
    启动 eureka-example/example-001 注册中心注册服务 
    启动 eureka-example/example-003
    启动 openfeign-example-007
    访问：
    http://localhost:8007/user/getName,看后台日志     