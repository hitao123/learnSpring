# Getting Started

## Introduce

### 1. 学习 Spring Boot Shiro 如何做权限管理

### 2. 学习 Spring 如何处理 cookie，登录态

### 3. 学习 Spring 异常控制

## Problem

### 权限表的设计

1.用户表设计(保存用户的基本信息)

```sql
CREATE TABLE `mall_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(63) NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) NOT NULL DEFAULT '' COMMENT '管理员密码',
  `last_login_ip` varchar(63) DEFAULT '' COMMENT '最近一次登录IP地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) DEFAULT '''' COMMENT '头像图片',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `role_ids` varchar(127) DEFAULT '[]' COMMENT '角色列表',
  PRIMARY KEY (`id`)
);
```

2.权限表(保存用户的权限信息)

```sql
DROP TABLE IF EXISTS `mall_permission`;
CREATE TABLE `mall_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `permission` varchar(63) DEFAULT NULL COMMENT '权限',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';
```

3.角色表(保存系统存在的角色)

```sql
DROP TABLE IF EXISTS `mall_role`;
CREATE TABLE `mall_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(63) NOT NULL COMMENT '角色名称',
  `desc` varchar(1023) DEFAULT NULL COMMENT '角色描述',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
```

### 前端RBAC管理系统

1. 基于 [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template) permision 分支进行开发修改
2. 功能点

```html
1. 登入和登出
2. 菜单和按钮权限根据登录用户角色不同而不一样
3. 目前只设计两种角色，超级管理员，基于菜单的管理员，基于按钮的权限管理员
```

### 问题记录

1. 开始的时候是 rest 返回数据结构和 后台模板的不一致，导致定制头 getToken 无法设置头部，所以一直是 401
2. 解决完上面的一个问题发现 role 不能为空，接着在 role 表插入响应的数据库返回，解决问题

### 如何创建定时任务

> 在前端我们可以使用 setInterval, setTimeOut 处理定时任务，在 spring 我们可以使用 @Scheduled 来启动我们的定时任务，启动类要
加上 @Scheduled 注解

```html
@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
@Scheduled(cron="*/5 * * * * *") ：通过cron表达式定义规则
```

### 如何基于 shiro @RequiresPermissions 注解来实现菜单权限控制

- 在 `AdminAuthorizingRealm` 实现类里面我们注入了三个 service, 也对应着上面的三张表，根据业务的不同，我们需要向三张表里面插入数据，详细的数据可以查看 sql 目录的 mall_data.sql

```java
    @Autowired
    private MallAdminService adminService;
    @Autowired
    private MallRoleService roleService;
    @Autowired
    private MallPermissionService permissionService;

    在 doGetAuthorizationInfo 授权方法里面

    1. 先获取当前 登录用户
    2. 查询当前用户的角色 id
    3. 根据 id 查询角色表里面的角色
    4. 根据 id 查询权限表里面的权限
    5. SimpleAuthorizationInfo 实例化 设置 roles 和 permissions
    6. Controller 通过使用 @RequiresPermissions("X:X:X") 来实现权限控制
    7. 到这里我们已经能根据用户的角色来实现对接口权限的控制
```

- 目前还遗留的问题，因为前端需要菜单和按钮根据不同的角色要实现控制，如何提供更好的接口方式告知前端进行权限控制呢？

### com.github.pagehelper 帮助实现分页功能

> 查询列表的功能经常需要做分页查询，这里是利用开源的 pagehelper 来实现分页的功能，后续可以对这个源码进行了解一下