/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50513
Source Host           : 127.0.0.1:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2016-07-28 16:33:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `classDirector_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fu9khrtclhj8x5rr9r3fpcbd2` (`classDirector_id`),
  CONSTRAINT `FK_fu9khrtclhj8x5rr9r3fpcbd2` FOREIGN KEY (`classDirector_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '一班', '1');
INSERT INTO `classes` VALUES ('2', '二班', '1');
INSERT INTO `classes` VALUES ('3', '三班', null);
INSERT INTO `classes` VALUES ('4', '三班', null);
INSERT INTO `classes` VALUES ('5', '四班', null);

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('30', '班级信息', '/classes/show', null, null);
INSERT INTO `privilege` VALUES ('31', '教师信息', '/teacher/show', null, null);
INSERT INTO `privilege` VALUES ('32', '测试json', '/classes/testJson', null, '30');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('4', '教师');
INSERT INTO `role` VALUES ('5', '年级主任');

-- ----------------------------
-- Table structure for role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `role_privilege`;
CREATE TABLE `role_privilege` (
  `role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of role_privilege
-- ----------------------------
INSERT INTO `role_privilege` VALUES ('5', '30');
INSERT INTO `role_privilege` VALUES ('5', '31');
INSERT INTO `role_privilege` VALUES ('5', '32');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birthday` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `superviseclass_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2lrxybqn1tke3riiy8fj594qp` (`superviseclass_Id`),
  CONSTRAINT `FK_2lrxybqn1tke3riiy8fj594qp` FOREIGN KEY (`superviseclass_Id`) REFERENCES `classes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', null, '小贺', null, '1');
INSERT INTO `teacher` VALUES ('2', null, '小刘', null, '2');

-- ----------------------------
-- Table structure for teacher_classes
-- ----------------------------
DROP TABLE IF EXISTS `teacher_classes`;
CREATE TABLE `teacher_classes` (
  `teacher_id` bigint(20) NOT NULL,
  `classes_id` bigint(20) NOT NULL,
  PRIMARY KEY (`teacher_id`,`classes_id`),
  KEY `FK_et9codq50y2mx2uv6s3dljj2j` (`classes_id`),
  CONSTRAINT `FK_et9codq50y2mx2uv6s3dljj2j` FOREIGN KEY (`classes_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `FK_t6nara4tjb32qvm5j65g5bwxm` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of teacher_classes
-- ----------------------------
INSERT INTO `teacher_classes` VALUES ('1', '1');
INSERT INTO `teacher_classes` VALUES ('2', '1');
INSERT INTO `teacher_classes` VALUES ('1', '2');
INSERT INTO `teacher_classes` VALUES ('2', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'heqing', 'heqing');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2', '4');
INSERT INTO `user_role` VALUES ('2', '5');
