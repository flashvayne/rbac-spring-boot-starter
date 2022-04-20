-- ----------------------------
-- Table structure for rbac_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_resource`;
CREATE TABLE `rbac_resource`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '资源id',
    `type`        tinyint      NOT NULL COMMENT '类型 1.接口 2.菜单',
    `name`        varchar(100) DEFAULT NULL COMMENT '资源名称',
    `url`         varchar(255) NOT NULL COMMENT '资源标识/地址',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Records of rbac_resource
-- ----------------------------
INSERT INTO `rbac_resource`
VALUES ('1', '1', '学生列表', '/student/list', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_resource`
VALUES ('2', '1', '教师列表', '/teacher/list', '2022-01-23 20:31:35', '2022-01-23 20:31:35');

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role`
(
    `id`          int NOT NULL COMMENT '角色id',
    `name`        varchar(20)  DEFAULT NULL COMMENT '角色名',
    `desc`        varchar(100) DEFAULT NULL COMMENT '描述',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Records of rbac_role
-- ----------------------------
INSERT INTO `rbac_role`
VALUES ('1', '班主任', '可管理学生信息', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_role`
VALUES ('2', '教务主任', '可管理教师和学生信息', '2022-01-23 20:31:35', '2022-01-23 20:31:35');

-- ----------------------------
-- Table structure for rbac_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_resource`;
CREATE TABLE `rbac_role_resource`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `role_id`     int NOT NULL COMMENT '角色id',
    `resource_id` int NOT NULL COMMENT '资源id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_resource` (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Records of rbac_role_resource
-- ----------------------------
INSERT INTO `rbac_role_resource`
VALUES ('1', '1', '1', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_role_resource`
VALUES ('2', '2', '1', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_role_resource`
VALUES ('3', '2', '2', '2022-01-23 20:31:35', '2022-01-23 20:31:35');

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user`;
CREATE TABLE `rbac_user`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `user_id`     varchar(50) NOT NULL COMMENT '用户id',
    `name`        varchar(100)         DEFAULT NULL COMMENT '用户名',
    `password`    varchar(255)         DEFAULT NULL COMMENT '密码',
    `status`      tinyint     NOT NULL DEFAULT '0' COMMENT '状态 0.正常 1.禁用',
    `mobile`      varchar(20)          DEFAULT NULL COMMENT '手机号',
    `email`       varchar(100)         DEFAULT NULL COMMENT '邮箱',
    `create_time` datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Records of rbac_user
-- ----------------------------
INSERT INTO `rbac_user`
VALUES ('1', 'bzr', '班主任', '123456', '0', '13811112222', '1234@qq.com', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_user`
VALUES ('2', 'jwzr', '教务主任', '123456', '0', '13811112222', '1234@qq.com', '2022-01-23 20:31:35', '2022-01-23 20:31:35');

-- ----------------------------
-- Table structure for rbac_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user_role`;
CREATE TABLE `rbac_user_role`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `user_id`     varchar(50) NOT NULL COMMENT '用户id',
    `role_id`     int         NOT NULL COMMENT '角色id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Records of rbac_user_role
-- ----------------------------
INSERT INTO `rbac_user_role`
VALUES ('1', 'bzr', '1', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
INSERT INTO `rbac_user_role`
VALUES ('2', 'jwzr', '2', '2022-01-23 20:31:35', '2022-01-23 20:31:35');
