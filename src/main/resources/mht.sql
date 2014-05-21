SET @ OLD_UNIQUE_CHECKS = @ @ UNIQUE_CHECKS , UNIQUE_CHECKS = 0 ; SET @ OLD_FOREIGN_KEY_CHECKS = @ @ FOREIGN_KEY_CHECKS , FOREIGN_KEY_CHECKS = 0 ; SET @ OLD_SQL_MODE = @ @ SQL_MODE , SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES' ; CREATE SCHEMA IF NOT EXISTS `manhattan` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ; USE `manhattan` ;

-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_user` ( `user_id` VARCHAR ( 32 ) NOT NULL , `user_name` VARCHAR ( 50 ) NULL COMMENT  '姓名' , `mobile` VARCHAR ( 20 ) NOT NULL COMMENT  '手机号（登录名）' , `password` VARCHAR ( 20 ) NULL , `type` VARCHAR ( 20 ) NULL COMMENT  'teacher;student;vip_student;' , `sex` VARCHAR ( 10 ) COMMENT  '性别' , `avatar` VARCHAR ( 100 ) COMMENT  '头像' , `email` VARCHAR ( 50 ) NULL , `address` VARCHAR ( 100 ) NULL , `area` VARCHAR ( 45 ) NULL COMMENT  '所在地区' , `credit` INT NULL COMMENT  '积分' , `evaluation` VARCHAR ( 200 ) COMMENT  '评价' , `vip_end_time` DATETIME NULL COMMENT  'vip结束时间' , `wallet` INT NULL COMMENT  '钱包余额' , `last_login_time` DATETIME NULL COMMENT  '上次登录时间' , `status` VARCHAR ( 20 ) NULL COMMENT  '账号状态' , `auth_code` VARCHAR ( 20 ) NULL COMMENT  '验证码' , PRIMARY KEY ( `user_id` ) , UNIQUE INDEX `mobile_UNIQUE` ( `mobile` ASC ) ) ENGINE = InnoDB ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_news`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_news` ( `news_id` VARCHAR ( 32 ) NOT NULL , `content` VARCHAR ( 1000 ) NULL , `post_time` DATETIME NULL COMMENT  '发布时间' , `status` VARCHAR ( 20 ) NULL , PRIMARY KEY ( `news_id` ) ) ENGINE = InnoDB COMMENT =  '资讯表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_course` ( `course_id` VARCHAR ( 32 ) NOT NULL , `course_name` VARCHAR ( 45 ) NULL , `start_time` DATETIME NULL COMMENT  '开课时间' , `end_time` DATETIME NULL COMMENT  '结课时间' , `period` INT NULL COMMENT  '课时' , `expense` INT NULL COMMENT  '费用' , `place` VARCHAR ( 100 ) NULL COMMENT  '开课地点' , `course_intro` TEXT NULL COMMENT  '课程简介' , `appointment_phone` VARCHAR ( 45 ) NULL COMMENT  '预约电话' , `teachers` VARCHAR ( 200 ) NULL COMMENT  '授课教师' , `course_category` VARCHAR ( 45 ) NULL COMMENT  '课程分类' , PRIMARY KEY ( `course_id` ) ) ENGINE = InnoDB COMMENT =  '课程表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_teacher_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_teacher_detail` ( `user_id` VARCHAR ( 32 ) NOT NULL , `course_category` VARCHAR ( 32 ) NULL COMMENT  '教授科目' , `authentication` VARCHAR ( 45 ) NULL COMMENT  '身份认证' , `credit_rate` VARCHAR ( 20 ) NULL COMMENT  '信用等级' , `wechat` VARCHAR ( 45 ) NULL COMMENT  '微信号' , `self_introduction` TEXT NULL COMMENT  '自我介绍' , `video_url` VARCHAR ( 45 ) NULL COMMENT  '视频展示' , `appointment_phone` VARCHAR ( 20 ) NULL COMMENT  '预约电话' , `expert_score` VARCHAR ( 45 ) NULL COMMENT  '专家评分' , `expert_comments` VARCHAR ( 200 ) NULL COMMENT  '专家点评' , `teaching_area` VARCHAR ( 45 ) NULL COMMENT  '授课区域' , `tutoring_way` VARCHAR ( 45 ) NULL COMMENT  '辅导方式' , `class_fees` INT NULL COMMENT  '课时费' , `teaching_time` VARCHAR ( 45 ) NULL COMMENT  '可授课时间；MON1/MON2/MON3/TUE1..' , `final_graduate_school` VARCHAR ( 45 ) NULL COMMENT  '最高毕业院校' , `specialty` VARCHAR ( 45 ) NULL COMMENT  '专业' , `education_certificate` VARCHAR ( 45 ) NULL COMMENT  '学历证明' , `experience_exam` VARCHAR ( 45 ) NULL , `exam_score` VARCHAR ( 20 ) NULL , `exam_certificate` VARCHAR ( 45 ) NULL COMMENT  '考试证明' , `teaching_experience` VARCHAR ( 45 ) NULL , `teaching_certificate` VARCHAR ( 45 ) NULL COMMENT  '教学证明' , `student_max_score` VARCHAR ( 45 ) NULL COMMENT  '学生最高分成绩' , `authentication_status` VARCHAR ( 45 ) NULL COMMENT  '认证状态' , PRIMARY KEY ( `user_id` ) ) ENGINE = InnoDB COMMENT =  '教师信息表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_place` ( `place_id` VARCHAR ( 32 ) NOT NULL , `place_name` VARCHAR ( 45 ) NULL , `place_address` VARCHAR ( 45 ) NULL , `phone` VARCHAR ( 20 ) NULL , `course_category` VARCHAR ( 45 ) NULL , `fees` INT NULL COMMENT  '收费标准' , `map_url` VARCHAR ( 100 ) NULL COMMENT  '所示图片地址' , PRIMARY KEY ( `place_id` ) ) ENGINE = InnoDB COMMENT =  '场地信息表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_ appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_ appointment` ( `appointment_id` VARCHAR ( 32 ) NOT NULL , `user_id` VARCHAR ( 32 ) NULL , `course_category` VARCHAR ( 45 ) NULL COMMENT  '学习科目' , `tutoring_way` VARCHAR ( 45 ) NULL , `other_content` VARCHAR ( 200 ) NULL COMMENT  '补充说明' , `mobile` VARCHAR ( 32 ) NULL , `phone` VARCHAR ( 45 ) NULL , `QQ` VARCHAR ( 45 ) NULL , `address` VARCHAR ( 45 ) NULL , PRIMARY KEY ( `appointment_id` ) ) ENGINE = InnoDB COMMENT =  '预约信息表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_user_action`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_user_action` ( `action_id` VARCHAR ( 32 ) NOT NULL , `user_id` VARCHAR ( 32 ) NULL , `action_type` VARCHAR ( 20 ) NULL COMMENT  'Listen；collect' , `action_time` DATETIME NULL , `resource_id` VARCHAR ( 32 ) NULL COMMENT  '收藏、试听的教师' , `status` VARCHAR ( 45 ) NULL , PRIMARY KEY ( `action_id` ) ) ENGINE = InnoDB COMMENT =  '用户动作表(试听、收藏)' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_pay`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_wallet` ( `wallet_id` VARCHAR ( 32 ) NOT NULL , `user_id` VARCHAR ( 45 ) NULL , `opt_time` DATETIME NULL COMMENT  '操作时间' , `money` INT NULL COMMENT  '充值金额' , `pay_status` VARCHAR ( 45 ) NULL COMMENT  '充值状态' , PRIMARY KEY ( `wallet_id` ) ) ENGINE = InnoDB COMMENT =  '充值信息表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mha_question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mha_question` ( `question_id` varchar ( 32 ) NOT NULL , `question_title` varchar ( 100 ) DEFAULT NULL COMMENT  '问题标题' , `question_content` text COMMENT  '问题内容' , `question_pic` varchar ( 100 ) DEFAULT NULL COMMENT  '上传问题图片' , `user_id` varchar ( 45 ) DEFAULT NULL COMMENT  '提问人' , `reply_user` varchar ( 32 ) DEFAULT NULL COMMENT  '回答人' , `assign_teacher` varchar ( 45 ) DEFAULT NULL COMMENT  '指定回答教师' , `answer` text COMMENT  '问题答案' , `create_time` datetime DEFAULT NULL COMMENT  '创建时间' , `answer_time` datetime DEFAULT NULL COMMENT  '回答时间' , PRIMARY KEY ( `question_id` ) ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '问答表' ;




-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_statistics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_statistics` ( `statistics_id` VARCHAR ( 32 ) NOT NULL , `type` VARCHAR ( 45 ) NULL COMMENT  '关注；报名；评论；收藏' , `object_id` VARCHAR ( 45 ) NULL , PRIMARY KEY ( `statistics_id` ) ) ENGINE = InnoDB COMMENT =  '信息统计表' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_teacher_homework`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_teacher_homework` ( `homework_id` VARCHAR ( 45 ) NOT NULL , `teacher_id` VARCHAR ( 32 ) NOT NULL , `post_time` VARCHAR ( 45 ) NULL , `homework_file` VARCHAR ( 45 ) NULL , PRIMARY KEY ( `homework_id` ) ) ENGINE = InnoDB COMMENT =  '教师发布作业' ;


-- -----------------------------------------------------
-- Table `manhattan`.`t_mht_homework_submit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `manhattan` . `t_mht_homework_submit` ( `homework_submit_id` VARCHAR ( 32 ) NOT NULL , `homework_id` VARCHAR ( 45 ) NULL , `user_id` VARCHAR ( 45 ) NULL COMMENT  '提交人' , `submit_file` VARCHAR ( 45 ) NULL COMMENT  '作业文件' , `submit_time` DATETIME NULL COMMENT  '提交时间' , PRIMARY KEY ( `homework_submit_id` ) ) ENGINE = InnoDB COMMENT =  '作业提交表' ; SET SQL_MODE = @ OLD_SQL_MODE ; SET FOREIGN_KEY_CHECKS = @ OLD_FOREIGN_KEY_CHECKS ; SET UNIQUE_CHECKS = @ OLD_UNIQUE_CHECKS ;
