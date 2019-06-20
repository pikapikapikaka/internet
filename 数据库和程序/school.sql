# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: school
# ------------------------------------------------------
# Server version 5.6.31-log

#
# Source for table book
#

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

#
# Dumping data for table book
#

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (4,'茅屋为秋风所破','杜甫');
INSERT INTO `book` VALUES (5,'石壕吏','杜甫');
INSERT INTO `book` VALUES (6,'黄鹤楼','王之涣');
INSERT INTO `book` VALUES (7,'红楼梦','曹雪芹');
INSERT INTO `book` VALUES (8,'赵帅傻逼','刘朦');
INSERT INTO `book` VALUES (10,'赏心悦目，你在深秋','刘朦');
INSERT INTO `book` VALUES (11,'what id love','ken liu');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table content
#

DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `yuan` varchar(15) DEFAULT NULL,
  `xi` varchar(15) DEFAULT NULL,
  `ban` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table content
#

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES ('信息','计算机','计算机科学技术');
INSERT INTO `content` VALUES ('信息','计算机','计开发');
INSERT INTO `content` VALUES ('信息','计算机','计测试');
INSERT INTO `content` VALUES ('信息','测绘','测绘');
INSERT INTO `content` VALUES ('信息','测绘','遥感');
INSERT INTO `content` VALUES ('信息','测绘','空间');
INSERT INTO `content` VALUES ('信息','电子','电子');
INSERT INTO `content` VALUES ('信息','电子','通信');
INSERT INTO `content` VALUES ('信息','数学','数学');
INSERT INTO `content` VALUES ('信息','数学','信息');
INSERT INTO `content` VALUES ('机电','玩机器','农机');
INSERT INTO `content` VALUES ('机电','玩机器','机械');
INSERT INTO `content` VALUES ('机电','玩机器','飞行旗');
INSERT INTO `content` VALUES ('机电','人工智能','自动化');
INSERT INTO `content` VALUES ('机电','人工智能','交通');
INSERT INTO `content` VALUES ('机电','人工智能','电信');
INSERT INTO `content` VALUES ('机电','其他','电气');
INSERT INTO `content` VALUES ('机电','其他','燃气');
INSERT INTO `content` VALUES ('金融','农业','农经');
INSERT INTO `content` VALUES ('金融','农业','农贸');
INSERT INTO `content` VALUES ('金融','农业','农会记');
INSERT INTO `content` VALUES ('金融','商业','会计');
INSERT INTO `content` VALUES ('金融','商业','金融');
INSERT INTO `content` VALUES ('金融','商业','财务管理');
INSERT INTO `content` VALUES ('金融','商业','保险');
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table control
#

DROP TABLE IF EXISTS `control`;
CREATE TABLE `control` (
  `name` varchar(15) NOT NULL,
  `value` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table control
#

LOCK TABLES `control` WRITE;
/*!40000 ALTER TABLE `control` DISABLE KEYS */;
INSERT INTO `control` VALUES ('对于学生','开放');
INSERT INTO `control` VALUES ('对于老师','开放');
/*!40000 ALTER TABLE `control` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table manager
#

DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `pwd` varchar(15) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table manager
#

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('1','1','1','1');
INSERT INTO `manager` VALUES ('admin','刘朦','admin','17863804101');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table picture
#

DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Dumping data for table picture
#

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (1,'first.jpg','/lesson_choose/picture/first.jpg');
INSERT INTO `picture` VALUES (2,'fourth.jpg','/lesson_choose/picture/fourth.jpg');
INSERT INTO `picture` VALUES (6,'timg.jpg','../picture/timg.jpg');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table question
#

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` varchar(15) DEFAULT NULL,
  `zt` int(11) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `con` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Dumping data for table question
#

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'111111',0,'操作系统','设计一个数据库的操作系统\r\n\r\n');
INSERT INTO `question` VALUES (2,'111111',1,'德玛西亚','设计一个英雄联盟\r\n');
INSERT INTO `question` VALUES (4,'10010',1,'网站设计','淘宝网站界面美化');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table student
#

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(12) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `pwd` varchar(15) DEFAULT NULL,
  `zy` varchar(10) DEFAULT NULL,
  `bj` varchar(5) DEFAULT NULL,
  `th` int(11) DEFAULT NULL,
  `xy` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table student
#

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('20143693','刘朦','1','20143693','','',0,'');
INSERT INTO `student` VALUES ('20143694','张刚',NULL,'20143694',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143695','路辉',NULL,'20143695',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143696','于甘霖',NULL,'20143696',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143697','赵帅',NULL,'20143697',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143698','许国良',NULL,'20143698',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143699','程磊',NULL,'20143699',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143700','孙琛',NULL,'20143700',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143701','王汉超',NULL,'20143701',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143702','慕馨茹',NULL,'20143702',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143703','郭诗雨',NULL,'20143703',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20143704','孙家菊',NULL,'20143704',NULL,NULL,0,NULL);
INSERT INTO `student` VALUES ('20145555','张三','男','20145555','测试','2',0,'信息');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table teacher
#

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` varchar(12) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `pwd` varchar(15) DEFAULT NULL,
  `xy` varchar(10) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `rank` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table teacher
#

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('10000','赵嘉伟',NULL,NULL,'10000',NULL,4,'教授');
INSERT INTO `teacher` VALUES ('10001','张青轩',NULL,NULL,'10001',NULL,2,'讲师');
INSERT INTO `teacher` VALUES ('10002','徐立省',NULL,NULL,'10002',NULL,4,'教授');
INSERT INTO `teacher` VALUES ('10003','张帅',NULL,NULL,'10003',NULL,3,'副教授');
INSERT INTO `teacher` VALUES ('10004','曹明明',NULL,NULL,'10004',NULL,1,'助教');
INSERT INTO `teacher` VALUES ('10005','马慧娟',NULL,NULL,'10005',NULL,4,'教授');
INSERT INTO `teacher` VALUES ('10006','李莎莎',NULL,NULL,'10006',NULL,3,'副教授');
INSERT INTO `teacher` VALUES ('10007','王永鑫',NULL,NULL,'10007',NULL,2,'讲师');
INSERT INTO `teacher` VALUES ('10008','张大力',NULL,NULL,'10008',NULL,1,'助教');
INSERT INTO `teacher` VALUES ('10009','刘二萌',NULL,NULL,'10009',NULL,2,'讲师');
INSERT INTO `teacher` VALUES ('111111','刘朦','男','110','11','信息',1,'教授');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
