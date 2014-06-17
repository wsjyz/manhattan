/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : manhattan

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2014-06-12 07:21:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mht_ appointment
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_ appointment`;
CREATE TABLE `t_mht_ appointment` (
  `appointment_id` varchar(32) NOT NULL,
  `action_id` varchar(32) DEFAULT NULL,
  `course_category` varchar(45) DEFAULT NULL COMMENT '学习科目',
  `tutoring_way` varchar(45) DEFAULT NULL,
  `other_content` varchar(200) DEFAULT NULL COMMENT '补充说明',
  `mobile` varchar(32) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `QQ` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约信息表';

-- ----------------------------
-- Table structure for t_mht_course
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_course`;
CREATE TABLE `t_mht_course` (
  `course_id` varchar(32) NOT NULL,
  `course_title` varchar(45) DEFAULT NULL COMMENT '标题',
  `course_subtitle` varchar(200) DEFAULT NULL COMMENT '副标题',
  `course_pic` varchar(100) DEFAULT NULL COMMENT '课程图片',
  `class_no` varchar(100) DEFAULT NULL COMMENT '班号',
  `start_time` datetime DEFAULT NULL COMMENT '开课时间',
  `end_time` datetime DEFAULT NULL COMMENT '结课时间',
  `period` decimal(10,3) DEFAULT NULL COMMENT '课时',
  `expense` decimal(10,3) DEFAULT NULL COMMENT '费用',
  `place` varchar(100) DEFAULT NULL COMMENT '开课地点',
  `course_intro` text COMMENT '课程简介',
  `appointment_phone` varchar(45) DEFAULT NULL COMMENT '预约电话',
  `teachers` varchar(200) DEFAULT NULL COMMENT '授课教师',
  `course_category` varchar(45) DEFAULT NULL COMMENT '课程分类',
  `post_teacher` varchar(32) DEFAULT NULL COMMENT '发布教师',
  `video_url` varchar(100) DEFAULT NULL COMMENT '视频链接',
  `teaching_time` varchar(100) DEFAULT NULL COMMENT '授课时间',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Table structure for t_mht_homework_submit
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_homework_submit`;
CREATE TABLE `t_mht_homework_submit` (
  `homework_submit_id` varchar(32) NOT NULL,
  `homework_id` varchar(45) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL COMMENT '提交人',
  `submit_file` varchar(45) DEFAULT NULL COMMENT '作业文件',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`homework_submit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业提交表';

-- ----------------------------
-- Table structure for t_mht_information
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_information`;
CREATE TABLE `t_mht_information` (
  `information_id` varchar(32) NOT NULL DEFAULT '',
  `content` varchar(1000) DEFAULT NULL,
  `post_time` datetime DEFAULT NULL COMMENT '发布时间',
  `status` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `informationid` varchar(255) NOT NULL,
  PRIMARY KEY (`information_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资讯表';

-- ----------------------------
-- Table structure for t_mht_place
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_place`;
CREATE TABLE `t_mht_place` (
  `place_id` varchar(32) NOT NULL,
  `place_name` varchar(45) DEFAULT NULL,
  `place_address` varchar(45) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `course_category` varchar(45) DEFAULT NULL,
  `fees` int(11) DEFAULT NULL COMMENT '收费标准',
  `map_url` varchar(100) DEFAULT NULL COMMENT '所示图片地址',
  PRIMARY KEY (`place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='场地信息表';

-- ----------------------------
-- Table structure for t_mht_question
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_question`;
CREATE TABLE `t_mht_question` (
  `question_id` varchar(32) NOT NULL,
  `question_title` varchar(100) DEFAULT NULL COMMENT '问题标题',
  `question_content` text COMMENT '问题内容',
  `question_pic` varchar(200) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL COMMENT '提问人',
  `reply_user` varchar(32) DEFAULT NULL COMMENT '回答人',
  `assign_teacher` varchar(45) DEFAULT NULL COMMENT '指定回答教师',
  `answer` text COMMENT '问题答案',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `answer_time` datetime DEFAULT NULL COMMENT '回答时间',
  `status` varchar(20) NOT NULL,
  `answer_pic` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问答表';

-- ----------------------------
-- Table structure for t_mht_statistics
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_statistics`;
CREATE TABLE `t_mht_statistics` (
  `statistics_id` varchar(32) NOT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT '关注；报名；评论；收藏',
  `object_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`statistics_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息统计表';

-- ----------------------------
-- Table structure for t_mht_teacher_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_teacher_detail`;
CREATE TABLE `t_mht_teacher_detail` (
  `user_id` varchar(32) NOT NULL,
  `course_category` varchar(32) DEFAULT NULL COMMENT '教授科目',
  `authentication` varchar(45) DEFAULT NULL COMMENT '身份认证',
  `credit_rate` varchar(20) DEFAULT NULL COMMENT '信用等级',
  `wechat` varchar(45) DEFAULT NULL COMMENT '微信号',
  `self_introduction` text COMMENT '自我介绍',
  `video_url` varchar(45) DEFAULT NULL COMMENT '视频展示',
  `appointment_phone` varchar(20) DEFAULT NULL COMMENT '预约电话',
  `expert_score` varchar(45) DEFAULT NULL COMMENT '专家评分',
  `expert_comments` varchar(200) DEFAULT NULL COMMENT '专家点评',
  `teaching_area` varchar(45) DEFAULT NULL COMMENT '授课区域',
  `tutoring_way` varchar(45) DEFAULT NULL COMMENT '辅导方式',
  `class_fees` int(11) DEFAULT NULL COMMENT '课时费',
  `teaching_time` varchar(45) DEFAULT NULL COMMENT '可授课时间；MON1/MON2/MON3/TUE1..',
  `final_graduate_school` varchar(45) DEFAULT NULL COMMENT '最高毕业院校',
  `specialty` varchar(45) DEFAULT NULL COMMENT '专业',
  `education_certificate` varchar(45) DEFAULT NULL COMMENT '学历证明',
  `experience_exam` varchar(45) DEFAULT NULL,
  `exam_score` varchar(20) DEFAULT NULL,
  `exam_certificate` varchar(45) DEFAULT NULL COMMENT '考试证明',
  `teaching_experience` varchar(45) DEFAULT NULL,
  `teaching_certificate` varchar(45) DEFAULT NULL COMMENT '教学证明',
  `student_max_score` varchar(45) DEFAULT NULL COMMENT '学生最高分成绩',
  `authentication_status` varchar(45) DEFAULT NULL COMMENT '认证状态',
  `student_max_score_certificate` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师信息表';

-- ----------------------------
-- Table structure for t_mht_teacher_homework
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_teacher_homework`;
CREATE TABLE `t_mht_teacher_homework` (
  `homework_id` varchar(45) NOT NULL,
  `teacher_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `post_time` varchar(45) DEFAULT NULL,
  `homework_file` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`homework_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师发布作业';

-- ----------------------------
-- Table structure for t_mht_user
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_user`;
CREATE TABLE `t_mht_user` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号（登录名）',
  `password` varchar(32) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL COMMENT '所在地区',
  `credit` int(11) DEFAULT NULL COMMENT '积分',
  `vip_end_time` datetime DEFAULT NULL COMMENT 'vip结束时间',
  `wallet` int(11) DEFAULT NULL COMMENT '钱包余额',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `status` varchar(20) DEFAULT NULL COMMENT '账号状态',
  `sex` varchar(10) DEFAULT NULL,
  `evaluation` varchar(200) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `auth_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `mobile_UNIQUE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mht_user_action
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_user_action`;
CREATE TABLE `t_mht_user_action` (
  `action_id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `action_type` varchar(20) DEFAULT NULL COMMENT 'Listen；collect',
  `action_time` datetime DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL COMMENT '收藏、试听的教师',
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户动作表(试听、收藏)';

-- ----------------------------
-- Table structure for t_mht_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_mht_wallet`;
CREATE TABLE `t_mht_wallet` (
  `wallet_id` varchar(32) NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `opt_time` datetime DEFAULT NULL COMMENT '操作时间',
  `money` int(11) DEFAULT NULL COMMENT '充值金额',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '充值状态',
  PRIMARY KEY (`wallet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值信息表';
