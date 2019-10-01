/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql连接
 Source Server Type    : MySQL
 Source Host           : localhost:3306

 Wrote by			   : bingoer
 quintuple_info 字段是按照五元组中的(源ip、源端口、目的ip、目的端口)四个字段拼接起来的唯一关键字，作为主键
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wm_netflow_quintuple
-- ----------------------------
DROP TABLE IF EXISTS `wm_check_received_tcp_packet`;
CREATE TABLE `wm_check_received_tcp_packet` (
 `id` int(20) NOT NULL AUTO_INCREMENT,
  `quintuple_info` varchar(256)  ,
   `received_time` varchar(256) ,
    `tcp_sequence` varchar(256) ,
	 `tcp_ack_num` varchar(256) ,
	 `tcp_urg` varchar(256) ,
	 `tcp_ack` varchar(256) ,
	 `tcp_psh` varchar(256) ,
	 `tcp_rst` varchar(256) ,
	 `tcp_syn` varchar(256) ,
	 `tcp_fin` varchar(256) ,
	 `tcp_rsv1` varchar(256) ,
	 `tcp_rsv2` varchar(256) ,
	 `tcp_window` varchar(256) ,
	 `tcp_urgent_pointer` varchar(256) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
