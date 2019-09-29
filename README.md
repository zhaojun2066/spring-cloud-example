## bootstrap-context-example
       bootstrap context
       官方解释：
       一个Spring Cloud应用程序通过创建一个“引导”上下文来进行操作，这个上下文是主应用程序的父上下文。开箱即用，负责从外部源加载配置属性，还解密本地外部配置文件中的属性。
       这两个上下文共享一个Environment，这是任何Spring应用程序的外部属性的来源。Bootstrap属性的优先级高，因此默认情况下不能被本地配置覆盖。
       引导上下文使用与主应用程序上下文不同的外部配置约定，因此使用bootstrap.yml application.yml（或.properties）代替引导和主上下文的外部配置。例：bootstrap.yml
       spring:
         application:
           name: foo
         cloud:
           config:
             uri:sss
       如果您的应用程序需要服务器上的特定于应用程序的配置，那么设置spring.application.name（在bootstrap.yml或application.yml）中是个好主意。
       您可以通过设置spring.cloud.bootstrap.enabled=false（例如在系统属性中）来完全禁用引导过程。
       
       总结：
       1>bootstrap context 创建时会读取bootstrap.properties|yml 在内容作为引导配置, 因此bootstrap优先于application加载。
       2>boostrap的属性文件在以下情景下会使用： 
     　　　　 配置中心：config-server里请用bootstrap属性文件 
     　　　   解密属性文件时，最好使用bootstrap属性文件 
     　　　　 需要自定义引导程序时使用bootstrap属性文件，主要一定不要被我们主程序扫描到
       3>application会覆盖bootstrap中的非引导配置，因此不建议两种类型配置文件同时存在。简单粗暴的做法是在springcloud应用中
         用bootstrap属性文件代替application，毕竟Envrionment是共享的。   
       
### bootstrap-context-example-001
      自定义  bootstrap context ,bootstrap context 加载的配置不会被application context覆盖，看输出内容是什么
      注意：MyBootstrapAutoConfiguration是我们自定义的引导类，该类一定不能被@SpringBootApplication注解ComponentScan到,
            否则引导必然就会被主程序所覆盖。因此我用包把他们区分开来
               
## eureka-example  
    
    
### example-001 
    @EnableEurekaServer 开启自动化配合 EurekaServer 
    eureka 单机版，会存在单点问题
    配置说明
    server.port，注册中心服务启动的端口
    eureka.client.register-with-eureka：是否将自己注册到该server ，应该设置false 不需要自己注册自己
    eureka.client.fetch-registry： 是否从eureka-server 获得注册信息，默认为true
    eureka.client.service-url.defaultZone：默认的eureka-server 地址，多个地址用逗号相隔 
    enable-self-preservation ：自我保护机制配置
    eviction-interval-timer-in-ms： 多少时间内没有心跳，开始剔除该client
     
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
## restTemplate-example
    restTemplate 是一个http client 
    ribbon是一个很好的负载均衡客户端，可以很好的控制http和tcp的行为
    restTemplate+ ribbon 可以很好进行负载均衡的操作
    
    代码配置ribbon
    https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html#_hystrix_timeouts_and_ribbon_clients
    配置文件ribbon
    https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html#_customizing_the_ribbon_client_by_setting_properties
    
    How to Use Ribbon Without Eureka：
    if you prefer not to use Eureka, Ribbon and Feign also work. Suppose you have declared a @RibbonClient for "stores", and 
    Eureka is not in use (and not even on the classpath). The Ribbon client defaults to a configured server list. You can supply 
    the configuration as follows:
    application.yml.
    stores:
      ribbon:
        listOfServers: example.com,google.com
        
    Disable Eureka Use in Ribbon:
    ribbon:
      eureka:
       enabled: false
       
    Ribbon支持配置eager load实现在启动时就初始化Ribbon相关类    
    ribbon:
      eager-load:
        enabled: true
        clients: client1, client2, client3   
    
### restTemplate-example-001
    LoadBalancerClient 进行负载，spring boot 会自动配置
    RestTemplate  进行调用
    先启动 eureka-example/example-001  eureka-example/example-003  eureka-example/example-004   
    
### restTemplate-example-002
    RestTemplate 和 @LoadBalanced + Ribbon 进行调用
    先启动 eureka-example/example-001  eureka-example/example-003  eureka-example/example-004       
     
[spring-cloud-starter-openfeign spring-cloud官网介绍](https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html#spring-cloud-feign "介绍").  
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
    修改openfeign 客户端的实现 ，使用OkHttp，同时设置连接数的大小
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
###  openfeign-example-008
    代码修改日志级别
    loggerLevel: BASIC ，FULL，HEADERS，NONE  
    NONE:默认值 ，什么都不打印
    BASIC：仅记录请求方法、URL、响应状态码以及执行时间   
    HEADERS：在BASIC基础上，记录请求和响应的header
    FULL：记录请求和响应的header、body 、元数据
    关于优先级：细粒度配置文件配置 > 细粒度代码配置 > 全局配置文件配置 > 全局代码配置
    demo:
    局部设置：UserServerFeignConfig
    @FeignClient(value = "eureka-producer",configuration = UserServerFeignConfig.class)
    public interface UserService 
    
    全局设置：
    @EnableFeignClients(defaultConfiguration = GlobalFeignLoggerConfig.class)
    
    启动 eureka-example/example-001 注册中心注册服务 
    启动 eureka-example/example-003
    启动 openfeign-example-008
    访问：
    http://localhost:8008/user/getName,看后台日志         
    
    
###  openfeign-example-009    
     代码设置超时时间
     启动 eureka-example/example-001 注册中心注册服务 
     启动 eureka-example/example-003
     启动 openfeign-example-009
     访问：
     http://localhost:8009/say/33,看后台报time out     
###  openfeign-example-010
    负载均衡策略设置，基于ribbon
     详见配置文件     
     启动 eureka-example/example-001 注册中心注册服务 
     启动 eureka-example/example-003 eureka-example/example-004
     启动 openfeign-example-010
     访问：
     http://localhost:8010/user/getName,看server后台日志    
###  openfeign-example-011
     多个实例，方便测试，只请求一个url，url 可以设置为单独的直连ip地址
     详见配置文件     
     启动 eureka-example/example-001 注册中心注册服务 
     启动 eureka-example/example-003 eureka-example/example-004
     启动 openfeign-example-011
     访问：
     http://localhost:8011/user/getName,看eureka-example/example-003 日志       
     
## config-example
    配置中心

### config-example-001
     启动配置中心server
     访问：
     http://localhost:1000/config/dev
     如果打印基本信息则表示启动正常。
     http请求地址：/{application}/{profile}[/{label}]
     资源文件：
     /{application}-{profile}.yml
     /{label}/{application}-{profile}.yml
     /{application}-{profile}.properties
     /{label}/{application}-{profile}.properties
     示例：本例中资源文件是config-dev.yml，其中config就是application，dev就是profile，
     匹配资源文件的第三种，访问地址就是/config(application)/dev(profile)
     label 是 git 的分支
### config-example-002
    配置中心客户端
    详见: bootstrap.yml
    先启动 config-example-001
    访问 ：http://localhost:1002/say           
### config-example-003
    占位符 {application} 启动配置服务server,这里以仓库 spring-cloud-example 为例作为application的名字
    访问：http://localhost:1003/spring-cloud-example/dev
### config-example-004
     配置中心客户端
     详见: bootstrap.yml
     先启动 config-example-003
     访问 ：http://localhost:1004/say   
### config-example-005
     配置中心客户端 手动刷新
     详见: bootstrap.yml
     先启动 config-example-001,然后再git 仓库修改config-dev 里的name 的value
     刷新：http://localhost:1005/actuator/refresh [post 请求]
     访问 ：http://localhost:1005/say ，发现值变了        
 
### config-auto-refresh/config-server
     配置中心开启广播  bus+ kafka
     详见: bootstrap.yml
     更新和推送配置编更POST 请求：http://localhost:9090/actuator/bus-refresh  
### config-auto-refresh/config-clients
    配置中心客户端 
    详见: bootstrap.yml
    先启动 config-auto-refresh/config-server,然后再git 仓库修改config-dev 里的name 的value
    刷新：POST 请求：http://localhost:9090/actuator/bus-refresh
    访问 ：http://localhost:9091/say ，发现值变了   
### ha-config
    高可用 注册到注册中心，client 从注册中心拉取注册server。
    ha-config/ha-server  ，配置中心server
    ha-config/ha-client ,  配置client
    
         
## hystrix-example
    demo：
        @HystrixCommand(
             ignoreExceptions :   忽略的异常
                  commandProperties = {
                  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                  @HystrixProperty(name = "execution.timeout.enabled", value = "false")},
                  threadPoolProperties = {
                                    @HystrixProperty(name = "coreSize", value = "2"),
                                    @HystrixProperty(name = "maxQueueSize", value = "2")},
                  fallbackMethod = "findByIdFallback")
          public User findById(@PathVariable Long id) {
            return this.restTemplate.getForObject("http://microservice-provider-user/simple/" + id, User.class);
          }
    
    
       hystrix.command.default和hystrix.threadpool.default中的default为默认CommandKey，default 是所有实例的默认值
    
       Command Properties
       Execution相关的属性的配置：
       hystrix.command.default.execution.isolation.strategy 隔离策略，默认是可选THREAD, 可选THREAD, SEMAPHORE
    
       hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds 命令执行超时时间，默认1000ms
    
       hystrix.command.default.execution.timeout.enabled 执行是否启用超时，默认启用true
       hystrix.command.default.execution.isolation.thread.interruptOnTimeout 发生超时是是否中断，默认true
       hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests 最大并发请求数，默认10，-
               该参数当使用ExecutionIsolationStrategy.SEMAPHORE策略时才有效。如果达到最大并发请求数，请求会被拒绝。
               理论上选择semaphore size的原则和选择thread size一致，但选用semaphore时每次执行的单元要比较小且执行速度快（ms级别），
               否则的话应该用thread。
       semaphore应该占整个容器（tomcat）的线程池的一小部分。
       Fallback相关的属性
       这些参数可以应用于Hystrix的THREAD和SEMAPHORE策略
    
       hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests 如果并发数达到该设置值，请求会被拒绝和抛出异常并且fallback不会被调用。默认10
       hystrix.command.default.fallback.enabled 当执行失败或者请求被拒绝，是否会尝试调用hystrixCommand.getFallback() 。默认true
       
       Circuit Breaker相关的属性
       hystrix.command.default.circuitBreaker.enabled 用来跟踪circuit的健康性，如果未达标则让request短路。默认true
       hystrix.command.default.circuitBreaker.requestVolumeThreshold 一个rolling window内最小的请求数。如果设为20，那么当一个rolling window的时间内（比如说1个rolling window是10秒）收到19个请求，即使19个请求都失败，也不会触发circuit break。默认20
       hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds 触发短路的时间值，当该值设为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit。默认5000
       hystrix.command.default.circuitBreaker.errorThresholdPercentage错误比率阀值，如果错误率>=该值，circuit会被打开，并短路所有请求触发fallback。默认50
       hystrix.command.default.circuitBreaker.forceOpen 强制打开熔断器，如果打开这个开关，那么拒绝所有request，默认false
       hystrix.command.default.circuitBreaker.forceClosed 强制关闭熔断器 如果这个开关打开，circuit将一直关闭且忽略circuitBreaker.errorThresholdPercentage
       
       Metrics相关参数
       hystrix.command.default.metrics.rollingStats.timeInMilliseconds 设置统计的时间窗口值的，毫秒值，circuit break 的打开会根据1个rolling window的统计来计算。若rolling window被设为10000毫秒，则rolling window会被分成n个buckets，每个bucket包含success，failure，timeout，rejection的次数的统计信息。默认10000
       hystrix.command.default.metrics.rollingStats.numBuckets 设置一个rolling window被划分的数量，若numBuckets＝10，rolling window＝10000，那么一个bucket的时间即1秒。必须符合rolling window % numberBuckets == 0。默认10
       hystrix.command.default.metrics.rollingPercentile.enabled 执行时是否enable指标的计算和跟踪，默认true
       hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds 设置rolling percentile window的时间，默认60000
       hystrix.command.default.metrics.rollingPercentile.numBuckets 设置rolling percentile window的numberBuckets。逻辑同上。默认6
       hystrix.command.default.metrics.rollingPercentile.bucketSize 如果bucket size＝100，window＝10s，若这10s里有500次执行，只有最后100次执行会被统计到bucket里去。增加该值会增加内存开销以及排序的开销。默认100
       hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds 记录health 快照（用来统计成功和错误绿）的间隔，默认500ms
       
       Request Context 相关参数
       hystrix.command.default.requestCache.enabled 默认true，需要重载getCacheKey()，返回null时不缓存
       hystrix.command.default.requestLog.enabled 记录日志到HystrixRequestLog，默认true
    
       Collapser Properties 相关参数
       hystrix.collapser.default.maxRequestsInBatch 单次批处理的最大请求数，达到该数量触发批处理，默认Integer.MAX_VALUE
       hystrix.collapser.default.timerDelayInMilliseconds 触发批处理的延迟，也可以为创建批处理的时间＋该值，默认10
       hystrix.collapser.default.requestCache.enabled 是否对HystrixCollapser.execute() and HystrixCollapser.queue()的cache，默认true
    
       ThreadPool 相关参数
       线程数默认值10适用于大部分情况（有时可以设置得更小），如果需要设置得更大，那有个基本得公式可以follow：
       requests per second at peak when healthy × 99th percentile latency in seconds + some breathing room
       每秒最大支撑的请求数 (99%平均响应时间 + 缓存值)
       比如：每秒能处理1000个请求，99%的请求响应时间是60ms，那么公式是：
       （0.060+0.012）
    
       基本得原则时保持线程池尽可能小，他主要是为了释放压力，防止资源被阻塞。
       当一切都是正常的时候，线程池一般仅会有1到2个线程激活来提供服务
    
       hystrix.threadpool.default.coreSize 并发执行的最大线程数，默认10
       hystrix.threadpool.default.maxQueueSize BlockingQueue的最大队列数，当设为－1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。该设置只会在初始化时有效，之后不能修改threadpool的queue size，除非reinitialising thread executor。默认－1。
       hystrix.threadpool.default.queueSizeRejectionThreshold 即使maxQueueSize没有达到，达到queueSizeRejectionThreshold该值后，请求也会被拒绝。因为maxQueueSize不能被动态修改，这个参数将允许我们动态设置该值。if maxQueueSize == -1，该字段将不起作用
       hystrix.threadpool.default.keepAliveTimeMinutes 如果corePoolSize和maxPoolSize设成一样（默认实现）该设置无效。如果通过plugin（https://github.com/Netflix/Hystrix/wiki/Plugins）使用自定义实现，该设置才有用，默认1.
       hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds 线程池统计指标的时间，默认10000
       hystrix.threadpool.default.metrics.rollingStats.numBuckets 将rolling window划分为n个buckets，默认10
       
       信号量是不能执行异步的 
       
       
       timeout 设置注意：
       Hystrix Timeouts And Ribbon Clients
       意思就是hystrix timeout 要大于 ribbon client 的超时时间，才有效，包括要大于ribbon client 重试的总时间
       https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html#_hystrix_timeouts_and_ribbon_clients
      
###  hystrix-example-001 
    服务提供者，用于测试，首先启动注册中心   eureka-example/example-001    ，下面所有的测试都是基于该服务
###  hystrix-example-002 
     command 、thread 参数设置 ,测试超时 fallback ,还可以为fallback的方法在设置fallback          
###  hystrix-example-003
    cache 测试
    这里大家可能有一个误解，可能会把Hystrix的请求缓存当作和Redis缓存一样的概念，就是如果数据存到缓存了，
    那么任意用户再次请求时都会在缓存中取出数据，其实并不是这样，Hystrix缓存仅限于当前线程内如果重复调用
    相同的服务依赖会返回缓存的数据，通俗解释就是Hystrix缓存是基于request的，在当次请求内对同一个依赖服务
    的多次调用，除了第一次是真实调用，其余的会使用Hystrix缓存.
    
    启动后访问：http://localhost:7003/say/jufeng，看后台服务只输出一次调用
###  hystrix-example-004 
    熔断器测试 circuit breaker
    当 Hystrix Command 请求后端服务失败数量超过一定阈值，断路器会切换到开路状态 (Open)。
    这时所有请求会直接失败而不会发送到后端服务:
    这个阈值涉及到三个重要参数：快照时间窗、请求总数下限、错误百分比下限。这个参数的作用分别是：
    快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的 10 秒
    请求总数下限：在快照时间窗内，必须满足请求总数下限才有资格进行熔断。默认为 20，意味着在 10 秒内，如果该 Hystrix Command 
                  的调用此时不足 20 次，即时所有的请求都超时或其他原因失败，断路器都不会打开。
    错误百分比下限：当请求总数在快照时间窗内超过了下限，比如发生了 30 次调用，如果在这 30 次调用中，有 16 次发生了超时异常，
                   也就是超过 50% 的错误百分比，在默认设定 50% 下限情况下，这时候就会将断路器打开。
                   
    断路器保持在开路状态一段时间后 (默认 5 秒)，自动切换到半开路状态 (HALF-OPEN)。这时会判断下一次请求的返回情况，如果请求成功，
    断路器切回闭路状态 (CLOSED)，否则重新切换到开路状态 (OPEN)。 
    
    该例子设置是 5000 ms 时间窗口内，请求次数要达到6次以上，且错误率要达到20% 才会打开  circuit breaker，打开时间是5000 ms   
    
    访问：http://localhost:7004/user/getName
             
###  hystrix-example-005 
     openfegin 测试 ，详见配置文件    
###  hystrix-example-006
    合并请求，在在一个时间窗口内，合并所有的请求在一个线程池内完成，再返回。
    测试未能成功，总是调用单个方法，返回null feture，并没有关联到批量方法 
###  hystrix-example-007
    异常处理和忽略异常测试
    http://localhost:7007/getUsers   
    
###  hystrix-example-009
    默认监控：    
    http://localhost:7008/actuator/hystrix.stream    
    
    配置Dashboard
    详见ConfigBean
     copy http://localhost:7008/hystrix.stream 到 http://localhost:7008/hystrix 页面中   
 
## zuul-example
    网关
    https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html#_router_and_filter_zuul
    time out set:
    基于服务发现： 通过ribbon 设置超时时间
    如果是直接代理的url，则通过 zuul.host.connect-timeout-millis 和 zuul.host.socket-timeout-millis 进行设置
    
    Hystrix超时需要考虑Ribbon读取和连接超时以及该服务将发生的重试总数。默认情况下，Spring Cloud Zuul将尽力为您
    计算Hystrix超时，除非您显式指定Hystrix超时：
    (ribbon.ConnectTimeout + ribbon.ReadTimeout) * (ribbon.MaxAutoRetries + 1) * (ribbon.MaxAutoRetriesNextServer + 1)
    
    
    跨域：
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/path-1/**")
                        .allowedOrigins("https://allowed-origin.com")
                        .allowedMethods("GET", "POST");
            }
        };
    }
    
    filter ：
    类型	过滤器	  顺序	        功能
    pre	 ServletDetectionFilter	-3	标记处理Servlet的类型
    pre	Servlet30WrapperFilter	-2	包装HttpServletRequest请求
    pre	FormBodyWrapperFilter	-1	包装请求体
    pre	DebugFilter	1	标记调试标志
    pre	PreDecorationFilter	5	处理请求上下文供后续使用
    route	RibbonRoutingFilter	10	serviceId请求转发
    route	SimpleHostRoutingFilter	100	url请求转发
    route	SendForwardFilter	500	forward请求转发
    error	SendErrorFilter	0	处理有错误的请求响应
    post	LocationRewriteFilter	900	重定向时，负责将标头重写为Zuul的URL
    post	SendResponseFilter	1000	组织需要发送回客户端的响应内容
    
### zuul-euraka-server
     注册到 eureka 的server
### zuul-example-server
     普通的server     
### zuul-example-001     
     代理zuul-euraka-server，并开始 okhttp client ,并且过滤客户端的cookie,并且过滤服务端代理传递的header
     设置代理超时时间，配置hystrix 超时时间，配置重试次数等，详见配置application.yml
     首先启动eureka-example/example-001作为注册中心 ，然后启动zuul-euraka-server
     http://localhost:3001/user/hello?what=100     
### zuul-example-002  
    zuul +hystrix demo     
    代理zuul-euraka-server
    首先启动eureka-example/example-001作为注册中心 ，然后启动zuul-euraka-server
    http://localhost:3002/user/hello?what=100    ,可以正常访问，然后停掉zuul-euraka-server，
    在访问   http://localhost:3002/user/hello?what=100，发现进入fallback
### zuul-example-003
    filter：pre，router，post,error
    首先启动eureka-example/example-001作为注册中心 ，然后启动zuul-euraka-server
    最后启动自己
    http://localhost:3003/user/hello?what=100
    看后台打印和返回的header
    
    接着停掉zuul-euraka-server，再次访问http://localhost:3003/user/hello?what=100，进入errorfiler

### zuul-example-004
    代理指定的url
    http://localhost:3004/user/hello?what=100       
### zuul-example-005
    动态url 路由 可以实时更新
     CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator     
### zuul-example-006
    再请求的时候确定路由信息，并进行改变，可以通过route filter 实现,RequestContext.getCurrentContext().setRouteHost()
    配置文件里故意写错，有filter进行更改。     
##  stream-example
    spring cloud stream 是基于spring integration 实现。
    事例 用kafka 作为作为中间件 binder
     Binder：是对中间件的抽象，通过与外部中间件连接的实现为 binder
     Binding： 用于创建 input和output两种类型的管道
     Channel ： 管道 ，发送和接受消息，也就是input和output实现类 
     Input： 用于订阅消息
     Output: 用于输出message ，也就是send，发布消息
     middleware: 中间件 kafka、rabbitmq
     
     注意：
     如果引入一个Binder 的实现，那么默认所有的管道都会绑定到这个binder上，现在就是kafka，如果是多个，
     需要指定通道的Binder进行配置
     spring.cloud.stream.bindings.通道名称.binder=Binder名称  ，
     或者设置默认的Binder
     spring.cloud.stream.defaultBinder=Binder名称
     
     
     
     【属性说明】
     
     【生产者属性公共属性】;
     以下绑定属性可用于输出绑定，但只能并必须以spring.cloud.stream.bindings.<channelName>.producer.为前缀。
     例如，spring.cloud.stream.bindings.input.producer.partitionKeyExpression=payload.id。 
       默认值可以使用spring.cloud.stream.default.producer前缀来设置，
     例如，spring.cloud.stream.default.producer.partitionKeyExpression=payload.id。 
     partitionKeyExpression 
       决定如何分区流出数据的SpEL表达式。如果设置，或者设置了partitionKeuExtractorClass，这个通道的流出数据会被分区，
     且partitionCount必须设置为大于1的值才能生效。这两个选项是互斥的。参阅分区支持。 
       默认值为：null。 
     partitionKeyExtractorClass 
       PartitionKeyExtractorStrategy的实现。如果设置，或者设置了partitionKeyExpression，此通道的流出数据会被分区，
     且partitionCount必须设置为大于1的值才能生效。这两个选项是互斥的。参阅分区支持。 
       默认值为：null。 
     partitionSelectorClass 
       PartitionSelecctorStrategy的实现。和partitionSelecorExpression互斥。如果设置了其中一个，分区将被选择为hashCode(key) % partitionCount，
     其中的key是通过partitionKeyExpression或partitionKeyExtractorClass来计算的。 
       默认值为：null。 
     partitionSelectorExpression 
       用于自定义分区选择的SpEL表达式。和partitionSelectorClass互斥。如果设置了其中一个，分区将被选择为hashCode(key) % partitionCount，
     其中的key是通过partitionKeyExpression或partitionKeyExtractorClass来计算的。 
       默认值为：null。 
     partitionCount 
       数据的目标分区的数量（如果分区已启用）。 如果生产者是分区的，则必须设置为大于1的值。 在Kafka上意味着使用 此值和目标主题分区数量中的较大值。 
       默认值为：1。 
     requiredGroups 
       生产者必须确保消息传递的组群列表（逗号分隔），即使它们是在创建之后启动的（例如，通过在RabbitMQ中预先创建持久队列）。 
     headerMode 
       设置为none时，禁用输出上的头部嵌入。 仅对本身不支持消息头但需要嵌入头部的消息中间件有效。 
     当从非Spring Cloud Stream应用消费数据而原生头部不被支持的时候，
     此选项非常有用。如果设置为headers，使用使用中间件本身的头部机制。如果设置为embeddedHeaders，在消息负载中嵌入头部。 
       默认值为：取决于binder实现。 
     useNativeEncoding 
       设置为true时，流出消息将直接由客户端库序列化，客户端库必须相应地进行配置（例如，设置适当的Kafka生产者value serializer）。 
     使用此配置时，流出消息编组不是基于绑定的contentType。 当使用本地编码时，消费者有责任使用适当的解码器（例如：Kafka消费者value de-serializer）
     来反序列化流入消息。 而且，当使用本地编码/解码时，headerMode = embeddedHeaders属性将被忽略，并且头部不会嵌入到消息中。 
     errorChannelEnabled 
       设置为true时，如果binder支持异步发送结果; 发送失败的消息将被发送到目的地（destination）的错误通道。 
     有关更多信息，请参阅消息通道绑定和错误通道。 
       默认值为：false。
      
     【消费者公共属性】
     下边的绑定属性值对输入绑定可用的且必须以spring.cloud.stream.bindings.<channelName>.consumer为前缀。
     例如，spring.cloud.stream.bindings.input.consumer.concurrency=3。 
       默认值可以使用spring.cloud.stream.default.consumer前缀来设置，
     例如，spring.cloud.stream.default.consumer.headerMode=none。 
      concurrency 
        流入消费者的并发性。 
        默认为：1。 
      partitioned 
        消费者是否接受来自一个分区的生产者数据。 
        默认为：false。 
      headerMode 
        如果设置为none，则禁用输入的头部处理。仅对本身不支持消息头但需要嵌入头部的消息传递中间件有效。 
      当从非Spring Cloud Stream应用消费数据而原生头部不被支持的时候，此选项非常有用。如果设置为headers，
      使用使用中间件本身的头部机制。如果设置为embeddedHeaders，在消息负载中嵌入头部。 
        默认为：取决于binder实现。 
      maxAttempts 
        如果处理失败，则尝试处理该消息的次数（包括第一次）。 设置为1以禁用重试。 
        默认值为： 3。 
      backOffInitialInterval 
        回退乘数 
        默认值为：2.0。 
      instanceIndex 
        当设置为大于等于0的值的时候，允许自定义此消费者的实例索引（如果与spring.cloud.stream.instanceIndex不同）。
      当设置为一个负值的时候，默认为spring.cloud.stream.instanceIndex。 
        默认值为：-1。 
      instanceCount 
        当设置为大于等于0的值的时候，允许自定义此消费者的实例数量（如果不同于spring.cloud.stream.instanceCount）。
      如果设置为负值，默认为spring.cloud.stream.instanceCount。 
        默认值为：-1。
      
      
      【Kafka 绑定（binder）属性】：
      spring.cloud.stream.kafka.binder.brokers 
        Kafka绑定（binder）将连接到的代理（broker）列表。 
        默认为： localhost。 
      spring.cloud.stream.kafka.binder.defaultBrokerPort 
        代理（broker）允许指定具有或不具有端口信息的主机（例如，host1，host2：port2）。 
      当代理列表（broker）中没有配置端口时，将设置默认端口。
      默认为： 9092
      
      spring.cloud.stream.kafka.binder.zkNodes 
        Kafka绑定（binder）可以连接的ZooKeeper节点列表。 
        默认为： localhost。 
      spring.cloud.stream.kafka.binder.defaultZkPort 
        zkNodes允许指定具有或不具有端口信息的主机（例如，host1，host2：port2）。 当节点列表中没有配置端口时，将设置默认端口。 
        默认为： 2181。 
     spring.cloud.stream.kafka.binder.configuration 
       传递给由绑定器创建的所有客户端的用户属性（包括生产者和消费者）的键/值 map。 由于生产者和消费者都会使用这些属性，因此应该将使用限制在公共属性中，特别是安全设置。 
       默认为： 空map。 
     spring.cloud.stream.kafka.binder.headers 
       将由绑定（binder）传输的自定义头部的列表。 
       默认为： 空。 
     spring.cloud.stream.kafka.binder.healthTimeout 
       以秒为单位的等待分区信息的时间，默认值60。如果此计时器到期，运行状况将报告为down。 
       默认为： 10。 
     spring.cloud.stream.kafka.binder.offsetUpdateTimeWindow 
       以毫秒为单位的保存偏移量的频率。 如果为0，则忽略。 
       默认为： 10000。 
     spring.cloud.stream.kafka.binder.offsetUpdateCount 
       持续消费偏移量更新数量的频率（The frequency, in number of updates, which which consumed offsets are persisted.）。 
         如果为0，则忽略。与offsetUpdateTimeWindow互斥。 
       默认为： 0。 
     spring.cloud.stream.kafka.binder.requiredAcks 
       需要代理（broker）受到应答的数量。 
       默认为： 1。 
     spring.cloud.stream.kafka.binder.minPartitionCount 
       仅在设置autoCreateTopics或autoAddPartitions时有效。 绑定（binder）将在其生产/消费数据的主题（topic）上配置的分区的全局最小数量。 
     它可以被生产者的partitionCount设置或生产者的instanceCount * concurrency设置的值所替代（其中较大的值）。 
       默认为： 1。 
     spring.cloud.stream.kafka.binder.replicationFactor 
       autoCreateTopics处于活动状态时,自动创建主题（topics ）复制因子（ replication factor ）。 
       默认为： 1。 
     spring.cloud.stream.kafka.binder.autoCreateTopics 
       如果设置为true，则绑定（binder）将自动创建新的主题（topic）。 如果设置为false，则绑定（binder）将依赖于已经配置的主题（topic）。 
     在后一种情况下，如果主题（topic）不存在，绑定（binder）将无法启动。 值得注意的是，此设置与代理（topic）的auto.topic.create.enable设置无关，
     并且不影响代理：如果服务器设置为自动创建主题（topic），则可以将它们和默认代理设置一起创建为元数据检索请求的一部分。 
       默认为： true。 
     spring.cloud.stream.kafka.binder.autoAddPartitions 
       如果设置为true，则绑定（binder）将根据需要创建添加新分区。 如果设置为false，则绑定（binder）将依赖于已经配置的主题的分区大小。 
     如果目标主题的分区数量小于期望值，绑定（binder）将无法启动。 
       默认为： false。 
     spring.cloud.stream.kafka.binder.socketBufferSize 
       Kafka消费者使用的套接字缓冲区的大小（以字节为单位）。 
       默认为： 2097152。 
     spring.cloud.stream.kafka.binder.transaction.transactionIdPrefix 
       在绑定（binder）中启用事务; 请参阅Kafka文档中的transaction.id和spring-kafka文档中的事务。 启用事务时，将忽略各个的producer属性，
     并且所有生产者都使用spring.cloud.stream.kafka.binder.transaction.producer.*属性。 
       默认为： null（没有事务）。 
     spring.cloud.stream.kafka.binder.transaction.producer. 
       事务绑定（binder）中生产者的全局生产者属性。 请参阅spring.cloud.stream.kafka.binder.transaction.transactionIdPrefix和
     [Kafka Producer Properties以及所有绑定（binder）支持的常规生产者属性。 
       默认为：查看各个生产者属性。
       
     【Kafka 消费者属性】：
     以下属性仅供Kafka使用者使用，且必须以spring.cloud.stream.kafka.bindings.<channelName>.consumer.为前缀。 
     autoRebalanceEnabled 
       如果为true，则主题（topic）分区将在消费者组的成员之间自动重新平衡。 如果为false，
     则会根据spring.cloud.stream.instanceCount和spring.cloud.stream.instanceIndex为每个使用者分配一组固定的分区。 
     这需要在每个启动的实例上正确设置spring.cloud.stream.instanceCount和spring.cloud.stream.instanceIndex属性。 
     在这种情况下，属性spring.cloud.stream.instanceCount通常必须大于1。 
       默认为： true。 
     autoCommitOffset 
       是否在处理消息完成后自动提交偏移量。 如果设置为false，则在流入消息中将包含一个类型为
     org.springframework.kafka.support.Acknowledgment的keykafka_acknowledgment的头部。 
     应用可以使用这个头来确认消息。 详细信息请参阅示例部分。 当此属性设置为···false···时，Kafka绑定（binder）
     将把应答确认模式设置为org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode.MANUAL。 
       默认为： true。 
     autoCommitOnError 
       仅在autoCommitOffset设置为true时有效。 如果设置为false，则会取消导致错误的消息的自动提交，并且只会提交成功的消息，
     允许流从上次成功处理的消息中自动重放，以防发生持续失败。 如果设置为true，它将始终自动提交（如果启用了自动提交）。 
     如果未设置（默认），则它与enableDlq实际上具有相同的值，如果消息被发送到DLQ，则自动提交错误消息，否则不提交它们。 
       默认为：not set。 
     recoveryInterval 
       尝试恢复连接的间隔，以毫秒为单位。 
       默认为：5000。 
     resetOffsets 
       是否将消费者的偏移量重置为startOffset提供的值。 
       默认为：false。 
     startOffset 
       新组的量起始偏移。 允许值：earliest，latest。 如果消费者组被显式设置为消费者“绑定”（
     通过spring.cloud.stream.bindings.<channelName> .group），那么’startOffset’被设置为earliest; 否则将其设置为anonymous消费者组的latest。 
       默认为：null （等同于earliest）。 
     enableDlq 
       设置为true时，它将为消费者发送启用DLQ行为。 默认情况下，导致错误的消息将被转发到名为error.<destination>.<group>的主题（topic）。
      DLQ主题（topic）名称可以通过属性dlqName进行配置。 当错误数量相对较少时，这为更常见的Kafka重放场景提供了一个替代选项，
      但重放整个原来的主题可能太麻烦。 
       默认为：false。 
     configuration 
       包含通用Kafka消费者属性的键/值对的Map。 
       默认为：Empty map。 
     dlqName 
       接收错误消息的DLQ主题（topic ）的名称。 
       默认为：null（如果未指定，则导致错误的消息将被转发到名为error.<destination>.<group>的主题（topic）。
     
    【 Kafka生产者属性】：
     以下属性仅适用于Kafka生产者，必须以spring.cloud.stream.kafka.bindings.<channelName>.producer.为前缀。
     bufferSize 
       Kafka 生产者在发送之前将尝试批量处理多少数据的上限（以字节为单位）。 
       默认为：16384。 
     sync 
       生产者是否为异步的。 
       默认为：false。 
     batchTimeout 
       在发送之前，生产者需要等待多长时间才能让更多的消息在同一批次中累积。 （通常情况下，生产者根本不会等待，
     只是发送前一次发送过程中累积的所有消息。）非零值可能会增加吞吐量，但会提高延迟。 
       默认为：0。 
     messageKeyExpression 
       根据流出消息计算的SpEL表达式，用于填充生成的Kafka消息的key。 例如headers.key或payload.myKey。 
       默认为：none。 
     headerPatterns 
       以逗号分隔的简单模式列表，用于匹配Spring消息头以映射到ProducerRecord中的kafka headers。 模式可以以通配符（星号）
     开始或结束。 模式可以通过用!前缀来取反。 匹配在第一次匹配成功后停止（positive or negative）。 例如!foo,fo *会通过fox
     但foo不会通过。 id和timestamp不会进行映射。 
       默认为：*（除了id和timestamp之外的所有头部）。 
     configuration 
       包含通用Kafka生产者属性的键/值对的Map。 
       默认为：Empty map  
### stream-example-001
     手动订阅消息  
     sink.input().subscribe
### stream-example-002
      @StreamListener(Sink.INPUT)  实现订阅消息   
      通过注解获取header 和 全部header  ,@Header,@Headers
      注意：如果@StreamListener注解的方法有返回值，那么必须使用@SendTo注解指明返回的值写入哪个通道     
### stream-example-003
    Spring Cloud Stream 可以有任意数量的通道，默认spring cloud 自带了 Source 、Sink、Processer
    自定义通道测试      
### stream-example-004
    消息的分发功能   @StreamListener
    根据hello 的值不同，找不同的处理管道
    @StreamListener(value = Sink.INPUT,condition = "headers['hello']=='word'")
    @StreamListener(value = Sink.INPUT,condition = "headers['hello']=='hello'")

### stream-example-005  stream-example-006  stream-example-007   
    分区：
    如何计算分区key，可以通过下面两种方式
    过配置partitionKeyExpression或者partitionKeyExtractorClass属性中的一个（并且只能配置一个），
    以及partitionCount属性来配置配置输出绑定，例如：
    spring.cloud.stream.bindings.output.producer.partitionKeyExpression=payload.id
    spring.cloud.stream.bindings.output.producer.partitionCount=5
    
    根据partitionKeyExpression计算每个发送到分区的输出通道的消息的分区键值（partition key’s value）
    partitionKeyExpression是一个根据流出消息计算的SpEL表达式，用于取得分区的key（partitioning key）。
    
    如果一个SpEL表达式不能满足需求，你可以通过将属性partitionKeyExtractorClass设置为实现了
    org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy接口的类来计算分区键值。
     虽然SpEL表达通常情况下够用，但更复杂的情况下可以使用自定义实现策略。 
     在这种情况下，属性’partitionKeyExtractorClass’可以设置如下
     spring.cloud.stream.bindings.output.producer.partitionKeyExtractorClass=com.example.MyKeyExtractor
     spring.cloud.stream.bindings.output.producer.partitionCount=5
     
     如何设置分区方法：
     模式是 key.hash mod partitionCount-1
     
     自定义实现 
     定义MyPartitionKeyExtractor，其实现PartitionKeyExtractorStrategy、PartitionSelectorStrategy接口
     
     
    stream-example-005 producer 支持分区设置
    stream-example-006 consumer 读取分区0的数据
    stream-example-007 consumer 读取分区1的数据
    
    先启动stream-example-006 stream-example-007，然后启动stream-example-005 发送数据，看接受端输出
    停掉任何一个，都可以进行自动重新平衡消费的。
    
### stream-example-008
    @SendTo 消费消息有返回值的情况，要输出到另外一个管道    
### stream-example-009
    运行时 动态设置管道    
 
### stream-example-010
    自定义消息转换器 ，需要使用 @StreamConverter 注释 进行限定才可以
    消息转换器，默认不写就是application/json	
    
    框架需要@StreamMessageConverter限定符注释，以避免获取到ApplicationContext上可能存在的其他转换器，并可能与默认的转换器重叠。 
     如果你的消息转换器需要使用特定的content-type和目标类（对于输入和输出），则消息转换器需要扩展
    org.springframework.messaging.converter.AbstractMessageConverter。若使用@StreamListener进行转换，一个实现
    org.springframework.messaging.converter.MessageConverter的消息转换器就足够了。 
     本例子是在Spring Cloud Stream应用程序中创建消息转换器bean（带有内容类型application/bar）的示例
### stream-example-011
     kafka 手动提交offset       
### stream-example-012
    错误处理 
    @ServiceActivator(inputChannel = "test12.group-aa.errors")     
##  bus-example
    spring cloud bus  
    spring cloud bus 消息总线，是基于spring cloud stream 实现
    【大致流程】
    客户端发送刷新事件请求（POST请求 /actuator/bus-refresh/${applicationContextId}:*），消息总线会产生一个event事件，并将该事件发送到
    消息中间件 kafka 或者其他mq，其他配置配置消息总线的client，都会监听mq的消息，监听的client 收到该消息后会转发为spring 内部
    事件进行下发，spring内部监听器，可以进行相应的处理
     ${applicationContextId}:* 一般是 serviceId:port，而 serviceId 就是在 application.properties 中配置的 spring.application.name
    消息总线提供的端点 /actuator/bus-refresh/${applicationContextId}:* 和 Actuator 自带的端点 /actuator/refresh 
    作用是相同的，都是刷新配置项，区别主要在于：
    /actuator/refresh：刷新本地配置项
    /actuator/bus-refresh/${applicationContextId}:*：刷新远程应用配置项
    【主要的事件】
    RemoteApplicationEvent： spring 事件的基类，以下事件都是基于他实现，是其他事件类的基类，定义了事件对象的公共属性
    RefreshRemoteApplicationEvent： 刷新事件，刷新远端应用配置的事件，用于接收远端刷新的请求。
    AckRemoteApplicationEvent:确认远端应用事件，该事件表示一个特定的RemoteApplicationEvent事件被确认。
    EnvironmentChangeRemoteApplicationEvent:环境变更的事件
    UnknownRemoteApplicationEvent：未知事件,该事件类与之前的SentApplicationEvent、AckRemoteApplicationEvent有关，
                                    当序列化时遇到事件的类型转换异常，则自动构造成一个未知的远端应用事件
    SentApplicationEvent:  发送事件 和上面事件不同，他不是继承RemoteApplicationEvent 
                           发送应用事件，表示系统中的某个地方发送了一个远端事件
                           
    spring 对应的内部监听
    RefreshListener 监听    RefreshRemoteApplicationEvent
    EnvironmentChangeListener 监听  EnvironmentChangeRemoteApplicationEvent
    TraceListener 监听 AckRemoteApplicationEvent 和 SentApplicationEvent
    
    消息发送和监听请看
    BusAutoConfiguration
    发送和接受通道 ：SpringCloudBusClient 中定义
    
    bus 相关属性 参考 BusProperties 这个类   
    
    实现自己的事件用于bus，要继承  RemoteApplicationEvent                
      
### bus-example-001  
    观察RefreshListener 输出，监听 RefreshRemoteApplicationEvent
    RefreshListener源码输出：
    @Override
    public void onApplicationEvent(RefreshRemoteApplicationEvent event) {
        Set<String> keys = contextRefresher.refresh();
        log.info("Received remote refresh request. Keys refreshed " + keys);
    }
    post 请求：http://localhost:9090/actuator/bus-refresh
    内部其实是发送了一个 topic 为springCloudBus 到kafka
    
### bus-example-002
    自定义   RefreshRemoteApplicationEvent 的事件监听器
    post 请求 ： http://localhost:9092/actuator/bus-refresh/bus-example-002
    其中bus-example-002 可以是 bus-example-002:9092 ，也可以不写，不写就会广播所有，写就是广播某一个服务
    bus-example-002： 代表广播bus-example-002 服务
    bus-example-002:9092 代表广播bus-example-002:9092 服务
    不写广播所有bus的client
### bus-example-003 
    自定义远程事件 RemoteApplicationEvent   
    
  
##  sleuth-example
    链路跟踪
    一些概念：
    
    1.Span，Span是基本的工作单元。Span包括一个64位的唯一ID，一个64位trace码，描述信息，时间戳事件，key-value 注解(tags)，span处理者的ID（通常为IP）。
    最开始的初始Span称为根span，此span中span id和 trace id值相同。
    2.Trance，包含一系列的span，它们组成了一个树型结构
    3.Annotation，用于及时记录存在的事件。常用的Annotation如下：
    cs - Client Sent：客户端发送一个请求，表示span的开始
    sr - Server Received：服务端接收请求并开始处理它。(sr-cs)等于网络的延迟
    ss - Server Sent：服务端处理请求完成，开始返回结束给服务端。(sr-ss)表示服务端处理请求的时间
    cr - Client Received：客户端完成接受返回结果，此时span结束。(cr-cs)表示客户端接收服务端数据的时间
    
    sender 相关
    ZipkinSenderProperties
    Zipkin 属性
    ZipkinProperties
    Kafka 相关
    ZipkinKafkaSenderConfiguration
    
    
    collector 属性
    ZipkinKafkaCollectorProperties
    
    zipkin: https://github.com/openzipkin/zipkin
        sleuth-example-001和sleuth-example-002
       如果是内存存储采集数据，请注释掉kafka 相关的配置
       如果是mysql 存储采集数据，请注释掉kafka 相关的配置
       如果是kafka ，请不要注释kafka相关的配置  
       
       相关sleuth和zipkin 收集端配置
       spring:
         zipkin:
           #base-url: http://localhost:5000
           #base-url: http://localhost:4999   # 下面是发送到kafka 所以base-url不需要
           sender:
             type: kafka  # 采集数据异步发送到kafka，不采用kafka 将其注释掉就可以了
         kafka:
            bootstrap-servers: 10.12.52.21:9092  # 用于发送到kafka
         sleuth:
           sampler:
             probability: 1 #设置采样率，为了测试设置100%采集，设置为1.0   
### zipkin-server
    展示数据，将数据放入内存
     http://localhost:5000
### zipkin-server-mysql
    展示数据，将数据放入mysql 
     http://localhost:4999
### zipkin-server-msyql-kafka
    展示数据，异步将数据放入mysql 
     http://localhost:4998         
### sleuth-example-001
    服务提供   
     
### sleuth-example-002
    调用服务 sleuth-example-001
    http://localhost:5002/getUsername/jufeng
    然后观察  zipkin ui 展示的数据   
    