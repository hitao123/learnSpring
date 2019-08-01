# Getting Started

## Introduce

> 本项目简介

### ✅ 1. 学习使用 MyBatis Generator 生成模型成数据

### ✅ 2. 学习生成数据之后如何提供服务

### ✅ 3. 学习 Spring 如何记录日志和跟踪日志

### ✅ 4. 学习 Spring Boot redis 的使用方法，如何做 redis 缓存

### ✅ 5. 学习 Spring Boot Devtools 的使用方法

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

8.为什么要进行序列化？什么时候进行序列化

```html
序列化就是一种用来处理对象流的机制，所谓对象流也就是将对象的内容进行流化,将数据分解成字节流，以便存储在文件中或在网络上传输。
通俗的说就是将 Java 对象转换成公共的格式（各个系统都能识别的）叫做序列化，将公共的格式转换成对象叫做反序列化
```

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

- Redis 相关注解

```html
@CacheConfig

这个注解在类上使用，用来描述该类中所有方法使用的缓存名称

@Cacheable

这个注解一般加在查询方法上，表示将一个方法的返回值缓存起来，默认情况下，缓存的key就是方法的参数，缓存的value就是方法的返回值

@CachePut

这个注解一般加在更新方法上，当数据库中的数据更新后，缓存中的数据也要跟着更新，使用该注解，可以将方法的返回值自动更新到已经存在的key上

@CacheEvict

这个注解一般加在删除方法上，当数据库中的数据删除后，相关的缓存数据也要自动清除，
该注解在使用的时候也可以配置按照某种条件删除(condition属性)或者或者配置清除所有缓存(allEntries属性)
```

- Redis 遇到的问题

1. 最开始导入 `spring-boot-starter-data-redis` 依赖发现如下错误, 无法找到 bean，原因是 SPDR 使用注解 @ConditionalOnMissingBean
@AutoConfigureAfter(RedisAutoConfiguration.class)，RedisConfig 里面 RedisTemplate 实现类无法注入bean，而且 pom.xml 配置也有问题
需要排除 lettuce ，然后添加 jedis 依赖，配置文件也要做相应修改，后来该问题不出现了，后面还要从根本上理解这个问题出现的原因，[思路](https://www.cnblogs.com/zhangyy3/p/9127109.html)，在具体使用 Service 层添加 `@Cacheable("brand")` 注解的时候，接口报 500 的序列化错误，
原因是返回的实体类没有实现 Serializable 接口，添加  Mybatis 插件`<plugin type="org.mybatis.generator.plugins.SerializablePlugin" />`
重新生成一遍，重新启动项目，就可以了，接口正常返回， 本地 Redis 服务器能看到存储的数据和 log

```java
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of method hashOperations in com.example.learn.config.RedisConfig required a bean of type 'org.springframework.data.redis.core.RedisTemplate' that could not be found.

The following candidates were found but could not be injected:
	- Bean method 'redisTemplate' in 'RedisAutoConfiguration' not loaded because @ConditionalOnMissingBean (names: redisTemplate; SearchStrategy: all) found beans named redisTemplate


Action:

Consider revisiting the entries above or defining a bean of type 'org.springframework.data.redis.core.RedisTemplate' in your configuration.

================================

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
  <exclusions>
    <exclusion>
      <groupId>io.lettuce</groupId>
      <artifactId>lettuce-core</artifactId>
    </exclusion>
      </exclusions>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>

================================

jedis:
  pool:
    max-active: 8
    max-wait: -1
    max-idle: 8
    min-idle: 0
```

## Referance

1. [数据库连接池的作用](https://blog.csdn.net/dly1580854879/article/details/73088884)
2. [MBG 的配置文件详细解析](https://gitee.com/free/Mybatis_Utils/blob/master/MybatisGeneator/MybatisGeneator.md)
3. [mybatis Mapper xml 详解](http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html)
4. [spring Boot 日志处理](https://blog.csdn.net/yu0_zhang0/article/details/83898819)
5. [为什么要进行序列化](https://blog.csdn.net/tlycherry/article/details/8986720)
6. [@RequestParam @RequestBody @PathVariable 等参数绑定注解详解](https://blog.csdn.net/walkerJong/article/details/7946109)
