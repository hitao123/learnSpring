# Getting Started

## Introduce

> 本项目简介

### ✅ 1. 学习使用 MyBatis Generator 生成模型成数据

### ✅ 2. 学习生成数据之后如何提供服务

### ✅ 3. 学习 Spring 如何记录日志和跟踪日志

### ✅ 4. 学习 Spring Boot redis 的使用方法，如何做 redis 缓存

### ✅ 5. 学习 Spring Boot Devtools 的使用方法

### 6. 学习 Spring Boot Shiro 如何做权限管理

### 7. 学习 Spring 如何处理 cookie，登录态

### 8. 学习 Spring 异常控制

## Problem

1.什么是 myBatis

> myBatis 是一个基于Java的持久层(简单的说就是将内存数据保存到数据库磁盘上)框架, 是ORM的一种实现框架，都是对JDBC的一种封装

2.什么是 ORM

> ORM 就是通过实例对象的语法，完成关系型数据库的操作的技术，是"对象-关系映射"（Object/Relational Mapping） 的缩写。

```html
ORM 把数据库映射成对象。
数据库的表（table） --> 类（class）
记录（record，行数据）--> 对象（object）
字段（field）--> 对象的属性（attribute）
```

3.为什么使用 [mybatis generator](https://github.com/mybatis/generator) ?

> 自动生成 ORM层代码，典型地包括我们日常需要手写的 POJO、mapper xml 以及 mapper 接口等。MyBatis Generator 
自动生成的 ORM层代码几乎可以应对大部分 CRUD 数据表操作场景

```html
自动生成MyBatis的 mapper、dao、entity 的框架，让我们省去规律性最强的一部分最基础的代码编写。
可配置性强；可拓展性强，支持插件
配置简单，只需要一个配置文件
功能强大，对于单表操作不需要额外写任何代码（包含分页、排序）
insert、update语句会生成两套，全部更新和只更新非空字段
select抽离大文本（BLOB）字段，可选择是否需要查询大文本字段，提供查询效率
```

4.为什么使用数据库连接池 [druid-spring-boot-starter](https://github.com/alibaba/druid) ?

```html
对于一个简单的数据库应用，由于对于数据库的访问不是很频繁。这时可以简单地在需要访问数据库时，
就新创建一个连接，用完后就关闭它，这样做也不会带来什么明显的性能上的开销。
但是对于一个复杂的数据库应用，情况就完全不同了。频繁的建立、关闭连接，会极大的减低系统的性能，
因为对于连接的使用成了系统性能的瓶颈。连接复用。通过建立一个数据库连接池以及一套连接使用管理策略，
使得一个数据库连接可以得到高效、安全的复用，避免了数据库连接频繁建立、关闭的开销。
对于共享资源，有一个很著名的设计模式：资源池。该模式正是为了解决资源频繁分配、释放所造成的问题的。
把该模式应用到数据库连接管理领域，就是建立一个数据库连接池，提供一套高效的连接分配、使用策略，
最终目标是实现连接的高效、安全的复用。
```

5.MyBatis Generator 自动生成的几部分是什么关系 ？

```html
1. 首先在我们的项目中产生了 `dao entity` 以及 resource 里面的 Mapper.xml (映射上面的dao层)
2. resource 里面的 Mapper.xml 对应真实的 sql 语句
3. resource 里面的 Mapper.xml namespace 与 dao 层 mapper 一一对应，dao 层为 接口，dao 层通过
xml 结合 mybatis 实际上就已经可以提供查询 sql 了， 在 service 层面通过 @Resource 注解 接口，就可以调用
dao 层接口的 sql 方法了
4. entity 里面包含两部分内容，一个是对应数据库的实体类，另外有一个Example 类， 实体类好理解，对应映射 table，
resource 里面的 Mapper.xml ==》 resultMap ==》type="com.example.learn.db.entity.MallAd"，
5. Example类指定如何构建一个动态的where子句.

```

6.如何通过 MBG 在 spring Boot 对外提供服务

```html
1. 首先和 dao 层同一级新建 service 对外面提供服务
2. 由于现在都是微服务分模块开发的，为了方便，我是在本项目新建了一个 web 目录，直接向外边暴露api
```

7.spring Boot devtools 的作用?

> spring Boot devtools 的作用是热加载，对本地开发非常友好，类比前端就是编辑器改动保存之后，浏览器reload，这里是服务器热更新
这部分代码，能实时看到改动

## Summary

- generatorConfig.xml `<properties resource="xx.properties">` 使用 yml 无效
- 在写 MallDemoTest 测试的时候，运行报错原因是没有注入 bean 在启动类前面要加上这个注解

```java
@SpringBootApplication(scanBasePackages = {"com.example.learn.db"})
@MapperScan("com.example.learn.db.dao") // @MapperScan可以指定要扫描的Mapper类的包的路径
```

- 遇到的问题

> 在 idea 上直接使用插件报错，在命令上执行下面的命令就没有错误, 补充 升级 mybatis-generator-plugin 到 1.2.16 通过 idea 生成没有出现上面这个问题

```bash
"/backProj/learnSpring/02.Start-Spring-Boot/mvnw" mybatis-generator:generate -f "/backProj/learnSpring/02.Start-Spring-Boot/pom.xml"

idea error

Execution default-cli of goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.7:generate failed: A required class was missing while executing org.mybatis.generator:mybatis-generator-maven-plugin:1.3.7:generate: org/dom4j/io/SAXReader

```

- 日志问题

前提：没有使用Log4j 日志插件，使用 Spring 自带日志

1. 日志分为 ERROR, WARN, INFO, DEBUG or TRACE 一般是这 4 个级别，程度由高到地，可以针对不同的包做设置
2. application.yml 设置日志的时候，file 和 path 只能设置一个，优先以file为主，path 后面不给具体的文件名，spring.log 为默认的，
3. todo: 这里以后有了解产线如何存储日志的，可以接着这里补充

```yml
  file: logfile.log
  path: app/logs
```

## Referance

1. [数据库连接池的作用](https://blog.csdn.net/dly1580854879/article/details/73088884)
2. [MBG 的配置文件详细解析](https://gitee.com/free/Mybatis_Utils/blob/master/MybatisGeneator/MybatisGeneator.md)
3. [mybatis Mapper xml 详解](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html)
4. [spring Boot 日志处理](https://blog.csdn.net/yu0_zhang0/article/details/83898819)
