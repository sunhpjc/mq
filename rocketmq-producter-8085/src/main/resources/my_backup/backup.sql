/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : springboot

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 06/05/2021 14:15:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int(10) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_teacher` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_number` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id` int(32) NOT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  INDEX `index_name`(`course_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for send_sms
-- ----------------------------
DROP TABLE IF EXISTS `send_sms`;
CREATE TABLE `send_sms`  (
  `id` bigint(18) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '短信序列号',
  `batch_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `app_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `template_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  `target_phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标电话',
  `sms_status` tinyint(1) NOT NULL COMMENT '短信状态 0表示未发送，1表示已发送，2表示发送失败',
  `sms_content` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容',
  `arrive_status` tinyint(1) NULL DEFAULT NULL COMMENT '短信送达状态 0：失败，1：成功',
  `sms_mark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信标识位',
  `channel_mark` tinyint(1) NULL DEFAULT NULL COMMENT '通道限定位 1：限定 0：不限定',
  `again_mark` tinyint(1) NULL DEFAULT NULL COMMENT '重发标志位 1：重发 0：不重发',
  `again_count` int(10) NULL DEFAULT NULL COMMENT '重发次数 指定次数，不包含第一次',
  `back_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：未拉取回执 1：已拉取回执',
  `custom_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户自定义id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sms_status`(`sms_status`) USING BTREE COMMENT '短信状态索引',
  INDEX `idx_app_code_batch_no`(`app_code`, `batch_no`) USING BTREE COMMENT '应用批次号',
  INDEX `idx_phone`(`target_phone`) USING BTREE COMMENT '电话号码'
) ENGINE = InnoDB AUTO_INCREMENT = 20001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '发送短信表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms`  (
  `id` bigint(15) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `batchNo` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '批次号20位',
  `phone` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '电话号码',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `status` tinyint(1) NOT NULL COMMENT '0：待发送，1：发送成功，2：发送中，3：发送失败',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_statistics
-- ----------------------------
DROP TABLE IF EXISTS `sms_statistics`;
CREATE TABLE `sms_statistics`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `time_flag` datetime(0) NOT NULL COMMENT '精确到小时 如 2021-04-20 03:00:00',
  `sms_total` bigint(20) NOT NULL COMMENT '当前小时发送短信总数',
  `sms_success` bigint(20) NOT NULL COMMENT '当前小时发送短信成功数',
  `previous_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一条短息的id',
  `status` tinyint(1) NOT NULL COMMENT '0无效，1有效',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '创建人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 715 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `course_id` int(10) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_teacher` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_number` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passWord` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `realName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user2
-- ----------------------------
DROP TABLE IF EXISTS `user2`;
CREATE TABLE `user2`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
