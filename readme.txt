修改代码需要重启的处理方案：
1.POM文件添加
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <!--<scope>provided</scope>-->
            <optional>true</optional>
        </dependency>
 2.file-settings-compile->build project automaticlly
 3.alt+ctrl+shift+/ 选择registry 然后在compiler.automake.allow.when.app.running后面的筛选框打勾
 4.若还无效果，则点击run 里面的 edit configurations中on update action和on frame deactivation中均选择 update classes and resources

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
六、TOKEN认证基于JSON Web Token(JWT)，参见JwtTokenUtil类
    https://www.cnblogs.com/cndarren/p/11518443.html
七、接口类*Mapper无法识别，使用注解@Mapper解决。

八，Swagger注解
@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"


@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"


@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）
        dataType：参数类型，默认String，其它值dataType="Integer"
        defaultValue：参数的默认值


@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类


@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
九、com.google.code.kaptcha.Producer报错
Field producer in org.felix.controller.login.UserConroller required a bean of type 'com.google.code.kaptcha.Producer' that could not be found.

在config目录下定义一个类KaptchaConfig，添加@Configuration， 生成DefaultKaptcha方法，配置@Bean



