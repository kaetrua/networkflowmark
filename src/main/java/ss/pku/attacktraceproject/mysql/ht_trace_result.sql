/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql连接
 Source Server Type    : MySQL
 Source Host           : localhost:3306

 Wrote by			   : bingoer
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ht_trace_result
-- ----------------------------
DROP TABLE IF EXISTS `ht_trace_result`;
CREATE TABLE `ht_trace_result` (
  `token_id` varchar(64) ,
   `ip` varchar(100) ,
    `os` varchar(100) ,
	 `other` varchar(256) ,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
