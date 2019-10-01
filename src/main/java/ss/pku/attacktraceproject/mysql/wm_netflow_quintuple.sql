/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql连接
 Source Server Type    : MySQL
 Source Host           : localhost:3306

 Wrote by			   : bingoer
 
 水印信息和五元组信息，五元组用来标识一个网络流，水印信息用来嵌入网络流中
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wm_netflow_quintuple
-- ----------------------------
DROP TABLE IF EXISTS `wm_netflow_quintuple`;
CREATE TABLE `wm_netflow_quintuple` (
  `original_wm_info` varchar(256) ,
   `src_ip` varchar(100) ,
    `src_port` varchar(100) ,
	 `dst_ip` varchar(100) ,
	 `dst_port` varchar(100) ,
  PRIMARY KEY (`original_wm_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
