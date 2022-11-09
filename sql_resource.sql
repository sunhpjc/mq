-- springboot.course definition

CREATE TABLE `course` (
  `course_id` int(10) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(10) NOT NULL,
  `course_teacher` varchar(10) NOT NULL,
  `course_number` char(4) NOT NULL,
  `id` int(32) NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


-- springboot.test definition

CREATE TABLE `test` (
  `course_id` int(10) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(10) NOT NULL,
  `course_teacher` varchar(10) NOT NULL,
  `course_number` char(4) NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- springboot.`user` definition

CREATE TABLE `user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `realName` varchar(32) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;


-- springboot.user2 definition

CREATE TABLE `user2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `birthday` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO springboot.`user` (userName,passWord,realName,status) VALUES
	 ('zhang','123456','zhang',1),
	 ('li','158845','li',1),
	 ('bob','123456789','张',0),
	 ('Tom','41010126','李',0),
	 ('Tim','41598126','刘',0),
	 ('yyyTim','41958126','刘',0),
	 ('yyyTim888','252828282','王',0),
	 ('last','1589558','lim',0),
	 ('lili','15885','张li',0),
	 ('异常','1298556','异常处理',0);
INSERT INTO springboot.`user` (userName,passWord,realName,status) VALUES
	 ('id测试','54894894','id测试',0),
	 ('a0','password0','test',0),
	 ('a1','password1','test',0),
	 ('a2','password2','test',0),
	 ('a3','password3','test',0),
	 ('a4','password4','test',0),
	 ('a0','password0','2020-05-07 14:47:18',0),
	 ('a1','password1','2020-05-07 14:47:18',0),
	 ('a2','password2','2020-05-07 14:47:18',0),
	 ('a3','password3','2020-05-07 14:47:18',0);
INSERT INTO springboot.`user` (userName,passWord,realName,status) VALUES
	 ('a4','password4','2020-05-07 14:47:18',0),
	 ('a0','password0','2020-05-11 10:31:09',NULL),
	 ('a1','password1','2020-05-11 10:31:09',NULL),
	 ('a2','password2','2020-05-11 10:31:09',NULL),
	 ('a3','password3','2020-05-11 10:31:09',NULL),
	 ('a4','password4','2020-05-11 10:31:09',NULL);
INSERT INTO springboot.course (course_name,course_teacher,course_number,id) VALUES
	 ('1','1','1',1),
	 ('2','2','2',2),
	 ('3','3','3',3),
	 ('4','4','4',4);