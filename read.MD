分布式事务的四种解决方式：
前提：前创建1一个父级项目，再创建三个子级项目[eureka-center,bank1,bank2](注册中心，工商银行，中国银行)

1.method 使用rocketMQ 消息通知[三张表：账号表account_info，转账支付表account_pay，事务表bank1_duplication]
 示例： 用户凤凰小哥哥使用中国银行bank2给工商银行bank1转账
 步骤： 1.用户输入转账账号 & 资金接收账号 & 转账金额发起请求
        2.减去转账账号金额，增加转账支付表记录
        3.发送rocketMQ消息给工商银行
        4.两种方式[4-1，rocketMQ自动监听，4-2，主动查询]：
        4-1.工商银行rocketMQ[RocketmqListener]消息监听器收到消息后，判断事务是否执行过，进行增加金额金额修改数据
          入库操作 & 添加日志
        4-2.工商银行主动查询中国银行的转账，进行金额修改数据入库操作。
        [为了保证避免重复转账操作，在修改之前先进行查找事务表，如果已经存在，则不做修改操作，
                不存在则进行修改入库操作，然后插入事务信息表]。
 注意： 1.亲测可用[由于4种方法，且代码在一起，测试method1 时，将bank2 ProducerTxmsgListener
       类中的“执行本地事务，扣减金额”下一句注释掉哦，不然会发生重复扣款操作。这一个类是方法四的操作]。               
        
2.method 使用Seata 分布式事务中间件保证事务[account_info]
示例： 用户凤凰小哥哥使用中国银行bank2给工商银行bank1转账
步骤： 1.pom 文件引入spring-cloud-alibaba-seata
       2.用户输入转账账号 & 资金接收账号 & 转账金额发起请求
       3.在bank2业务层加@Transactional &  @GlobalTransactional即可[资金转出方]
       4.配置registry.conf & file.conf 文件
       4.由于需要搭建Seata-server，太麻烦，故不做实验，知道这种方式即可
       
3.method 使用tcc两阶段进行分布式事务
示例： 用户凤凰小哥哥使用中国银行bank2给工商银行bank1转账
步骤[资金转出方]：
      1.pom 文件引入hmily-springcloud
      2.yml 文件配置hmily信息
      3.java读取yml hmily 配置信息  
      4.创建三张表：local_try_log | local_confirm_log | local_cancel_log 
      5-1.在业务实现上加上注解@Hmily(confirmMethod="commit",cancelMethod="rollback")[commit & rollback 分别是两个方法]：
      5-2.获取全局事务id[String transId = HmilyTransactionContextLocal.getInstance().get().getTransId()]
      5-3.根据事务id 查询local_try_log ，如果存在，则不进行操作。
      5-4.如果local_confirm_log | local_cancel_log 已经存在了，则不进行操作。
      5-5.进行账户扣减金额[同时插入local_try_log 日志]，如果报错则抛出异常
      5-6.调用外部服务，进行[资金接收方]转账增加金额操作，如果报错则抛出异常
      ----------------------------------------------------------------------
      commit method: 记录日志
      rollback method: 
      1.获取全局事务id
      2.根据事务id 查询local_cancel_log ，如果存在，则不进行操作。 
      3.根据事务id 查询local_try_log ，   如果不存在，则不进行操作。
      4.进行账户增加金额[同时插入local_cancel_log 日志]
   [资金接收方]
      1.pom 文件引入hmily-springcloud
      2.yml 文件配置hmily信息
      3.java读取yml hmily 配置信息
      4-1.在业务实现上加上注解@Hmily(confirmMethod="commit",cancelMethod="rollback")[commit & rollback 分别写两个方法]：
      4-2.获取全局事务id[String transId = HmilyTransactionContextLocal.getInstance().get().getTransId()] 
      4-3.分别实现commit & rollback 方法
      commit method:
      1.根据事务id 查询local_confirm_log,如果存在，则不进行操作
      2.进行转账落库操作，增加资金接收方金额[添加local_confirm_log日志]
      rollback method: 记录日志
      
4.method: 使用事务+rocketMQ消息机制
示例： 用户凤凰小哥哥使用中国银行bank2给工商银行bank1转账
步骤[资金转出方]：
     1.发送rocketMQ 消息
     2.消息监听器ProducerTxmsgListener【executeLocalTransaction】执行扣减金额
    [资金接收方]：
     1.rocketMQ 消息监听器监听消息[TxmsgConsumer]  
     2.进行判断事务是否已执行过
     3.资金接收方进行增加金额操作
     4.添加事务日志落库操作。  




hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000  # 设置熔断超时时间  default 1000
        timeout:
          enabled: true # 打开超时熔断功能 default true

org:
  dromara:
    hmily :
      serializer : kryo
      recoverDelayTime : 30
      retryMax : 30
      scheduledDelay : 30
      scheduledThreadMax :  10
      repositorySupport : db
      started: false
      hmilyDbConfig :
        driverClassName  : com.mysql.jdbc.Driver
        url :  jdbc:mysql://localhost:3306/bank_transaction?useUnicode=true
        username : root
        password : root                 