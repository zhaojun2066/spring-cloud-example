

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
    采用example-001 注册中心注册服务，所以需要启动example-002 集群三个实例
### example-004  
    服务发现
    LoadBalancerClient 进行负载，spring boot 会自动配置
    RestTemplate  进行调用
    先启动 example-003 
        