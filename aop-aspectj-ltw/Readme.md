## LTW: 加载时编织

不需要配合 AspectJ 的 ajc 实现编译织入
使用 aspectjweaver-1.9.7.jar 作为 JVMTI 的 agent 启动


## 开发

### 1. Eclipse 安装 AJDT
	https://www.eclipse.org/ajdt/
	
### 2. LTW 开发支持注解方式
	注解方式    -- 参考`AspectWithAnnotation`
	aspect 语法    -- 暂时不能编译成功, TODO

### 3. 需要在配置文件(/src/main/resources/META-INF/aop.xml) 指定 aspects 的配置

`/aop-aspectj-ltw/src/main/resources/META-INF/aop.xml`

	
	<?xml version="1.0"?>
	<!-- document: https://www.eclipse.org/aspectj/doc/released/devguide/ltw-configuration.html#configuring-load-time-weaving-with-aopxml-files -->
	<aspectj>
	    <weaver options="-showweaveInfo -Xlint:ignore"/>
	    <aspects>
	        <aspect name="com.jos.aop.aspectjltw.aspect.AspectWithAnnotation"/>
	    </aspects>
	</aspectj>


### 4. 不需要 ajc 的支持， 但是需要在运行时集成 JVMTI 的 agent （aspectjweaver）

启动时设置 VM arguments ： -javaagent:E:/jarfile/aspectjweaver-1.9.7.jar



## 验证

### 1. 编译后执行 `AppPcw` 输出测试结果

	Hello World!
	[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjltw.proxy.ProxyDomain.run0(java.lang.String))' in Type 'com.jos.aop.aspectjltw.proxy.ProxyDomain' (ProxyDomain.java:6) advised by before advice from 'com.jos.aop.aspectjltw.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
	[AppClassLoader@18b4aac2] weaveinfo Join point 'method-execution(void com.jos.aop.aspectjltw.proxy.ProxyDomain.run1(java.lang.String))' in Type 'com.jos.aop.aspectjltw.proxy.ProxyDomain' (ProxyDomain.java:10) advised by before advice from 'com.jos.aop.aspectjltw.aspect.AspectWithAnnotation' (AspectWithAnnotation.java)
	class com.jos.aop.aspectjltw.proxy.ProxyDomain
	 -- AspectWithAnnotation before exce: 2023-11-22T18:42:15.021
	ProxyDomain run0: step0
	 -- AspectWithAnnotation before exce: 2023-11-22T18:42:15.022
	ProxyDomain run0: step1
	 -- AspectWithAnnotation before exce: 2023-11-22T18:42:15.022
	ProxyDomain run1: step2



### 2. 反编译 `ProxyDomain` 源码，源码没有变化，但是实现了代码静态织入


	package com.jos.aop.aspectjltw.proxy;
	
	import java.io.PrintStream;
	
	public class ProxyDomain
	{
	  public static void run0(String args)
	  {
	    System.out.println("ProxyDomain run0: " + args);
	  }
	  
	  public void run1(String args)
	  {
	    System.out.println("ProxyDomain run1: " + args);
	  }
	}


