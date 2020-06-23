/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 22/06/2020 15:18:35
*/


create database test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

use test;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (1, 'test1', NULL, '{bcrypt}$2a$10$9W3aMnHj0src9wIqHOk/PuzYgvCGkpc.1wsPr1AWWCCT/dgQNcw6C', 'all', 'authorization_code,client_credentials,password,implicit,refresh_token', 'https://baidu.com', NULL, 3600, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `save_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '1:本地 2:网络',
  `relative_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '相对路径',
  `deleted` tinyint(255) NULL DEFAULT 0 COMMENT '1:yes  0:no',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (1, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602094838.xlsx', 0);
INSERT INTO `sys_file` VALUES (2, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602095034.xlsx', 0);
INSERT INTO `sys_file` VALUES (3, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100028.xlsx', 0);
INSERT INTO `sys_file` VALUES (4, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100400.xlsx', 0);
INSERT INTO `sys_file` VALUES (5, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100707.xlsx', 0);
INSERT INTO `sys_file` VALUES (6, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602100831.xlsx', 0);
INSERT INTO `sys_file` VALUES (7, '我是测试的啊', '1', '2020\\06\\02\\ITS3_TestRawData_2020W14_20200602102850.xlsx', 0);
INSERT INTO `sys_file` VALUES (8, '我是测试的啊', '1', '2020\\06\\02\\u=3623998424,2087716137&fm=26&gp=0_20200602103137.jpg', 0);

-- ----------------------------
-- Table structure for sys_file_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_relation`;
CREATE TABLE `sys_file_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_id` bigint(20) NULL DEFAULT NULL,
  `file_module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `info_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `work_no` int(11) NULL DEFAULT NULL,
  `header_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` tinyint(255) NULL DEFAULT NULL COMMENT '1:man 2:woman',
  `is_admin` tinyint(255) NULL DEFAULT 0 COMMENT '1:yes 0:no',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` bigint(255) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `enabled` tinyint(255) NULL DEFAULT 1 COMMENT '1:enable 0:disable',
  `deleted` tinyint(255) NULL DEFAULT 0 COMMENT '1:yes 0:no',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$9W3aMnHj0src9wIqHOk/PuzYgvCGkpc.1wsPr1AWWCCT/dgQNcw6C', '5J9189', '超级管理员', NULL, 1, NULL, 1, 1, '2020-05-25 17:30:20', 1, '2020-05-25 17:30:29', 1, 1, 0, NULL, 'ADMIN');
INSERT INTO `sys_user` VALUES (2, 'fae', '{bcrypt}$2a$10$swWamFiwSQ8Vsf8q/kY2fel3kv8b4A9HitlblnKzUdl91Mc1fGNpG', NULL, 'fae录入', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, 'FAE');
INSERT INTO `sys_user` VALUES (3, 'confirm', '{bcrypt}$2a$10$dnvN3NTYPPq2JZTQt9Sjge6AwJZj.fxq/pWy/YoJQZAK4P.uLFvZy', NULL, '确认人员', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, 'CONFIRM');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` smallint(6) NULL DEFAULT NULL,
  `money` double(255, 2) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, 'test1', 12, 244.00, '2020-05-22 11:52:12');
INSERT INTO `test` VALUES (2, 'test2', 25, 1000000.00, '2020-05-22 11:52:15');
INSERT INTO `test` VALUES (3, 'test3', 23, 1222222.00, '2020-05-22 11:54:38');
INSERT INTO `test` VALUES (4, 'test4', 30, 30000000.00, '2020-05-22 15:43:00');
INSERT INTO `test` VALUES (5, 'test5', 30, 30000000.00, '2020-05-22 16:43:00');
INSERT INTO `test` VALUES (6, 'test6', 30, 30000000.00, '2020-05-22 16:11:00');
INSERT INTO `test` VALUES (7, 'test7', 35, 22332233.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (8, 'test8', 35, 100000001.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (9, 'test9', 35, 100000001.00, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (10, 'test10', 35, 100000000.55, '2020-05-24 13:11:00');
INSERT INTO `test` VALUES (11, 'test11', 35, 100000000.55, '2020-05-24 13:11:00');

SET FOREIGN_KEY_CHECKS = 1;
