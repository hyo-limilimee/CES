-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: DB20212226
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_number` varchar(20) NOT NULL,
  `balance` int(10) DEFAULT NULL,
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('A01',500),('A02',10),('A03',20),('A04',40),('A05',80),('A06',100),('A07',1000),('A08',800);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrower` (
  `customer_id` varchar(3) NOT NULL,
  `loan_number` varchar(20) NOT NULL,
  PRIMARY KEY (`customer_id`,`loan_number`),
  KEY `loan_number` (`loan_number`),
  CONSTRAINT `borrower_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `borrower_ibfk_2` FOREIGN KEY (`loan_number`) REFERENCES `loan` (`loan_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
INSERT INTO `borrower` VALUES ('C01','L01'),('C03','L02'),('C04','L03'),('C04','L04'),('C05','L05'),('C06','L05'),('C04','L06'),('C01','L07'),('C01','L08');
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `branch_name` varchar(100) NOT NULL,
  `branch_city` varchar(100) DEFAULT NULL,
  `assets` int(10) DEFAULT NULL,
  PRIMARY KEY (`branch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('관악지점','서울',500),('대전역지점','대전',100),('범계지점','안양',120),('수성지점','대구',80),('숭실대지점','서울',300),('여의도지점','서울',200),('천천지점','수원',250);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` varchar(3) NOT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_street` varchar(100) DEFAULT NULL,
  `customer_city` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('C01','이강인','장안구 천천동 18번지','수원'),('C02','손흥민','관악구 봉천동 77번지','서울'),('C03','이승우','팔달구 행궁동 11번지','수원'),('C04','이재성','동작구 상도동 10번지','서울'),('C05','조규성','단원구 중앙동 99번지','안산'),('C06','김민재','중구 은행동 4번지','대전'),('C07','황희찬','수영구 광안동 111번지','부산'),('C08','설영우','남구 옥동 22번지','울산');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_banker`
--

DROP TABLE IF EXISTS `customer_banker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_banker` (
  `customer_id` varchar(3) NOT NULL,
  `employee_id` varchar(3) NOT NULL,
  PRIMARY KEY (`customer_id`,`employee_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `customer_banker_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `customer_banker_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_banker`
--

LOCK TABLES `customer_banker` WRITE;
/*!40000 ALTER TABLE `customer_banker` DISABLE KEYS */;
INSERT INTO `customer_banker` VALUES ('C01','E01'),('C02','E01'),('C03','E03'),('C04','E04'),('C05','E05'),('C06','E06'),('C07','E07');
/*!40000 ALTER TABLE `customer_banker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `depositor`
--

DROP TABLE IF EXISTS `depositor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `depositor` (
  `customer_id` varchar(3) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  PRIMARY KEY (`customer_id`,`account_number`),
  KEY `account_number` (`account_number`),
  CONSTRAINT `depositor_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `depositor_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `account` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depositor`
--

LOCK TABLES `depositor` WRITE;
/*!40000 ALTER TABLE `depositor` DISABLE KEYS */;
INSERT INTO `depositor` VALUES ('C01','A01'),('C02','A02'),('C02','A03'),('C04','A04'),('C05','A05'),('C06','A06'),('C07','A07'),('C08','A08');
/*!40000 ALTER TABLE `depositor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` varchar(3) NOT NULL,
  `employee_name` varchar(100) DEFAULT NULL,
  `telephone_number` varchar(11) DEFAULT NULL,
  `employee_length` int(10) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('E01','김민지','1234-5678',5,'2018-01-01'),('E02','박하니','876-5432',3,'2020-03-15'),('E03','강해린','234-6789',11,'2012-06-20'),('E04','이혜인','8765-4321',18,'2005-09-10'),('E05','장원영','3456-7890',22,'2001-02-05'),('E06','안유진','543-2109',7,'2016-04-30'),('E07','이현서','890-1234',1,'2022-07-25'),('E08','김채원','820-1274',21,'2002-07-25'),('E09','홍은채','490-1439',13,'2010-04-25'),('E10','허윤진','550-8739',10,'2013-01-28');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_dependent`
--

DROP TABLE IF EXISTS `employee_dependent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_dependent` (
  `employee_id` varchar(3) NOT NULL,
  `dependent_name` varchar(100) NOT NULL,
  PRIMARY KEY (`employee_id`,`dependent_name`),
  CONSTRAINT `employee_dependent_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_dependent`
--

LOCK TABLES `employee_dependent` WRITE;
/*!40000 ALTER TABLE `employee_dependent` DISABLE KEYS */;
INSERT INTO `employee_dependent` VALUES ('E01','배인혁'),('E01','정해인'),('E02','남주혁'),('E03','박서준'),('E03','박은빈'),('E03','배수지'),('E03','양세종'),('E04','차은우');
/*!40000 ALTER TABLE `employee_dependent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `loan_number` varchar(20) NOT NULL,
  `amount` int(10) DEFAULT NULL,
  PRIMARY KEY (`loan_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES ('L01',5),('L02',10),('L03',15),('L04',300),('L05',600),('L06',70),('L07',20),('L08',10);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_branch`
--

DROP TABLE IF EXISTS `loan_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_branch` (
  `loan_number` varchar(20) NOT NULL,
  `branch_name` varchar(100) NOT NULL,
  PRIMARY KEY (`loan_number`,`branch_name`),
  KEY `branch_name` (`branch_name`),
  CONSTRAINT `loan_branch_ibfk_1` FOREIGN KEY (`loan_number`) REFERENCES `loan` (`loan_number`),
  CONSTRAINT `loan_branch_ibfk_2` FOREIGN KEY (`branch_name`) REFERENCES `branch` (`branch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_branch`
--

LOCK TABLES `loan_branch` WRITE;
/*!40000 ALTER TABLE `loan_branch` DISABLE KEYS */;
INSERT INTO `loan_branch` VALUES ('L01','관악지점'),('L07','관악지점'),('L08','관악지점'),('L04','대전역지점'),('L05','범계지점'),('L02','숭실대지점'),('L03','여의도지점'),('L06','천천지점');
/*!40000 ALTER TABLE `loan_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_worker`
--

DROP TABLE IF EXISTS `manager_worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager_worker` (
  `employee_manager` varchar(20) NOT NULL,
  `employee_worker` varchar(20) NOT NULL,
  PRIMARY KEY (`employee_manager`,`employee_worker`),
  KEY `employee_worker` (`employee_worker`),
  CONSTRAINT `manager_worker_ibfk_1` FOREIGN KEY (`employee_manager`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `manager_worker_ibfk_2` FOREIGN KEY (`employee_worker`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_worker`
--

LOCK TABLES `manager_worker` WRITE;
/*!40000 ALTER TABLE `manager_worker` DISABLE KEYS */;
INSERT INTO `manager_worker` VALUES ('E09','E01'),('E01','E02'),('E01','E04'),('E02','E05'),('E04','E06'),('E05','E07'),('E06','E08'),('E07','E09'),('E08','E10');
/*!40000 ALTER TABLE `manager_worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `loan_number` varchar(20) NOT NULL,
  `payment_number` int(10) NOT NULL,
  `payment_amount` int(10) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  PRIMARY KEY (`loan_number`,`payment_number`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`loan_number`) REFERENCES `loan` (`loan_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('L01',1,20,'2020-02-15'),('L01',2,30,'2023-11-04'),('L02',1,20,'2020-04-20'),('L03',1,50,'2022-05-25'),('L03',2,30,'2023-10-04'),('L03',3,70,'2023-11-04'),('L04',1,10,'2022-07-05');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-16 18:20:09
