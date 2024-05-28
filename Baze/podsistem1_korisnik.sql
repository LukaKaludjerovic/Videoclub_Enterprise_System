CREATE DATABASE  IF NOT EXISTS `podsistem1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem1`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem1
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
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL AUTO_INCREMENT,
  `Ime` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Godiste` varchar(45) NOT NULL,
  `Pol` varchar(45) NOT NULL,
  `IDMes` int NOT NULL,
  PRIMARY KEY (`IDKor`),
  KEY `FK_IDMes_idx` (`IDMes`),
  CONSTRAINT `FK_IDMes` FOREIGN KEY (`IDMes`) REFERENCES `mesto` (`IDMes`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Jelena Cicvaric','jelenacicvaric@gmail.com','2003','Z',1),(2,'Nikola Milina','nikolamilina@gmail.com','2002','M',1),(3,'Bojana Zecevic','bojanazecevic@gmail.com','2002','Z',1),(4,'Zarko Bodroski','zarko@maxeler.com','1990','M',2),(5,'Miljana Kulic','miljanakulic@gmail.com','1991','Z',3),(6,'Ivan Milankovic','imilankovic@groq.com','1985','M',4),(10,'Slavica Stasevic','slavicastasevic@gmail.com','2003','Z',5),(21,'Ana Vitkovic','anavitkovic@gmail.com','2002','Z',2),(22,'Ana Bulatovic','anabulatovic@gmail.com','2002','Z',7),(23,'Konstantin Vuckovic','kolevu@gmail.com','2003','M',7),(25,'Vladimir Vuckovic','vladavu@gmail.com','2003','M',6),(26,'Jovan','jovance@gmail.com','2005','M',2),(27,'Jovan Babovic','jbabovic@groq.com','2002','M',2);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
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
