/*
Navicat MySQL Data Transfer

Source Server         : pi
Source Server Version : 80025
Source Host           : 192.168.1.100:3306
Source Database       : rbac

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-01-18 22:23:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rbac_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_resource`;
CREATE TABLE `rbac_resource` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `type` tinyint NOT NULL COMMENT '类型 1.接口 2.菜单',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源标识/地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of rbac_resource
-- ----------------------------
INSERT INTO `rbac_resource` VALUES ('1', '1', '用户列表', '/user/list', '2022-01-18 20:31:35', '2022-01-18 20:32:14');
INSERT INTO `rbac_resource` VALUES ('2', '1', '用户更新', '/user/update', '2022-01-18 20:32:03', '2022-01-18 20:32:20');

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role` (
  `id` int NOT NULL COMMENT '角色id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名',
  `desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of rbac_role
-- ----------------------------
INSERT INTO `rbac_role` VALUES ('1', '普通用户', '普通用户', '2022-01-18 20:29:25', '2022-01-18 20:29:54');
INSERT INTO `rbac_role` VALUES ('2', '会员', '会员', '2022-01-18 20:29:33', '2022-01-18 20:29:54');
INSERT INTO `rbac_role` VALUES ('3', '管理员', '管理员', '2022-01-18 20:29:40', '2022-01-18 20:29:54');

-- ----------------------------
-- Table structure for rbac_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_resource`;
CREATE TABLE `rbac_role_resource` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int NOT NULL COMMENT '角色id',
  `resource_id` int NOT NULL COMMENT '资源id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_role_resource` (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of rbac_role_resource
-- ----------------------------
INSERT INTO `rbac_role_resource` VALUES ('1', '1', '1', '2022-01-18 20:32:37', '2022-01-18 20:32:37');
INSERT INTO `rbac_role_resource` VALUES ('2', '2', '1', '2022-01-18 20:32:44', '2022-01-18 20:32:44');
INSERT INTO `rbac_role_resource` VALUES ('3', '3', '1', '2022-01-18 20:32:47', '2022-01-18 20:32:47');
INSERT INTO `rbac_role_resource` VALUES ('4', '3', '2', '2022-01-18 20:32:49', '2022-01-18 20:32:49');

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user`;
CREATE TABLE `rbac_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0.正常 1.禁用',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of rbac_user
-- ----------------------------
INSERT INTO `rbac_user` VALUES ('1', 'niexiaoqian', '聂小倩', '123456', '0', '13811112222', '1234@qq.com', '2022-01-18 20:28:04', '2022-01-18 20:28:38');
INSERT INTO `rbac_user` VALUES ('2', 'ningcaichen', '宁采臣', '123456', '0', '13811112222', '1234@qq.com', '2022-01-18 20:28:55', '2022-01-18 20:29:01');

-- ----------------------------
-- Table structure for rbac_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user_role`;
CREATE TABLE `rbac_user_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of rbac_user_role
-- ----------------------------
INSERT INTO `rbac_user_role` VALUES ('1', 'niexiaoqian', '1', '2022-01-18 20:31:06', '2022-01-18 20:31:06');
INSERT INTO `rbac_user_role` VALUES ('2', 'ningcaichen', '2', '2022-01-18 20:31:11', '2022-01-18 20:31:11');
SET FOREIGN_KEY_CHECKS=1;
