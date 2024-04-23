/*
 Navicat Premium Data Transfer

 Source Server         : 华为云
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 121.36.42.101:3306
 Source Schema         : db_message

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 16/02/2023 17:09:08
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_secret
-- ----------------------------
DROP TABLE IF EXISTS `tb_secret`;
CREATE TABLE `tb_secret`
(
    `id`           int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `app_id`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'APPId',
    `secret_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_socket_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_socket_message`;
CREATE TABLE `tb_socket_message`
(
    `id`           int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `app_id`       varbinary(16) NOT NULL COMMENT 'app_id',
    `receiver_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收者唯一用户标识',
    `sender_key`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送者唯一标识',
    `create_at`    datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `message_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX          `appid_reciver`(`app_id`, `receiver_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
