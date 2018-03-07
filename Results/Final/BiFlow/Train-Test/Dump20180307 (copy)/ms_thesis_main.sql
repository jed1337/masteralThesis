-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: ms_thesis
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `main`
--

DROP TABLE IF EXISTS `main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `main` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SystemType` varchar(45) NOT NULL,
  `CategoricalType` varchar(45) NOT NULL,
  `NoiseLevel` float(6,5) NOT NULL,
  `Dataset` varchar(45) NOT NULL,
  `ExtractionTool` varchar(45) NOT NULL,
  `Timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `idmain_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=536 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `main`
--

LOCK TABLES `main` WRITE;
/*!40000 ALTER TABLE `main` DISABLE KEYS */;
INSERT INTO `main` VALUES (496,'Single','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:42:49'),(497,'Single','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:43:03'),(498,'Single','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:43:31'),(499,'Single','SPECIFIC',0.50000,'Final/','BiFlow','2018-03-07 20:43:50'),(500,'Hybrid isAttack','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:44:20'),(501,'Hybrid isAttack','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:44:30'),(502,'Hybrid DDoS Type','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:45:45'),(503,'Hybrid DDoS Type','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:45:56'),(504,'Single','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:46:14'),(505,'Single','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:46:27'),(506,'Single','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:46:50'),(507,'Single','SPECIFIC',0.50000,'Final/','BiFlow','2018-03-07 20:47:09'),(508,'Hybrid isAttack','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:47:38'),(509,'Hybrid isAttack','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:47:48'),(510,'Hybrid DDoS Type','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:48:05'),(511,'Hybrid DDoS Type','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:48:16'),(512,'Single','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:48:35'),(513,'Single','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:48:48'),(514,'Single','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:48:59'),(515,'Single','SPECIFIC',0.50000,'Final/','BiFlow','2018-03-07 20:49:12'),(516,'Hybrid isAttack','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:49:32'),(517,'Hybrid isAttack','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:49:40'),(518,'Hybrid DDoS Type','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:49:47'),(519,'Hybrid DDoS Type','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:49:55'),(520,'Single','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:50:11'),(521,'Single','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:52:12'),(522,'Single','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:52:57'),(523,'Single','SPECIFIC',0.50000,'Final/','BiFlow','2018-03-07 20:54:53'),(524,'Hybrid isAttack','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:56:13'),(525,'Hybrid isAttack','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 20:56:30'),(526,'Hybrid DDoS Type','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 20:57:23'),(527,'Hybrid DDoS Type','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 20:58:08'),(528,'Single','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 21:03:31'),(529,'Single','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 21:04:39'),(530,'Single','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 21:10:32'),(531,'Single','SPECIFIC',0.50000,'Final/','BiFlow','2018-03-07 21:16:39'),(532,'Hybrid isAttack','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 21:25:12'),(533,'Hybrid isAttack','GENERAL',0.50000,'Final/','BiFlow','2018-03-07 21:26:27'),(534,'Hybrid DDoS Type','GENERAL',0.00000,'Final/','BiFlow','2018-03-07 21:30:14'),(535,'Hybrid DDoS Type','SPECIFIC',0.00000,'Final/','BiFlow','2018-03-07 21:30:37');
/*!40000 ALTER TABLE `main` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-07 21:37:39
