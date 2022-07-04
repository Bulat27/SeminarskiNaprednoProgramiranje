/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.25 : Database - automobile_service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`automobile_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `automobile_service`;

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `role` varchar(10) NOT NULL,
  `hourly_rate` double(10,2) NOT NULL DEFAULT '0.00',
  `date_of_employment` date NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `chk_username` (`username`),
  CONSTRAINT `chk_satnica` CHECK ((`hourly_rate` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee` */

insert  into `employee`(`id`,`first_name`,`last_name`,`role`,`hourly_rate`,`date_of_employment`,`username`,`password`) values 
(47,'Nikola','Bulat','ADMIN',10.00,'2020-07-29','nikola','nikola'),
(49,'Zdravko','Bulat','ADMIN',5.00,'2000-05-05','zdravko','zdravko'),
(50,'Kica','Kicanovic','WORKER',2.00,'2020-09-09','kica','kica');

/*Table structure for table `employee_engagement` */

DROP TABLE IF EXISTS `employee_engagement`;

CREATE TABLE `employee_engagement` (
  `employee_id` bigint unsigned NOT NULL,
  `repair_id` bigint unsigned NOT NULL,
  `order_number` int unsigned NOT NULL,
  `duration` int unsigned NOT NULL,
  PRIMARY KEY (`employee_id`,`repair_id`,`order_number`),
  KEY `employee_engagement_ibfk_2` (`repair_id`,`order_number`),
  CONSTRAINT `employee_engagement_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `employee_engagement_ibfk_2` FOREIGN KEY (`repair_id`, `order_number`) REFERENCES `repair_item` (`repair_id`, `order_number`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chk_duration` CHECK ((`duration` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee_engagement` */

insert  into `employee_engagement`(`employee_id`,`repair_id`,`order_number`,`duration`) values 
(47,66,1,5),
(47,67,3,3),
(47,69,1,3),
(47,70,2,2),
(47,71,1,5),
(47,73,1,2),
(47,74,2,5),
(49,65,2,2),
(49,67,2,3),
(49,72,1,2),
(50,65,1,3),
(50,67,1,3),
(50,68,1,5),
(50,70,1,3),
(50,74,1,3);

/*Table structure for table `repair` */

DROP TABLE IF EXISTS `repair`;

CREATE TABLE `repair` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_date` date NOT NULL,
  `total_revenue` double(10,2) NOT NULL DEFAULT '0.00',
  `total_expense` double(10,2) NOT NULL DEFAULT '0.00',
  `service_book_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_book_id` (`service_book_id`),
  CONSTRAINT `repair_ibfk_1` FOREIGN KEY (`service_book_id`) REFERENCES `service_book` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_total_expense` CHECK ((`total_expense` > 0)),
  CONSTRAINT `chk_total_revenue` CHECK ((`total_revenue` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `repair` */

insert  into `repair`(`id`,`name`,`start_date`,`total_revenue`,`total_expense`,`service_book_id`) values 
(65,'Mali servis','2022-05-05',165.00,51.00,24),
(66,'Centriranje trapa','2021-05-05',50.00,51.00,24),
(67,'Veliki servis','2022-05-05',490.00,91.00,24),
(68,'Remont glave motora','2022-07-07',400.00,110.00,24),
(69,'Zamena ulja','2020-05-05',115.00,50.00,24),
(70,'Zamena ulja i filtera','2022-05-05',190.00,71.00,25),
(71,'Remont glave motora','2022-05-08',400.00,120.00,25),
(72,'Centriranje trapa','2021-05-05',55.00,15.00,25),
(73,'Zamena kocnica','2022-08-08',65.00,30.00,25),
(74,'Veliki servis','2020-05-05',240.00,86.00,25);

/*Table structure for table `repair_item` */

DROP TABLE IF EXISTS `repair_item`;

CREATE TABLE `repair_item` (
  `repair_id` bigint unsigned NOT NULL,
  `order_number` int unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `employee_expense` double(10,2) NOT NULL,
  `additional_expense` double(10,2) NOT NULL DEFAULT '0.00',
  `additional_revenue` double(10,2) NOT NULL DEFAULT '0.00',
  `service_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`repair_id`,`order_number`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `repair_item_ibfk_1` FOREIGN KEY (`repair_id`) REFERENCES `repair` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repair_item_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_date` CHECK ((`start_date` < `end_date`)),
  CONSTRAINT `chk_employee_expense` CHECK ((`employee_expense` > 0)),
  CONSTRAINT `chk_order_number` CHECK ((`order_number` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `repair_item` */

insert  into `repair_item`(`repair_id`,`order_number`,`start_date`,`end_date`,`remark`,`employee_expense`,`additional_expense`,`additional_revenue`,`service_id`) values 
(65,1,'2022-05-05','2022-06-05','Nema dodatnih napomena',6.00,10.00,50.00,26),
(65,2,'2022-05-05','2022-06-06','',10.00,5.00,15.00,26),
(66,1,'2021-05-05','2021-05-06','Nema dodatnih napomena',50.00,1.00,10.00,28),
(67,1,'2022-05-08','2022-05-09','',6.00,5.00,100.00,29),
(67,2,'2022-06-06','2022-07-07','',15.00,5.00,50.00,25),
(67,3,'2022-05-05','2022-05-08','',30.00,10.00,50.00,28),
(68,1,'2022-07-07','2022-07-08','',10.00,50.00,100.00,27),
(69,1,'2020-05-05','2020-05-06','',30.00,5.00,15.00,25),
(70,1,'2022-05-05','2022-05-06','',6.00,10.00,20.00,25),
(70,2,'2022-05-05','2022-05-07','',20.00,10.00,20.00,26),
(71,1,'2022-05-08','2022-05-10','',50.00,20.00,100.00,27),
(72,1,'2021-05-05','2021-07-05','',10.00,5.00,15.00,28),
(73,1,'2022-08-08','2022-08-09','',20.00,5.00,15.00,30),
(74,1,'2020-05-05','2020-05-07','',6.00,10.00,20.00,29),
(74,2,'2020-08-08','2020-08-09','',50.00,15.00,30.00,28);

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `price` double(10,2) NOT NULL DEFAULT '0.00',
  `name` varchar(30) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `material_cost` double(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  CONSTRAINT `chk_price` CHECK ((`price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`price`,`name`,`description`,`material_cost`) values 
(25,100.00,'Zamena ulja','Zamena ulja na kolima.',15.00),
(26,50.00,'Zamena filtera','Zamena filtera za ulje i vazduh.',10.00),
(27,300.00,'Remont glave motora','Kompletan remont glave motora.',50.00),
(28,40.00,'Centriranje trapa','Centriranje trapa.',0.00),
(29,150.00,'Zamena zupcastog kaisa','Zameniti zupcasti kais',5.00),
(30,50.00,'Zamena kocnica','Zameniti kocnice na automobilu.',5.00);

/*Table structure for table `service_book` */

DROP TABLE IF EXISTS `service_book`;

CREATE TABLE `service_book` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `client_first_name` varchar(30) NOT NULL,
  `client_last_name` varchar(30) NOT NULL,
  `vehicle_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `initial_date` date NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `service_book` */

insert  into `service_book`(`id`,`client_first_name`,`client_last_name`,`vehicle_description`,`initial_date`,`active`) values 
(24,'Jovan','Tonkic','Peugeot 308 1.6','2022-07-03',1),
(25,'Igor','Maksimovic','Ford Fiesta 1.4','2022-07-04',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
