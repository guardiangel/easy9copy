一、  添加application.yml 注意里面的写法，保持空格等。
二、 filters: stat,wall,slf4j 使用slf4j,可以不用在pom中配置log4j
三、Easy9CopyStartUpApplication类中的@EnableAspectJAutoProxy注解，参考LoginCheckAspect和SysLogAspect类

四、切面注解介绍：

    @Aspect:作用是把当前类标识为一个切面供容器读取
    @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
    @Around：环绕增强，相当于MethodInterceptor
    @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
    @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
    @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
    @After: final增强，不管是抛出异常或者正常退出都会执行
五、TokenSettings
    @ConfigurationProperties