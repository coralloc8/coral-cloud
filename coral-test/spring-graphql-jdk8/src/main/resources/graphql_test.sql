/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : graphql_test

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 02/01/2024 17:44:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for author
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者编码',
  `author_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者名称',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of author
-- ----------------------------
INSERT INTO `author` VALUES (1, '1001', '鱼人二代', 'MALE', '1984-01-23', '2024-01-02 17:35:02');
INSERT INTO `author` VALUES (2, '1002', '辰东', 'MALE', '1987-12-15', '2024-01-02 17:35:22');
INSERT INTO `author` VALUES (3, '1003', '耳根', 'MALE', '1980-08-03', '2024-01-02 17:35:40');
INSERT INTO `author` VALUES (4, '1004', '猫腻', 'FEMALE', '1979-09-20', '2024-01-02 17:36:08');
INSERT INTO `author` VALUES (5, '1005', '流浪猫', 'FEMALE', '1985-01-03', '2024-01-02 17:36:32');
INSERT INTO `author` VALUES (6, '1006', '渐羽', 'FEMALE', '2000-06-01', '2024-01-02 17:37:39');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '书籍名称',
  `author_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者编码',
  `author_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `chapter` int NULL DEFAULT NULL COMMENT '章节',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '书籍信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '校花的贴身高手', '1001', '鱼人二代', 200.00, 15000, '2024-01-02 17:38:10');
INSERT INTO `book` VALUES (2, '很纯很暧昧', '1001', '鱼人二代', 36.00, 3000, '2024-01-02 17:38:31');
INSERT INTO `book` VALUES (3, '神墓', '1002', '辰东', 60.00, 860, '2024-01-02 17:38:54');
INSERT INTO `book` VALUES (4, '遮天', '1002', '辰东', 90.00, 2000, '2024-01-02 17:39:21');
INSERT INTO `book` VALUES (5, '完美世界', '1002', '辰东', 80.00, 2500, '2024-01-02 17:39:37');
INSERT INTO `book` VALUES (6, '长生界', '1002', '辰东', 75.00, 1500, '2024-01-02 17:40:08');
INSERT INTO `book` VALUES (7, '圣墟', '1002', '辰东', 65.00, 3000, '2024-01-02 17:40:29');
INSERT INTO `book` VALUES (8, '一念永恒', '1003', '耳根', 45.00, 1200, '2024-01-02 17:41:09');
INSERT INTO `book` VALUES (9, '耳根002', '1003', '耳根', 30.00, 750, '2024-01-02 17:41:34');
INSERT INTO `book` VALUES (10, '耳根003', '1003', '耳根', 25.00, 400, '2024-01-02 17:41:46');
INSERT INTO `book` VALUES (11, '耳根004', '1003', '耳根', 100.00, 7500, '2024-01-02 17:41:58');
INSERT INTO `book` VALUES (12, '猫腻001', '1004', '猫腻', 90.00, 6500, '2024-01-02 17:42:20');
INSERT INTO `book` VALUES (13, '猫腻002', '1004', '猫腻', 66.00, 6600, '2024-01-02 17:42:42');
INSERT INTO `book` VALUES (14, '流浪猫001', '1005', '流浪猫', 55.00, 5500, '2024-01-02 17:43:01');
INSERT INTO `book` VALUES (15, '流浪猫005', '1005', '流浪猫', 120.00, 9000, '2024-01-02 17:43:24');
INSERT INTO `book` VALUES (16, '流浪猫100', '1005', '流浪猫', 36.00, 3600, '2024-01-02 17:43:38');
INSERT INTO `book` VALUES (17, '高渐离自传', '1006', '渐羽', 300.00, 800, '2024-01-02 17:44:10');
INSERT INTO `book` VALUES (18, '安琪拉打野日记', '1006', '渐羽', 30.00, 120, '2024-01-02 17:44:35');

SET FOREIGN_KEY_CHECKS = 1;
