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
-- Table structure for table `feature_selection`
--

DROP TABLE IF EXISTS `feature_selection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feature_selection` (
  `MainID` int(11) NOT NULL,
  `Method` varchar(512) NOT NULL,
  PRIMARY KEY (`MainID`),
  CONSTRAINT `FK_FEATURE_SELECTION_MAIN` FOREIGN KEY (`MainID`) REFERENCES `main` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature_selection`
--

LOCK TABLES `feature_selection` WRITE;
/*!40000 ALTER TABLE `feature_selection` DISABLE KEYS */;
INSERT INTO `feature_selection` VALUES (496,'No feature selection'),(497,'No feature selection'),(498,'No feature selection'),(499,'No feature selection'),(500,'No feature selection'),(501,'No feature selection'),(502,'No feature selection'),(503,'No feature selection'),(504,'Information gain'),(505,'Information gain'),(506,'Information gain'),(507,'Information gain'),(508,'Information gain'),(509,'Information gain'),(510,'Information gain'),(511,'Information gain'),(512,'Attribute correlation'),(513,'Attribute correlation'),(514,'Attribute correlation'),(515,'Attribute correlation'),(516,'Attribute correlation'),(517,'Attribute correlation'),(518,'Attribute correlation'),(519,'Attribute correlation'),(520,'NB'),(521,'NB'),(522,'NB'),(523,'NB'),(524,'NB'),(525,'NB'),(526,'NB'),(527,'NB'),(528,'J48'),(529,'J48'),(530,'J48'),(531,'J48'),(532,'J48'),(533,'J48'),(534,'J48'),(535,'J48');
/*!40000 ALTER TABLE `feature_selection` ENABLE KEYS */;
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
