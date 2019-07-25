# Getting Started

## 提供简单的 REST

## 获取 application.yml 的数据

## 如何使用 @Autowired 注入bean

## 笔记

### java 注解

注解分为两类：

1、一类是使用Bean, 即是把已经在xml文件中配置好的Bean拿来用，完成属性、方法的组装；比如@Autowired 通过byTYPE, @Resource 通过byNAME的方式获取Bean；
2、一类是注册Bean, @Component, @Controller, @Service, @Repository, @Configration这些注解都是把你要实例化的对象转化成一个Bean，放在IoC容器中，等你要用的时候，它会和上面的@Autowired, @Resource配合到一起，把对象、属性、方法完美组装。
3. @Bean 放在方法上，产生一个Bean
4. @Component 泛指组件类，就是在自身定位不明确的时候使用
5. @Service 表明这是业务层的组件
6. @Controller 表明这是控制层的组件
7. 在业务层和控制层也可以用 @Component，但是这会导致代码不易读
