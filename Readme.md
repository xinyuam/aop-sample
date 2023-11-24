
## AspectJ 是什么

AspectJ 是一个 AOP 的具体实现框架。AOP（Aspect Oriented Programming）即面向切面编程，可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。

### 织入时间

将定义横切关注点(切面)插入到编写的源代码中的过程，称为编织。 在 AspectJ 中，编织有3个不同的时间段:

1. Compile-time(CTW): 编译时编织，AJC编译器从源代码编译并生成编织好的文件输出。
2. Post-compile time: 编译后编织，用于编织已编译的类。（简写为 `PCW`）
3. Load-time(LTW): 加载时编织，在类加载器将类加载到JVM时编织，需要提供AspectJ Java Agent支持.

### AspectJ Jar

我们经常会使用到AspecJ提供的三个Jar包：

1. aspectjrt.jar: 提供运行时的注解、静态方法，基本上用到AspectJ都需要引入依赖。
2. aspectjweaver.jar: 包含AspectJ运行时、编织器、编织类装入器和编织代理。它还包含用于解析XML编织配置文件的DTD。
3. aspectjtools.jar: 提供AJC编译器，可以在编译期将 `.java`、`.class`、`.aj`定义的切面编织到源代码中。


## Spring AOP

Spring AOP是用纯Java实现的。不需要特殊的编译过程。
Spring AOP 不需要控制类加载器层次结构，因此适合在 servlet 容器或应用程序服务器中使用。
Spring AOP 当前仅支持方法执行连接点,其他的建议采用 AspectJ 


spring aop 可以基于 xml 和 注解 声明
    使用 xml 可以不引入 AspectJ 的依赖， spring 会自动解析 xml
    使用 注解 需要引入 AspectJ 的依赖， spring aop 使用了 AspectJ 的注解
spring aop 注解是采用了AspectJ的注解，因此只能算是采用了AspectJ风格开发( @AspectJ style)，
底层代理实现还是动态代理(JdkDynamicProxy + CglibProxy， 默认 JdkDynamicProxy )


## 示例代码结构

- <module>aop-aspectj-ctw</module> : 纯 AspectJ 实现 CTW
- <module>aop-aspectj-pcw</module> : 纯 AspectJ 实现 PCW
- <module>aop-aspectj-ltw</module> : 纯 AspectJ 实现 LTW
- <module>aspectj-spring</module> : spring 框架配合 `@Async` 实现 AspectJ 的织入
- <module>aspectj-spring-ctw</module> : spring 框架集成 AspectJ 实现 CTW 和 PCW
- <module>aspectj-spring-ltw-ajbean</module> : spring 框架集成 AspectJ 实现 LTW
- <module>aspectj-spring-ltw-spbean</module> : spring 框架集成 AspectJ 通过 spring提供的 spring-instrument-5.3.18.jar实现，测试未成功


## 版本信息

- 2023-11-22 V1 

		
		学习和初始化
		2023-11-22
		
