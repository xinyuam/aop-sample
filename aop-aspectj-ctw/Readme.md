## CTW: 编译时编织

需要配合 AspectJ 的 ajc 实现编译织入
aspectjtools.jar: 提供AJC编译器，可以在编译期将 `.java`、`.class`、`.aj`定义的切面编织到源代码中



## 开发

### 1. Eclipse 安装 AJDT
	https://www.eclipse.org/ajdt/
	
### 2. CTW 开发支持两种语法
	注解方式    -- 参考`AspectWithAnnotation`
	aspect 语法    -- 参考`AspectWithAspect`

### 3. 使用 aspectjtools.jar 编译插件进行编译

	<plugin>
		<groupId>org.codehaus.mojo</groupId>
		<artifactId>aspectj-maven-plugin</artifactId>
		<version>${maven.aspectj.version}</version>
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
					<goal>compile</goal> <!-- aspectj:compile. it is executed by default in the Maven:compile phase. -->
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
			<forceAjcCompile>true</forceAjcCompile>  <!-- mandatory use of ajc compiler -->
		</configuration>
	</plugin>
	

## 验证

### 1. 编译后执行 `AppCtw` 输出测试结果

	Hello World!
	class com.jos.aop.aspectjctw.proxy.ProxyDomain
	 -- AspectWithAnnotation before exce: 2023-11-22T17:26:03.413
	ProxyDomain run0: step0
	 -- AspectWithAspect after exce: 2023-11-22T17:26:03.414
	 -- AspectWithAnnotation before exce: 2023-11-22T17:26:03.414
	ProxyDomain run0: step1
	 -- AspectWithAspect after exce: 2023-11-22T17:26:03.414
	 -- AspectWithAnnotation before exce: 2023-11-22T17:26:03.414
	ProxyDomain run1: step2
	 -- AspectWithAspect after exce: 2023-11-22T17:26:03.414


### 2. 反编译 `ProxyDomain` 源码


	package com.jos.aop.aspectjctw.proxy;
	
	import com.jos.aop.aspectjctw.aspect.AspectWithAnnotation;
	import com.jos.aop.aspectjctw.aspect.AspectWithAspect;
	import java.io.PrintStream;
	
	public class ProxyDomain
	{
	  public static void run0(String args)
	  {
	    try
	    {
	      AspectWithAnnotation.aspectOf().before();System.out.println("ProxyDomain run0: " + args);
	    }
	    catch (Throwable localThrowable)
	    {
	      AspectWithAspect.aspectOf().ajc$after$com_jos_aop_aspectjctw_aspect_AspectWithAspect$1$694065ff();throw localThrowable;
	    }
	    AspectWithAspect.aspectOf().ajc$after$com_jos_aop_aspectjctw_aspect_AspectWithAspect$1$694065ff();
	  }
	  
	  public void run1(String args)
	  {
	    try
	    {
	      AspectWithAnnotation.aspectOf().before();System.out.println("ProxyDomain run1: " + args);
	    }
	    catch (Throwable localThrowable)
	    {
	      AspectWithAspect.aspectOf().ajc$after$com_jos_aop_aspectjctw_aspect_AspectWithAspect$1$694065ff();throw localThrowable;
	    }
	    AspectWithAspect.aspectOf().ajc$after$com_jos_aop_aspectjctw_aspect_AspectWithAspect$1$694065ff();
	  }
	}



