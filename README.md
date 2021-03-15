
> 本工具通过接口实现数据脱敏，不会改变原始数据结构和类型（有些方法是通过将数据转为json实现，这样如果项目中还有其他需要对结果进行转换的方法就不能实现了）。

##### 仓库坐标
```xml
 <dependency>
      <groupId>com.github.duanyashu</groupId>
       <artifactId>datamask</artifactId>
       <version>1.0.0</version>
 </dependency>
```
#### 使用

 1. **添加扫包**

	由于使用了aop需要先添加扫包
	

```java
springboot 在项目启动类上添加上包路径（注意：需要将本工具配置到最后，防止其他切面修改数据结构）

@SpringBootApplication(scanBasePackages = {"你的项目包路径","com.github.duanyashu"})

```

 2. DesensitiveApi 实体字段注解

   

```java
 @DesensitiveInfo(DesensitiveType.EMAIL)
  指定要脱敏的字段类型（名称，手机，座机，地址，身份证号，邮箱，银行卡）
  除了指定格式，也可以自定义脱敏格式
  @DesensitiveInfo(retainLeft = 3,retainRight = 3,padStr = "*",separator = "@")
  retainLeft 左边显示个数 
  retainRight 右边显示个数
  padStr 替换的字符
  separator 分隔符 （eg:邮箱的“@”，座机的“-”）
```
 3. DesensitiveApi 接口类，方法注解

    在需要脱敏的接口上或者类上添加上这个注解
