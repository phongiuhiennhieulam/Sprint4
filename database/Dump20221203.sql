-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: demoqlpl
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `demo`
--

DROP TABLE IF EXISTS `demo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `id_staff` bigint DEFAULT NULL,
  `id_welfare` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8sw0rkld6onvpg41qbi0vbyte` (`id_staff`),
  KEY `FKn45bi25e7epv23a6ib8nrkpff` (`id_welfare`),
  CONSTRAINT `FK8sw0rkld6onvpg41qbi0vbyte` FOREIGN KEY (`id_staff`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKn45bi25e7epv23a6ib8nrkpff` FOREIGN KEY (`id_welfare`) REFERENCES `welfare` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo`
--

LOCK TABLES `demo` WRITE;
/*!40000 ALTER TABLE `demo` DISABLE KEYS */;
/*!40000 ALTER TABLE `demo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demo12312344`
--

DROP TABLE IF EXISTS `demo12312344`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demo12312344` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT NULL,
  `id_staff` bigint DEFAULT NULL,
  `id_welfare` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4rsd7cp724ycfabhhpb17iydt` (`id_staff`),
  KEY `FK544pyjkobnm8oxokflmsdv231` (`id_welfare`),
  CONSTRAINT `FK4rsd7cp724ycfabhhpb17iydt` FOREIGN KEY (`id_staff`) REFERENCES `staff` (`id`),
  CONSTRAINT `FK544pyjkobnm8oxokflmsdv231` FOREIGN KEY (`id_welfare`) REFERENCES `welfare` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo12312344`
--

LOCK TABLES `demo12312344` WRITE;
/*!40000 ALTER TABLE `demo12312344` DISABLE KEYS */;
/*!40000 ALTER TABLE `demo12312344` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Phòng nhân sự',0),(2,'Phòng kế toán',0);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_welfare`
--

DROP TABLE IF EXISTS `general_welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_welfare` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` bit(1) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_welfare`
--

LOCK TABLES `general_welfare` WRITE;
/*!40000 ALTER TABLE `general_welfare` DISABLE KEYS */;
INSERT INTO `general_welfare` VALUES (2,'Trà chiều',1000000.00,NULL,0,'Đồ ăn và trà miễn phí mỗi tháng 1 ngày ');
/*!40000 ALTER TABLE `general_welfare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_update`
--

DROP TABLE IF EXISTS `money_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_update` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `manv` varchar(255) DEFAULT NULL,
  `money_update` decimal(19,2) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `id_staff` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_update`
--

LOCK TABLES `money_update` WRITE;
/*!40000 ALTER TABLE `money_update` DISABLE KEYS */;
INSERT INTO `money_update` VALUES (1,'NV37',30000000.00,0,37),(2,'NV36',30000000.00,0,37),(3,'NV35',30000000.00,0,37),(4,'NV22',500000.00,0,37),(7,'NV42',9999.00,0,20);
/*!40000 ALTER TABLE `money_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register_welfare`
--

DROP TABLE IF EXISTS `register_welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `register_welfare` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `id_welfare` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1yu0vnj6260u69gni40ywfl4q` (`id_welfare`),
  CONSTRAINT `FK1yu0vnj6260u69gni40ywfl4q` FOREIGN KEY (`id_welfare`) REFERENCES `welfare` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register_welfare`
--

LOCK TABLES `register_welfare` WRITE;
/*!40000 ALTER TABLE `register_welfare` DISABLE KEYS */;
/*!40000 ALTER TABLE `register_welfare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_MODERATOR'),(3,'ROLE_ADMIN'),(4,'ROLE_PERSONNEL');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` int DEFAULT NULL,
  `welfare_money` decimal(19,2) NOT NULL,
  `id_department` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpvctx4dbua9qh4p4s3gm3scrh` (`email`),
  UNIQUE KEY `UK9iyphxhcq6d836q8vrykuw6vx` (`code`),
  KEY `FKk9ompbt1aoqm791st85h8ny73` (`id_department`),
  CONSTRAINT `FKk9ompbt1aoqm791st85h8ny73` FOREIGN KEY (`id_department`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (18,'NV1','2001-12-12 07:00:00','phong@gmail.com','Phong Doan Hai',0,20000000.00,1),(19,'NV2','2002-05-12 00:00:00','khang@gmail.com','Khang Ngu',0,20000000.00,2),(20,'NV3','2002-08-12 00:00:00','khangngu@gmail.com','Khang Tran',0,18000000.00,1),(22,'NV4','2002-08-08 00:00:00','thanh@gmail.com','Thanh',0,19000000.00,2),(24,'NV5','2001-06-04 07:00:00','hoang@gmail.com','Hoàng Trung',0,15000000.00,2),(25,'NV6','2002-10-08 00:00:00','hung@gmail.com','Hùng Nguyễn ',0,20000000.00,2),(26,'NV7','2006-07-05 00:00:00','long@gmail.com','Long Nguyễn',0,21000000.00,1),(27,'NV8','2006-07-11 07:00:00','kien@gmail.com','Kiên Nguyễn',0,25000000.00,1),(28,'NV9','2006-07-11 00:00:00','vanh@gmail.com','Vanh Nguyễn',0,19000000.00,2),(29,'NV10','2001-11-12 00:00:00','khoankien@gmail.com','Khoan Nguyễn Hoàng',0,20000000.00,1),(33,'NV13','2006-07-11 00:00:00','vanhca@gmail.com','Vanh Nguyễn',0,19000000.00,2),(34,'NV14','2001-12-11 00:00:00','khoan@gmail.com','Khoan Nguyễn',0,21000000.00,1),(36,'NV15','2006-10-11 00:00:00','hoa@gmail.com','Hòa Nguyễn',0,20000000.00,1),(37,'NV16','2001-10-11 00:00:00','huy@gmail.com','Huy Nguyễn',0,20000000.00,2),(38,'NV17','2002-03-04 00:00:00','yen@gmail.com','Yến Nguyễn',0,21000000.00,1),(46,'NV20','2001-06-04 00:00:00','hu@gmail.com','Xiao Hu',0,21000000.00,1),(47,'NV21','2001-02-05 00:00:00','zing@gmail.com','Zing Zi Long',0,20000000.00,2),(48,'NV22','2001-06-05 00:00:00','ca34@gmail.com','Ca Nguyễn',0,22000000.00,1),(49,'NV23','2001-04-03 00:00:00','phu@gmail.com','Phú Hoàng',0,21000000.00,2),(50,'NV24','2002-08-03 00:00:00','tan@gmail.com','Tân Nguyễn',1,22000000.00,1),(51,'NV25','2001-06-04 00:00:00','tien@gmail.com','Tiến Hoàng',1,20000000.00,1),(52,'NV26','2001-08-06 00:00:00','thien@gmail.com','Thiên Tê',0,25000000.00,2),(53,'NV27','2001-08-03 07:00:00','toan@gmail.com','Toàn Nguyễn',0,15000000.00,1),(54,'NV28','2002-06-04 07:00:00','tam@gmail.com','Tâm Ca',0,18000000.00,2),(65,'NV35','2001-04-03 00:00:00','le@gmail.com','Lê Hoàng',0,21000000.00,2),(66,'NV36','2002-08-03 07:00:00','sao@gmail.com','Sao Nguyễn',0,21000000.00,1),(67,'NV37','2022-05-06 07:00:00','dao@gmail.com','Lâm Hoàng Đạo',0,27000000.00,1),(78,'NV40','2001-12-12 00:00:00','caca@gmail.com','Ca Long',0,22000000.00,2),(80,'NV42','2001-04-05 07:00:00','su@gmail.com','Su Su',0,28000000.00,2),(86,'NV0001','2022-11-03 07:00:00','phongcahaiduong@gmail.com','Về ',0,22000000.00,1),(87,'NV60','2001-04-06 07:00:00','phonghai@gmail.com','Phong Hai',0,25000000.00,1);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(32,2),(36,2),(53,2),(9,3),(14,3),(20,3),(35,3),(37,3),(53,3),(35,4),(37,4),(47,4);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (8,'Phong Doan','$2a$10$vPjd2MeEaP4s.xI7O.hNZOOiXtGUKureO5PONFIf7tTvz9W5uoMde',0,'phong@gmail.com'),(9,'Khang Ngu','$2a$10$gzTWEB.IqXIpzmQGZ2Acy.HmIPoSPSu6l8cvXMi1yBGSFweBdvxw6',0,'khang@gmail.com'),(10,'Khang Tran','$2a$10$0Zzpm6qNfMmhfwf6OIecFutLAlPNIbCOfyhPg6EJfCkrrmCb0EbHC',0,'khangngu@gmail.com'),(11,'Thanh','$2a$10$dgzE8fk5vql7hByu9s.hJ.FwedIu7eRXR3DdYz7i9IaqjkKnJ9BCK',0,'thanh@gmail.com'),(12,'Hoàng Trung','$2a$10$EH42n9CRr14U8MnUHQYa/OGoPpIC8cMoSryS/KXilAS3RvtLqETFa',0,'hoang@gmail.com'),(13,'Hùng Nguyễn ','$2a$10$FC/PdCmg0hu2/MCuu6NY5.A.KOk8VR7YIziZWKKGE9xfByuZ4XJW6',0,'hung@gmail.com'),(14,'Long Nguyễn','$2a$10$aFEIee82/XIGc/A0.xdgge8VbQYQuW8lA6xQyDYM9z.PZQMeVcXkK',0,'long@gmail.com'),(15,'Kiên Nguyễn','$2a$10$8btEBHb5GwOQAVGsvbNQuOjMd3LeIxegmSz8UntrWWWju.tKYy.mq',0,'kien@gmail.com'),(16,'Vanh Nguyễn','$2a$10$aYPlHSLDCEPyoIyAufvKF.Koa9e3nHzGhKSoSon9HgjMyE/qGfila',0,'vanh@gmail.com'),(17,'Khoan Nguyễn','$2a$10$SbhfVvV5EgnlsAhXTI2vu.L/xz1QM6KZtAI0vSJkBZxbfnN26vIj6',0,'khoan@gmail.com'),(18,'Vanh Nguyễn','$2a$10$p0KKSuUIVSOFaKimK.JhbuvMv.l7PaRM5NjJUCb6wrP.Kvz8v8mOi',0,'vadsnh@gmail.com'),(19,'Khoan Nguyễn','$2a$10$YrtSuCFjMK9P3aLISNetMeJfV/94PwoyJw8Glfa1gRH.u6xCCfJzO',0,'khodsan@gmail.com'),(20,'Hòa Nguyễn','$2a$10$wwqxZvZ4mmkBvjNg5L5K5OKBEi2GGHqRJy2c1ISluSezOBO9uAFs6',0,'hoa@gmail.com'),(21,'Huy Nguyễn','$2a$10$Dxczm7HZGBbVS3dSHb5M8Of/05kUJ/qVRIUnMGUtxJMWRRroswvmW',0,'huy@gmail.com'),(22,'Yến Nguyễn','$2a$10$AiwmlZ8KT1YfgMfCRHAeSuXTYcklVk18mJxUVXzIaqYWwFNmYmEBO',0,'yen@gmail.com'),(23,'Thu Nguyễn','$2a$10$gf1R9d14JAwafBS6K0z3deLjLlXlGLJu9MOwLYpSsWVAwySj0Z9mS',0,'thu@gmail.com'),(29,'Xiao Hu','$2a$10$Z.aWGkTEX2F9h7s2PSBvYO/HdvcHxBBndeK.V4uNvyfCjM.i6o6HO',0,'hu@gmail.com'),(30,'Zing Zi Long','$2a$10$4IZiurhGdaCv4Cw0/WdjduEZzRzgZRhDLfeq2ZQrmmoymVBy3cuoO',0,'zing@gmail.com'),(31,'Ca Nguyễn','$2a$10$W6F8astmCTs/VyC61u4Yr..Lay8v523QOAy7rQpP3fiRi6YxH5TcO',0,'ca34@gmail.com'),(32,'Phú Hoàng','$2a$10$UppkFsUH17MaNESuoQR7Q.6ai5eEKNJnrlj7c5H4RCIm/f89QNj7m',0,'phu@gmail.com'),(33,'Tân Nguyễn','$2a$10$LG.amzoTDszV2kp1zmw2UOywVx/bLW4SPEz5GaKy64lq19qE9tH3S',1,'tan@gmail.com'),(34,'Tiến Hoàng','$2a$10$VPqiLGgT9W8mPt374KcBMusPbUbei8yRVgPGA0cVBBjm2Dig2dfB6',1,'tien@gmail.com'),(35,'Thiên Tê','$2a$10$T1dHzgqIq1t92JCNqFaxvOdZgFLMBUfK0sjRnj2TZ6GYJPiCwZdXe',0,'thien@gmail.com'),(36,'Toàn Nguyễn','$2a$10$DUF2BXcxJ9FO0GEjt6I4CO7PUZ1X9hjtHbRR6QGWamHx7HczdYUKO',0,'toan@gmail.com'),(37,'Tâm Ca','$2a$10$1eYO8aEyFUDb8brMEVWpoe.9Ut/GDL7LCMrcq/8QILrcQr6XXWKOu',0,'tam@gmail.com'),(47,'Lê Hoàng','$2a$10$b6eLadhkmpULpp3GdejRY.cLj8P6UOwJbQ5ZCKtCbvx8GK4ogND/e',0,'le@gmail.com'),(48,'Sao Nguyễn','$2a$10$ySnikehjB7fUI7mDbNgvZedZzpv7JRLfqLiKd7APp2LcpYtUJhySq',0,'sao@gmail.com'),(49,'Lâm Hoàng Đạo','$2a$10$MrwkxNiNxseSjhZz9FDHGex84vc.DBDHXcKAlK6dA0UEuoTiDM9Fa',0,'dao@gmail.com'),(50,'Ca Long','$2a$10$DWuCrekcQ6OSlP.Ejsic8uEjiKHHNcQq/zdgp3CAWxu.wZbGzUxTK',0,'caca@gmail.com'),(51,'Su Su','$2a$10$lKZNF.s8H8ZSpBYLvMqETusYflvoO3FAQLM9RhChSbZtIiGAAKxxC',0,'su@gmail.com'),(52,'Về ','$2a$10$X.NIrnwkRCOqJGTujMht4e2gEZmZ8I4xyZDRG6gj0uC/APUMJ6AEi',0,'phongcahaiduong@gmail.com'),(53,'Phong Hai','$2a$10$mdbKCAH398WZPTW9DnoPR.S09oxZBUJaGSbLRIs8ve.d3ruV578qu',0,'phonghai@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `welfare`
--

DROP TABLE IF EXISTS `welfare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `welfare` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_quantity` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `welfare`
--

LOCK TABLES `welfare` WRITE;
/*!40000 ALTER TABLE `welfare` DISABLE KEYS */;
INSERT INTO `welfare` VALUES (1,_binary '','Bảo hiểm',1000000.00,0,'Đóng bảo hiểm xã hội 1 năm 1 lần'),(2,_binary '\0','Quà trung thu',200000.00,0,'Quà trung thu dịp trung '),(3,_binary '\0','Nghỉ phép có lương',400000.00,0,'Được nghỉ phép hưởng full lương'),(4,_binary '\0','Cây cảnh',300000.00,0,'Mua cây cảnh trang trí ngày tết'),(5,_binary '','Karaoke',300000.00,0,'Sự kiện tổ chức cho nhân viên ca hát'),(6,_binary '\0','Team building',10000000.00,0,'Du lịch mỗi năm tối đa 3 lần tùy nhân viên '),(7,_binary '','Dạ hội',1500000.00,0,'Sự kiện nhảy múa khiêu vũ 1 năm 1 lần '),(8,_binary '','Đi muộn ',200000.00,0,'Có thể đi muộn 3 ngày 1 tháng'),(9,_binary '','Linh hoạt thời gian',100000.00,0,'Linh hoạt về thời gian làm việc'),(11,_binary '\0','Demo tay',1200000.00,0,'Demo phúc lợi 2');
/*!40000 ALTER TABLE `welfare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `welfare_staff`
--

DROP TABLE IF EXISTS `welfare_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `welfare_staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_staff` bigint DEFAULT NULL,
  `id_welfare` bigint DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `welfare_staff`
--

LOCK TABLES `welfare_staff` WRITE;
/*!40000 ALTER TABLE `welfare_staff` DISABLE KEYS */;
INSERT INTO `welfare_staff` VALUES (1,19,2,6,0),(2,19,1,1,0),(3,19,2,1,0),(4,19,3,1,0),(5,19,3,3,0),(6,19,2,3,0),(7,19,3,4,0),(8,24,1,1,0),(9,24,2,2,0),(10,19,2,1,0),(11,19,2,1,0),(12,19,2,1,0),(13,22,2,1,0),(14,22,2,1,0),(15,24,2,1,0),(16,24,3,1,0),(17,25,2,1,0),(18,25,1,1,0),(19,27,2,1,0),(20,27,1,1,0),(21,28,2,1,0),(22,28,1,1,0),(23,18,2,1,0),(24,33,2,1,0),(25,34,2,1,0),(26,36,1,1,0),(27,36,2,1,0),(28,37,2,1,0),(29,37,1,1,0),(30,19,2,2,0),(31,19,3,2,0),(32,19,2,3,0),(33,19,3,4,1),(34,19,2,1,0),(35,19,3,1,0),(36,53,1,1,0),(37,53,2,1,0),(38,53,3,1,0),(39,53,2,1,0),(40,53,3,1,0),(41,53,4,1,1),(42,53,5,1,1),(43,53,2,11,2),(44,19,2,1,0),(45,19,3,1,0),(46,19,4,1,2),(47,19,5,1,2),(48,19,2,1,2),(49,19,3,1,2),(50,19,4,1,2),(51,19,7,1,2),(52,19,8,1,2),(53,19,9,1,2),(55,87,1,1,0),(56,87,2,7,0),(57,87,3,4,2),(58,87,2,1,2),(59,87,3,1,2),(60,36,2,9,0),(61,36,3,3,0);
/*!40000 ALTER TABLE `welfare_staff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-03 21:08:08
