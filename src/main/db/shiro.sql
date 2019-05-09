/*
SQLyog Community v12.5.1 (64 bit)
MySQL - 5.7.25 : Database - ssm_security
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssm_security` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssm_security`;

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `pid` varchar(36) DEFAULT NULL COMMENT '父菜单id',
  `permission_name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`pid`,`permission_name`,`url`) values 
('basic-manage','0','基础数据',''),
('permission01','basic-manage','产品管理','normal/product.html'),
('permission02','basic-manage','订单管理','normal/order.html'),
('permission03','system-manage','用户管理','admin/account.html'),
('permission04','system-manage','角色管理','admin/role.html'),
('permission05','system-manage','权限管理','admin/permission.html'),
('permission06','system-manage','支付管理','admin/pay.html'),
('system-manage','0','系统管理','');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`role_desc`) values 
('role01','ROLE_SUPER','超级管理员'),
('role02','ROLE_ADMIN','管理员'),
('role03','ROLE_USER','普通用户');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `permission_id` varchar(36) DEFAULT NULL,
  `role_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`permission_id`,`role_id`) values 
('permission01','role01'),
('permission02','role01'),
('permission04','role01'),
('permission05','role01'),
('permission03','role01'),
('system-manage','role01'),
('basic-manage','role01'),
('permission03','role02'),
('permission04','role02'),
('permission05','role02'),
('system-manage','role02'),
('permission01','role03'),
('permission02','role03'),
('basic-manage','role03'),
('permission06','role01');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `phone_num` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `status` varchar(10) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`),
  KEY `username_idx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`email`,`password`,`phone_num`,`status`) values 
('531391d3-7b9b-4287-ae23-67c009fd4bfa','haha',NULL,NULL,NULL,NULL),
('user01','super','admin@qq.com','123','110','ok'),
('user02','admin','15712058302@163.com','123','13283705681','bad'),
('user03','小红','xiaohong@qq.com','123','13283705681','ok');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values 
('user01','role01'),
('user02','role02'),
('user03','role03');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
