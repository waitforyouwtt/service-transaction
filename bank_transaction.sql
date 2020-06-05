/*
Navicat MySQL Data Transfer

Source Server         : bank_transaction
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bank_transaction

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-06-05 16:17:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '户主姓名',
  `account_no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `account_password` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '帐户密码',
  `account_balance` double DEFAULT NULL COMMENT '帐户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of account_info
-- ----------------------------
INSERT INTO `account_info` VALUES ('1', '凤凰小哥哥', '411421199308236039', '577521', '337000');
INSERT INTO `account_info` VALUES ('3', '伊羽', '411421199308236040', '577521', '151000');

-- ----------------------------
-- Table structure for account_pay
-- ----------------------------
DROP TABLE IF EXISTS `account_pay`;
CREATE TABLE `account_pay` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `account_no_source` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '转账账号',
  `account_no_receiver` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资金接收账号',
  `pay_amount` double DEFAULT NULL COMMENT '充值余额',
  `result` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '充值结果:success，fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of account_pay
-- ----------------------------
INSERT INTO `account_pay` VALUES ('0c7502d9-ad6b-46c1-9226-117cc181db5d', '411421199308236039', '411421199308236040', '3000', 'success');
INSERT INTO `account_pay` VALUES ('317cee2c-681f-4a7f-aedb-e3710c86f403', '411421199308236039', '411421199308236040', '3000', 'success');

-- ----------------------------
-- Table structure for bank1_account_pay
-- ----------------------------
DROP TABLE IF EXISTS `bank1_account_pay`;
CREATE TABLE `bank1_account_pay` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `account_no_source` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '转账账号',
  `account_no_receiver` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资金接收账号',
  `pay_amount` double DEFAULT NULL COMMENT '充值余额',
  `result` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '充值结果:success，fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bank1_account_pay
-- ----------------------------

-- ----------------------------
-- Table structure for bank1_duplication
-- ----------------------------
DROP TABLE IF EXISTS `bank1_duplication`;
CREATE TABLE `bank1_duplication` (
  `tx_no` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '事务号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bank1_duplication
-- ----------------------------
INSERT INTO `bank1_duplication` VALUES ('0a28f192-cb7d-468d-a5bc-b658dfd1ebe4', '2020-06-05 15:59:09');
INSERT INTO `bank1_duplication` VALUES ('0c7502d9-ad6b-46c1-9226-117cc181db5d', '2020-06-05 14:36:53');
INSERT INTO `bank1_duplication` VALUES ('317cee2c-681f-4a7f-aedb-e3710c86f403', '2020-06-05 14:33:21');

-- ----------------------------
-- Table structure for bank2_duplication
-- ----------------------------
DROP TABLE IF EXISTS `bank2_duplication`;
CREATE TABLE `bank2_duplication` (
  `tx_no` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '事务号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bank2_duplication
-- ----------------------------
INSERT INTO `bank2_duplication` VALUES ('0a28f192-cb7d-468d-a5bc-b658dfd1ebe4', '2020-06-05 15:59:09');
INSERT INTO `bank2_duplication` VALUES ('620be28a-4961-477e-8e74-3c6034e68bfc', '2020-06-05 15:44:38');

-- ----------------------------
-- Table structure for local_cancel_log
-- ----------------------------
DROP TABLE IF EXISTS `local_cancel_log`;
CREATE TABLE `local_cancel_log` (
  `tx_no` varchar(64) NOT NULL COMMENT '事务id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_cancel_log
-- ----------------------------

-- ----------------------------
-- Table structure for local_confirm_log
-- ----------------------------
DROP TABLE IF EXISTS `local_confirm_log`;
CREATE TABLE `local_confirm_log` (
  `tx_no` varchar(64) NOT NULL COMMENT '事务id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_confirm_log
-- ----------------------------

-- ----------------------------
-- Table structure for local_try_log
-- ----------------------------
DROP TABLE IF EXISTS `local_try_log`;
CREATE TABLE `local_try_log` (
  `tx_no` varchar(64) NOT NULL COMMENT '事务id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_try_log
-- ----------------------------
