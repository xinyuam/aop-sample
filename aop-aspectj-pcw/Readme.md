## PCW: 编译后编织

需要配合 AspectJ 的 ajc 实现编译织入
aspectjtools.jar: 提供AJC编译器，可以在编译期将 `.java`、`.class`、`.aj`定义的切面编织到源代码中

示例代码中使用 Lombok 实现编译， 使用 AJC 实现编译后织入



## 开发

### 1. Eclipse 安装 AJDT
	https://www.eclipse.org/ajdt/
	
### 2. PCW 开发支持注解方式
	注解方式    -- 参考`AspectWithAnnotation`
	aspect 语法    -- 暂时不能编译成功, TODO

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
				<phase>process-classes</phase>  <!-- change compile phase -->
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
			<aspectDirectory>src/main/java</aspectDirectory>
			<weaveDirectories>   <!-- change weave directory -->
				<weaveDirectory>${project.build.outputDirectory}</weaveDirectory>
			</weaveDirectories>
			<sources /> <!-- Don't compile the source code folder -->
		</configuration>
	</plugin>
	

## 验证

### 1. 编译后执行 `AppPcw` 输出测试结果

	Hello World!
	class com.jos.aop.aspectjpcw.proxy.ProxyDomain
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.502
	ProxyDomain run0: step0
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.503
	ProxyDomain run0: step1
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.503
	ProxyDomain run1: step2
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.503
	step3
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.503
	step4
	 -- AspectWithAnnotation before exce: 2023-11-22T18:04:02.503
	step5


### 2. 反编译 `ProxyDomain` 源码


	package com.jos.aop.aspectjpcw.proxy;
	
	import com.jos.aop.aspectjpcw.aspect.AspectWithAnnotation;
	import java.io.PrintStream;
	
	public class ProxyDomain
	{
	  private static String name0;
	  private String name1;
	  
	  public int hashCode()
	  {
	    int PRIME = 59;int result = 1;Object $name1 = getName1();result = result * 59 + ($name1 == null ? 43 : $name1.hashCode());return result;
	  }
	  
	  protected boolean canEqual(Object other)
	  {
	    AspectWithAnnotation.aspectOf().before();return other instanceof ProxyDomain;
	  }
	  
	  public boolean equals(Object o)
	  {
	    AspectWithAnnotation.aspectOf().before();
	    if (o == this) {
	      return true;
	    }
	    if (!(o instanceof ProxyDomain)) {
	      return false;
	    }
	    ProxyDomain other = (ProxyDomain)o;
	    if (!other.canEqual(this)) {
	      return false;
	    }
	    Object this$name1 = getName1();Object other$name1 = other.getName1();return this$name1 == null ? other$name1 == null : this$name1.equals(other$name1);
	  }
	  
	  public ProxyDomain()
	  {
	    super();
	  }
	  
	  public void setName1(String name1)
	  {
	    AspectWithAnnotation.aspectOf().before();this.name1 = name1;
	  }
	  
	  public String toString()
	  {
	    return "ProxyDomain(name1=" + getName1() + ")";
	  }
	  
	  public String getName1()
	  {
	    return this.name1;
	  }
	  
	  public static void run0(String args)
	  {
	    AspectWithAnnotation.aspectOf().before();System.out.println("ProxyDomain run0: " + args);
	  }
	  
	  public void run1(String args)
	  {
	    AspectWithAnnotation.aspectOf().before();System.out.println("ProxyDomain run1: " + args);
	  }
	  
	  public static String getName0()
	  {
	    return ProxyDomain.name0;
	  }
	  
	  public static void setName0(String name0)
	  {
	    AspectWithAnnotation.aspectOf().before();ProxyDomain.name0 = name0;
	  }
	}




