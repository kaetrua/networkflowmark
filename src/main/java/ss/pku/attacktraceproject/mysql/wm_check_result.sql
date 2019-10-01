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
-- Table structure for wm_check_result
-- ----------------------------
DROP TABLE IF EXISTS `wm_check_result`;
CREATE TABLE `wm_check_result` (
`check_id` int(20) NOT NULL AUTO_INCREMENT,
  `original_wm_info` varchar(100) ,
   `check_time` varchar(100) ,
    `check_ip` varchar(100) ,
	 `check_result` varchar(100) ,
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
