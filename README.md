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
       
