/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : test2

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 17/05/2021 16:12:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `enabled` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (1, 'test1', NULL, '{bcrypt}$2a$10$9W3aMnHj0src9wIqHOk/PuzYgvCGkpc.1wsPr1AWWCCT/dgQNcw6C', 'all', 'authorization_code,client_credentials,password,implicit,refresh_token', 'https://baidu.com', NULL, 3600, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_type`;
CREATE TABLE `sys_dictionary_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deleted` int NULL DEFAULT NULL,
  `enabled` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dictionary_type
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_value`;
CREATE TABLE `sys_dictionary_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deleted` int NULL DEFAULT NULL,
  `enabled` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `type_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unique_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dictionary_value
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件唯一编号',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `save_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '1:本地 2:网络',
  `relative_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '相对路径',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '1:yes  0:no',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (1, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602094838.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (2, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602095034.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (3, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100028.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (4, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100400.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (5, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100707.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (6, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100831.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (7, NULL, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602102850.xlsx', 0, NULL);
INSERT INTO `sys_file` VALUES (8, NULL, '我是测试的啊', '1', '2020\\06\\02\\u=3623998424,2087716137&fm=26&gp=0_20200602103137.jpg', 0, NULL);

-- ----------------------------
-- Table structure for sys_file_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_relation`;
CREATE TABLE `sys_file_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `info_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_relation
-- ----------------------------
INSERT INTO `sys_file_relation` VALUES (1, '1', 'dXNlcl9oZWFkZXJfaW1hZ2U=', '1');
INSERT INTO `sys_file_relation` VALUES (2, '4', 'dXNlcl9oZWFkZXJfaW1hZ2U=', '2');
INSERT INTO `sys_file_relation` VALUES (3, '2', 'dXNlcl9oZWFkZXJfaW1hZ2U=', '3');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `error` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '编号',
  `parent_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父级编号',
  `sort` int NULL DEFAULT 0 COMMENT '排序，值越大，排序越靠前',
  `unique_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单唯一关键字',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面路由',
  `page` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件名称',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向url',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `right` int NULL DEFAULT NULL COMMENT '权限总值',
  `hidden` tinyint(1) NULL DEFAULT 0 COMMENT '0:否 1:是',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '0:否 1:是',
  `enabled` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_authority`;
CREATE TABLE `sys_menu_authority`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `no` int NULL DEFAULT NULL,
  `menu_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unique_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT 1,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_authority
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `no` int NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unique_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_right`;
CREATE TABLE `sys_role_right`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_no` int NULL DEFAULT NULL,
  `right` int NULL DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_right
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `work_no` int NULL DEFAULT NULL,
  `header_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '1:man 2:woman',
  `is_admin` tinyint(1) NULL DEFAULT 0 COMMENT '1:yes 0:no',
  `create_time` datetime NULL DEFAULT NULL,
  `create_user` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user` bigint NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '1:enable 0:disable',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '1:yes 0:no',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `no` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$9W3aMnHj0src9wIqHOk/PuzYgvCGkpc.1wsPr1AWWCCT/dgQNcw6C', '5J9189', '超级管理员', NULL, 1, NULL, 1, 1, '2020-05-25 17:30:20', 1, '2020-05-25 17:30:29', 1, 1, 0, NULL, 'ADMIN', NULL);
INSERT INTO `sys_user` VALUES (2, 'fae', '{bcrypt}$2a$10$swWamFiwSQ8Vsf8q/kY2fel3kv8b4A9HitlblnKzUdl91Mc1fGNpG', NULL, 'fae录入', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, 'FAE', NULL);
INSERT INTO `sys_user` VALUES (3, 'confirm', '{bcrypt}$2a$10$dnvN3NTYPPq2JZTQt9Sjge6AwJZj.fxq/pWy/YoJQZAK4P.uLFvZy', NULL, '确认人员', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, 'CONFIRM', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_no` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sysi18n
-- ----------------------------
DROP TABLE IF EXISTS `sysi18n`;
CREATE TABLE `sysi18n`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `info_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `info_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sysi18n
-- ----------------------------

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` smallint NULL DEFAULT NULL,
  `money` double(255, 2) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, 'test11', 12, 23443.00, '2020-05-22 11:52:12');
INSERT INTO `test` VALUES (2, 'test22', 25, 2000000.00, '2020-05-22 11:52:15');
INSERT INTO `test` VALUES (3, 'test33', 23, 3222222.00, '2020-05-22 11:54:38');
INSERT INTO `test` VALUES (4, 'test44', 30, 60000000.00, '2020-05-22 15:43:00');
INSERT INTO `test` VALUES (5, 'test55', 30, 70000000.00, '2020-05-22 16:43:00');
INSERT INTO `test` VALUES (6, 'test66', 30, 90000000.00, '2020-05-22 16:11:00');
INSERT INTO `test` VALUES (7, 'test77', 35, 33222233.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (8, 'test88', 35, 232000001.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (9, 'test99', 35, 458900001.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (10, 'test100', 35, 123400000.55, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (11, 'test111', 35, 787000000.55, '2020-05-24 13:11:00');

SET FOREIGN_KEY_CHECKS = 1;
