## AspectJ 编译时织入、编译后织入 和 Spring 结合

1. 需要配合 AspectJ 的 ajc 实现编译织入 aspectjtools.jar: 提供AJC编译器，可以在编译期将 .java、.class、.aj定义的切面编织到源代码中
2. 编译后生成新的字节码文件，整个spring的启动和注入没有发生任何变化
3. 关系
	AspectJ 编译时织入、编译后织入 改变的是字节码
	Spring 是基于 java 的应用， 属于运行期时的范畴， 对加载之前的字节码是透明的，因此，编译期的操作不会影响spring


## AspectJ CTW 和 PCW 实现

CTW 作用于编译时，作用于源码文件
PCW 作用于编译后，作用域class文件(lombok 编译后)


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
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjrt</artifactId>
	</dependency>

### Plugin

	
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
	


### 验证

#### 1. main启动，执行 `AppSpringCtw`


输出结果, 包含织入代码

```
Hello World!
class com.jos.aop.aspectjspringctw.bean.ProxyDomain
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.156
ProxyDomain run0: step0
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.157
ProxyDomain run0: step1
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.157
ProxyDomain run1: step2
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.157
step3
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.157
step4
 -- AspectWithAnnotation before exce: 2023-11-23T16:26:04.157
step5

```


#### 1. spring启动，执行 `AppSpringCtwBean`

1. 程序启动，可以获取到织入之后的bean



输出结果, 包含织入代码

```
class com.jos.aop.aspectjspringctw.bean.ProxyDomain
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
ProxyDomain run0: step0
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
ProxyDomain run0: step1
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
ProxyDomain run1: step2
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
step3
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
step4
 -- AspectWithAnnotation before exce: 2023-11-23T16:27:34.555
step5
```


反编译 `ProxyDomain` 源码

```

package com.jos.aop.aspectjspringctw.bean;

import com.jos.aop.aspectjspringctw.aspect.AspectWithAnnotation;
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


```
