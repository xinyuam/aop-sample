## AspectJ 加载时织入 和 Spring 结合

1. 不需要 ajc 编译， 在加载时织入, 内存中用的是织入的 calss ，整个spring的启动和注入没有发生任何变化
3. 关系: \
	AspectJ 加载时织入，使用 aspectjweaver-1.9.7.jar 作为 JVMTI 的 agent 启动 \
	Spring 是基于 java 的应用， 操作的是 JVM class，因此，编译期的操作不会影响spring


## AspectJ LTW 实现

1. 编辑 ` src/main/resources/META-INF/aop.xml ` 指定切面信息
2. 使用 aspectjweaver-1.9.7.jar 作为 JVMTI 的 agent 启动，实现LTW 


### Maven

	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
	</dependency>
	
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
	
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aspects</artifactId>
	</dependency>

### Plugin
	
	不需要编译插件的支持，需要启动指定 javaagent
	
### 验证

#### 1. 本地测试，运行 `AppLtwAjbean`

启动时设置 VM arguments ： -javaagent:E:/jarfile/aspectjweaver-1.9.7.jar


输出结果, 包含织入代码

```

Hello World!
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run000(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:12) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run00(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:16) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run0(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:20) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:24) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.setName0(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:32) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.setName1(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(boolean com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.equals(java.lang.Object))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(boolean com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.canEqual(java.lang.Object))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
class com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo
-------- step0
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.568
ProxyDomain run000: step1
-------- step1
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
ProxyDomain run0: step2
-------- step2
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
ProxyDomain run0: step3
-------- step3
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
ProxyDomain run1: step4
-------- step4
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
-------- step5
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
-------- step6
 -- AspectWithAnnotation before exce: 2023-11-24T16:09:36.569
-------- step7



```


#### 2. Spring测试，运行 `AppSpringLtwAjbean`

Bean的注入和之前一样，不存在改变

启动时设置 VM arguments ： -javaagent:E:/jarfile/aspectjweaver-1.9.7.jar


输出结果, 包含织入代码
	执行spring启动之前，先输出 `weaveinfo` 的日志信息
	启动之后，输出织入之后的日志信息
	反编译 ProxyDomain 的信息，没有织入的信息 

```

[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run000(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:12) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run00(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:16) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run0(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:20) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.run(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:24) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.setName0(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:32) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.setName1(java.lang.String))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(boolean com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.equals(java.lang.Object))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(boolean com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo.canEqual(java.lang.Object))' in Type 'com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo' (ProxyDomainBo.java:5) advised by before advice from 'com.jos.aop.aspectjspringltw.ajbean.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.6)

[AppClassLoader@18b4aac2] warning javax.* types are not being woven because the weaver option '-Xset:weaveJavaxPackages=true' has not been specified
2023-11-24 16:10:19.099  INFO 153076 --- [           main] c.j.a.a.ajbean.AppSpringLtwAjbean        : Starting AppSpringLtwAjbean using Java 1.8.0_361 on TSJ-015240 with PID 153076 (E:\project\project-test\aop\aop_sample\aop-sample\aspectj-spring-ltw-ajbean\target\classes started by yonghua.cao in E:\project\project-test\aop\aop_sample\aop-sample\aspectj-spring-ltw-ajbean)
2023-11-24 16:10:19.106  INFO 153076 --- [           main] c.j.a.a.ajbean.AppSpringLtwAjbean        : No active profile set, falling back to 1 default profile: "default"
2023-11-24 16:10:20.101  INFO 153076 --- [           main] c.j.a.a.ajbean.AppSpringLtwAjbean        : Started AppSpringLtwAjbean in 1.87 seconds (JVM running for 3.351)
class com.jos.aop.aspectjspringltw.ajbean.bean.ProxyDomainBo
-------- step0
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.116
ProxyDomain run000: step1
-------- step1
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
ProxyDomain run0: step2
-------- step2
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
ProxyDomain run0: step3
-------- step3
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
ProxyDomain run1: step4
-------- step4
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
-------- step5
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
-------- step6
 -- AspectWithAnnotation before exce: 2023-11-24T16:10:20.117
-------- step7



```



