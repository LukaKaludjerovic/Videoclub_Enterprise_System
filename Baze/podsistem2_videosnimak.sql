CREATE DATABASE  IF NOT EXISTS `podsistem2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem2`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `videosnimak`
--

DROP TABLE IF EXISTS `videosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videosnimak` (
  `IDVid` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Trajanje` int NOT NULL,
  `Vlasnik` int NOT NULL,
  `DatumVreme` datetime NOT NULL,
  PRIMARY KEY (`IDVid`),
  KEY `Vlasnik_idx` (`Vlasnik`),
  CONSTRAINT `Vlasnik` FOREIGN KEY (`Vlasnik`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videosnimak`
--

LOCK TABLES `videosnimak` WRITE;
/*!40000 ALTER TABLE `videosnimak` DISABLE KEYS */;
INSERT INTO `videosnimak` VALUES (1,'Titanik',245,3,'1995-10-20 15:15:00'),(2,'Manifest',183,4,'2021-02-05 10:20:00'),(3,'Kum',367,5,'1990-05-07 12:00:00'),(4,'La Dolce Vita',96,6,'2017-08-08 13:00:00'),(5,'Casablanka',150,3,'2024-02-04 12:17:24');
/*!40000 ALTER TABLE `videosnimak` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 15:03:28
