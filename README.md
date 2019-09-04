

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
### example-007
    spring-cloud-starter-openfeign
     @EnableFeignClients注解用于spring boot 的启动类上，表示启动该项目的openfeign支持
     @EnableFeignClients 的 basePackages 还可以设置扫描的package
     @EnableFeignClients 的 clients， List of classes annotated with @FeignClient. If not empty, disables classpath scanning.
     但是如果clients他不空，指定的basePackages会失效。
     @EnableFeignClients 的defaultConfiguration：spring cloud 将所有的@feignClient集中在一起重新创建一个ApplicationContext，并使用FeignClientsConfiguration
     进行配置，FeignClientsConfiguration中包括一个编码器，一个解码器，还有hystrix的一些配置。配置此项可以使用自定义的feignclient配置来
     代替默认配置。并且优先级高于默认配置。但一把情况下使用默认即可
    使用 @FeignClient 注解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用 Spring MVC 的注解就可以来绑定服务提供方的 REST 接口
    如：@FeignClient(value = "eureka-producer",fallback = UserCallBack.class,url="http://localhost:8051")
        通过url 可以指定具体某个实例服务，不写就会进行轮询
    启动 example-001 注册中心注册服务 
    启动 example-003  example-004 
    启动 example-007
    访问：http://localhost:9003/user/getName               