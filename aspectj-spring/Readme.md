## SpringBoot Config

Spring 异步、事务、缓存实现都是基于 AOP

SpringBoot可以指定 @Async、@Transactional、@Cacheable 使用AspectJ代理。
通过以下注解设置：

```java
@EnableAsync(mode = AdviceMode.ASPECTJ)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableCaching(mode = AdviceMode.ASPECTJ)
```

## Spring CTW 使用


### 项目简介

本项目结合SpringBoot使用基于Java Annotation的切面。

Spring中AOP默认使用JDK动态代理模式，在调用同类方法时，`this`关键字将指向对象本身，而非代理类，所以同类中的方法调用无法通过proxy增强。使用AspectJ可以解决这个问题。


### Maven

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
	
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjrt</artifactId>
	</dependency>
	<!-- spring aspectJ aspects library -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aspects</artifactId>
	</dependency>

### Plugin

	
	<plugin>
		<groupId>org.codehaus.mojo</groupId>
		<artifactId>aspectj-maven-plugin</artifactId>
		<dependencies>
			<dependency>
				<groupId>org.aspectj</groupId>
				<artifactId>aspectjtools</artifactId>
				<version>${aspectj.version}</version>
			</dependency>
		</dependencies>
		<executions>
			<execution>
				<goals>
					<goal>compile</goal>
				</goals>
			</execution>
		</executions>
		<configuration>
			<source>${maven.compiler.source}</source>
			<target>${maven.compiler.target}</target>
			<encoding>${project.build.sourceEncoding}</encoding>
			<complianceLevel>${maven.compiler.target}</complianceLevel>
			<Xlint>ignore</Xlint>
			<showWeaveInfo>true</showWeaveInfo>
			<forceAjcCompile>true</forceAjcCompile>
			<aspectLibraries>
				<aspectLibrary> <!-- library of the aspect -->
					<groupId>org.springframework</groupId>
					<artifactId>spring-aspects</artifactId>
				</aspectLibrary>
			</aspectLibraries>
		</configuration>
	</plugin>
	


### 验证

#### 1. 正常调用，两个异步方法不在一个class, Spring AOP 可以实现异步

1. 不用借助 aspectjtools 的 ajc 编译
2. 关闭ASPECTJ： `AppSpringAdvice` 中 开启 `@EnableAsync(mode = AdviceMode.PROXY)` 关闭 `@EnableAsync(mode = AdviceMode.ASPECTJ)`
3. 执行 `AppSpringNormal`


输出结果, A 和 B 在两个线程， 实现异步执行

```
runA in thread[22], runB in thread[27]
runA in thread[23], runB in thread[23]
runA in thread[20], runB in thread[25]
runA in thread[21], runB in thread[21]
runA in thread[19], runB in thread[26]
```


#### 2. 正常调用，两个异步方法在一个class, Spring AOP 可以不能实现异步

1. 不用借助 aspectjtools 的 ajc 编译
2. 关闭ASPECTJ： `AppSpringAdvice` 中 开启 `@EnableAsync(mode = AdviceMode.PROXY)` 关闭 `@EnableAsync(mode = AdviceMode.ASPECTJ)`
3. 执行 `AppSpringAdvice`


输出结果, A 和 B 在一个线程， 不能实现异步执行

```
runA in thread[22], runB in thread[22]
runA in thread[23], runB in thread[23]
runA in thread[19], runB in thread[19]
runA in thread[21], runB in thread[21]
runA in thread[20], runB in thread[20]
```

#### 3. 织入后调用，两个异步方法在一个class, Spring AOP 可以不能实现异步，但是 AspectJ 可以实现异步执行

1. 借助 aspectjtools 的 ajc 编译
2. 开启ASPECTJ： `AppSpringAdvice` 中 开启 `@EnableAsync(mode = AdviceMode.ASPECTJ)` 关闭 `@EnableAsync(mode = AdviceMode.PROXY)`
3. 执行 `AppSpringAdvice`


输出结果, A 和 B 在两个线程， 实现异步执行

```
runA in thread[22], runB in thread[16]
runA in thread[17], runB in thread[22]
runA in thread[24], runB in thread[20]
runA in thread[23], runB in thread[17]
runA in thread[16], runB in thread[23]
```

反编译 DomainAdviceAo 源码

```
package com.jos.aop.aspectjspring.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.aspectj.AbstractAsyncExecutionAspect;
import org.springframework.scheduling.aspectj.AnnotationAsyncExecutionAspect;
import org.springframework.stereotype.Component;

@Component
public class DomainAdviceAo
{
  @Async
  public void runA()
  {
    JoinPoint localJoinPoint = Factory.makeJP(ajc$tjp_0, this, this);Object[] arrayOfObject = new Object[2];arrayOfObject[0] = this;arrayOfObject[1] = localJoinPoint;AnnotationAsyncExecutionAspect.aspectOf().ajc$around$org_springframework_scheduling_aspectj_AbstractAsyncExecutionAspect$1$6c004c3e(new DomainAdviceAo.AjcClosure1(arrayOfObject), ajc$tjp_0, localJoinPoint);
  }
  
  @Async
  public void runB(long threadId)
  {
    long l = threadId;JoinPoint localJoinPoint = Factory.makeJP(ajc$tjp_1, this, this, Conversions.longObject(l));Object[] arrayOfObject = new Object[3];arrayOfObject[0] = this;arrayOfObject[1] = Conversions.longObject(l);arrayOfObject[2] = localJoinPoint;AnnotationAsyncExecutionAspect.aspectOf().ajc$around$org_springframework_scheduling_aspectj_AbstractAsyncExecutionAspect$1$6c004c3e(new DomainAdviceAo.AjcClosure3(arrayOfObject), ajc$tjp_1, localJoinPoint);
  }
  
  static {}
}

```
