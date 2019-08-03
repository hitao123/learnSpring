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