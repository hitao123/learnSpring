# Getting Started

## Introduce

> 本项目简介

### 1. 学习使用 MyBatis Generator 生成模型成数据

### 2. 学习生成数据之后如何提供服务

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

> 自动生成 ORM层代码，典型地包括我们日常需要手写的 POJO、mapper xml 以及 mapper 接口等。MyBatis Generator 自动生成的 ORM层代码几乎可以应对大部分 CRUD 数据表操作场景

```html
自动生成MyBatis的 mapper、dao、entity 的框架，让我们省去规律性最强的一部分最基础的代码编写。
可配置性强；可拓展性强，支持插件
配置简单，只需要一个配置文件
功能强大，对于单表操作不需要额外写任何代码（包含分页、排序）
insert、update语句会生成两套，全部更新和只更新非空字段
select抽离大文本（BLOB）字段，可选择是否需要查询大文本字段，提供查询效率
```

4.[MBG 的配置文件详细解析](https://gitee.com/free/Mybatis_Utils/blob/master/MybatisGeneator/MybatisGeneator.md)
5.为什么使用数据库连接池 [druid-spring-boot-starter](https://github.com/alibaba/druid) ?
6.MyBatis Generator 自动生成的几部分是什么关系 ？
7.如何通过 MBG 在 spring Boot 对外提供服务

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
