/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : leave_attend_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-02-24 20:28:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance`;
CREATE TABLE `t_attendance` (
  `attendanceId` int(11) NOT NULL auto_increment COMMENT '出勤id',
  `attendanceDate` varchar(20) default NULL COMMENT '出勤日期',
  `employeeObj` varchar(30) NOT NULL COMMENT '考勤员工',
  `attendanceState` int(11) NOT NULL COMMENT '出勤结果',
  `attendanceMemo` varchar(500) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`attendanceId`),
  KEY `employeeObj` (`employeeObj`),
  KEY `attendanceState` (`attendanceState`),
  CONSTRAINT `t_attendance_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `t_attendance_ibfk_2` FOREIGN KEY (`attendanceState`) REFERENCES `t_attendancestate` (`attendanceStateId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendance
-- ----------------------------
INSERT INTO `t_attendance` VALUES ('1', '2018-01-13', 'EM001', '2', '迟到了5分钟');
INSERT INTO `t_attendance` VALUES ('2', '2018-02-23', 'EM002', '1', '好员工');
INSERT INTO `t_attendance` VALUES ('3', '2018-02-20', 'EM002', '2', '迟到了3分钟哦');

-- ----------------------------
-- Table structure for `t_attendancestate`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendancestate`;
CREATE TABLE `t_attendancestate` (
  `attendanceStateId` int(11) NOT NULL auto_increment COMMENT '出勤状态id',
  `attendanceStateName` varchar(20) NOT NULL COMMENT '出勤状态名称',
  PRIMARY KEY  (`attendanceStateId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendancestate
-- ----------------------------
INSERT INTO `t_attendancestate` VALUES ('1', '已到');
INSERT INTO `t_attendancestate` VALUES ('2', '迟到');
INSERT INTO `t_attendancestate` VALUES ('3', '早退');
INSERT INTO `t_attendancestate` VALUES ('4', '旷工');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL COMMENT 'departmentNo',
  `departmentName` varchar(20) NOT NULL COMMENT '部门名称',
  `departmentDesc` varchar(800) NOT NULL COMMENT '部门描述',
  PRIMARY KEY  (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '人事部', '管理公司人事');
INSERT INTO `t_department` VALUES ('BM002', '财务部', '管理公司财务');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(30) NOT NULL COMMENT 'employeeNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `departmentObj` varchar(20) NOT NULL COMMENT '所在部门',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `employeePhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `basicWage` float NOT NULL COMMENT '基本工资',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  PRIMARY KEY  (`employeeNo`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_employee_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '123', 'BM001', '双鱼林', '男', '2018-01-04', 'upload/d028f610-e761-4569-9ffa-edc1e4d41708.jpg', '4000', '13984908342', '成都红星路13号');
INSERT INTO `t_employee` VALUES ('EM002', '123', 'BM001', '李明芬', '女', '2018-02-01', 'upload/c8e033ac-e682-4b99-961b-e6913b804773.jpg', '3500', '13984990843', '四川南充人民南路');

-- ----------------------------
-- Table structure for `t_leaveinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveinfo`;
CREATE TABLE `t_leaveinfo` (
  `leaveId` int(11) NOT NULL auto_increment COMMENT '请假id',
  `leaveTypeObj` int(11) NOT NULL COMMENT '请假类型',
  `leaveTitle` varchar(30) NOT NULL COMMENT '请假标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '请假内容',
  `days` varchar(20) NOT NULL COMMENT '请假时长',
  `employeeObj` varchar(30) NOT NULL COMMENT '请假员工',
  `addTime` varchar(20) default NULL COMMENT '提交时间',
  `shenHeState` varchar(20) NOT NULL COMMENT '审核状态',
  `replyContent` varchar(20) default NULL COMMENT '负责人回复',
  PRIMARY KEY  (`leaveId`),
  KEY `leaveTypeObj` (`leaveTypeObj`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_leaveinfo_ibfk_1` FOREIGN KEY (`leaveTypeObj`) REFERENCES `t_leavetype` (`leaveTypeId`),
  CONSTRAINT `t_leaveinfo_ibfk_2` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveinfo
-- ----------------------------
INSERT INTO `t_leaveinfo` VALUES ('1', '1', '感冒了生病了', '这2天太冷了，得了重感冒！', '3天', 'EM001', '2018-01-13 22:03:54', '审核通过', '准予请假');
INSERT INTO `t_leaveinfo` VALUES ('2', '2', '奶奶去世了回去3天', '奶奶因病去世了，我得回去参加葬礼！', '3天', 'EM002', '2018-02-24 18:25:22', '审核通过', '准予请假');

-- ----------------------------
-- Table structure for `t_leavetype`
-- ----------------------------
DROP TABLE IF EXISTS `t_leavetype`;
CREATE TABLE `t_leavetype` (
  `leaveTypeId` int(11) NOT NULL auto_increment COMMENT '请假类型id',
  `leaveTypeName` varchar(20) NOT NULL COMMENT '请假类型名称',
  PRIMARY KEY  (`leaveTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leavetype
-- ----------------------------
INSERT INTO `t_leavetype` VALUES ('1', '病假');
INSERT INTO `t_leavetype` VALUES ('2', '丧假');
INSERT INTO `t_leavetype` VALUES ('3', '事假');
INSERT INTO `t_leavetype` VALUES ('4', '产假');

-- ----------------------------
-- Table structure for `t_manager`
-- ----------------------------
DROP TABLE IF EXISTS `t_manager`;
CREATE TABLE `t_manager` (
  `managerUserName` varchar(20) NOT NULL COMMENT 'managerUserName',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `departmentObj` varchar(20) NOT NULL COMMENT '所属部门',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `bornDate` varchar(20) default NULL COMMENT '出生日期',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  PRIMARY KEY  (`managerUserName`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_manager_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manager
-- ----------------------------
INSERT INTO `t_manager` VALUES ('cwb', '123', 'BM002', '王旭明', '男', '2018-02-07', '13959342344');
INSERT INTO `t_manager` VALUES ('rsb', '123', 'BM001', '李明', '男', '2018-01-03', '13598430843');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '1111', '<p>22222aaa</p>', '2018-01-13 22:05:27');

-- ----------------------------
-- Table structure for `t_salary`
-- ----------------------------
DROP TABLE IF EXISTS `t_salary`;
CREATE TABLE `t_salary` (
  `salaryId` int(11) NOT NULL auto_increment COMMENT '工资id',
  `employeeObj` varchar(30) NOT NULL COMMENT '员工',
  `salaryYear` varchar(20) NOT NULL COMMENT '工资年份',
  `salaryMonth` varchar(20) NOT NULL COMMENT '工资月份',
  `basicWage` float NOT NULL COMMENT '基本工资',
  `atttendanceReduce` float NOT NULL COMMENT '考勤扣除',
  `realSalary` float NOT NULL COMMENT '实得工资',
  PRIMARY KEY  (`salaryId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_salary_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_salary
-- ----------------------------
INSERT INTO `t_salary` VALUES ('1', 'EM001', '2017', '12', '4000', '100', '3900');
INSERT INTO `t_salary` VALUES ('2', 'EM002', '2018', '1', '3500', '50', '3450');
