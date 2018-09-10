-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: kantor
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator` (
  `idAdministrator` int(11) NOT NULL AUTO_INCREMENT,
  `imie` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwisko` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `pesel` varchar(11) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `telefon` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `uzytkownik_idUzytkownik` int(11) NOT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idAdministrator`,`uzytkownik_idUzytkownik`,`administrator_idAdministrator`),
  UNIQUE KEY `idAdministrator_UNIQUE` (`idAdministrator`),
  KEY `fk_administrator_uzytkownik1_idx` (`uzytkownik_idUzytkownik`),
  KEY `fk_administrator_administrator1_idx` (`administrator_idAdministrator`),
  CONSTRAINT `fk_administrator_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_administrator_uzytkownik1` FOREIGN KEY (`uzytkownik_idUzytkownik`) REFERENCES `uzytkownik` (`iduzytkownik`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'Ewa','Krakowska-Admin',NULL,NULL,NULL,2,1),(2,'Krzysztof','BiaÅostocki','54012531762','852963741','2018-09-04 08:23:29',24,1);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danedolar`
--

DROP TABLE IF EXISTS `danedolar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `danedolar` (
  `idDolar` int(11) NOT NULL AUTO_INCREMENT,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'USD',
  `bid` decimal(9,4) DEFAULT NULL,
  `ask` decimal(9,4) DEFAULT NULL,
  `imieOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwiskoOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `operator_idOperator` int(11) NOT NULL,
  PRIMARY KEY (`idDolar`,`operator_idOperator`),
  UNIQUE KEY `idDolar_UNIQUE` (`idDolar`),
  KEY `fk_dolar_operator1_idx` (`operator_idOperator`),
  CONSTRAINT `fk_dolar_operator1` FOREIGN KEY (`operator_idOperator`) REFERENCES `operator` (`idoperator`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danedolar`
--

LOCK TABLES `danedolar` WRITE;
/*!40000 ALTER TABLE `danedolar` DISABLE KEYS */;
INSERT INTO `danedolar` VALUES (1,'USD',0.9500,1.0500,'Alojzy','Operator2','2018-08-20 19:31:28',4),(2,'USD',0.9700,1.0700,'Alojzy','Operator2','2018-08-20 19:31:35',4),(3,'USD',0.9910,1.0100,'Alojzy','Operator2','2018-08-20 19:36:24',4),(4,'USD',0.0970,1.0700,'Adam','NowakOperator','2018-08-20 19:36:58',3),(5,'USD',0.9555,1.0555,'Adam','NowakOperator','2018-08-20 19:37:19',3),(6,'USD',0.8900,1.1100,'Adam','NowakOperator','2018-08-20 19:50:43',3),(7,'USD',0.9800,1.0200,'Adam','NowakOperator','2018-08-20 22:16:04',3),(8,'USD',0.9800,1.0500,'Alojzy','Operator2','2018-08-21 00:02:59',4),(9,'USD',0.9910,1.0100,'Adam','NowakOperator','2018-08-23 18:40:34',3),(10,'USD',0.9700,1.0700,'Adam','NowakOperator','2018-08-23 19:28:50',3),(11,'USD',0.9700,1.0700,'Adam','NowakOperator','2018-08-23 19:28:50',3),(12,'USD',0.9500,1.0500,'Alojzy','Operator2','2018-08-24 01:07:12',4),(13,'USD',0.9910,1.0100,'Adam','NowakOperator','2018-08-25 21:06:39',3),(14,'USD',0.9800,1.0700,'Adam','NowakOperator','2018-08-31 20:16:45',3),(15,'USD',0.9700,1.0200,'Adam','NowakOperator','2018-09-03 16:37:13',3),(16,'USD',0.9800,1.0100,'Alojzy','Operator2','2018-09-07 22:22:38',4);
/*!40000 ALTER TABLE `danedolar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daneeuro`
--

DROP TABLE IF EXISTS `daneeuro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `daneeuro` (
  `idEuro` int(11) NOT NULL AUTO_INCREMENT,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'EUR',
  `bid` decimal(9,4) DEFAULT NULL,
  `ask` decimal(9,4) DEFAULT NULL,
  `imieOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwiskoOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `operator_idOperator` int(11) NOT NULL,
  PRIMARY KEY (`idEuro`,`operator_idOperator`),
  UNIQUE KEY `idDolar_UNIQUE` (`idEuro`),
  KEY `fk_dolar_operator1_idx` (`operator_idOperator`),
  CONSTRAINT `fk_dolar_operator10` FOREIGN KEY (`operator_idOperator`) REFERENCES `operator` (`idoperator`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daneeuro`
--

LOCK TABLES `daneeuro` WRITE;
/*!40000 ALTER TABLE `daneeuro` DISABLE KEYS */;
INSERT INTO `daneeuro` VALUES (1,'EUR',0.9500,1.0500,'Alojzy','Operator2','2018-08-20 19:35:06',4),(2,'EUR',0.9560,1.0560,'Alojzy','Operator2','2018-08-20 19:35:20',4),(3,'EUR',0.9580,1.0580,'Alojzy','Operator2','2018-08-20 19:36:36',4),(4,'EUR',0.9590,1.0590,'Adam','NowakOperator','2018-08-20 19:37:27',3),(5,'EUR',0.9570,1.0570,'Adam','NowakOperator','2018-08-20 19:37:41',3),(6,'EUR',0.9900,1.0100,'Adam','NowakOperator','2018-08-20 22:16:22',3),(7,'EUR',0.9560,1.0560,'Alojzy','Operator2','2018-08-21 00:00:00',4),(8,'EUR',0.9570,1.0570,'Adam','NowakOperator','2018-08-23 18:46:55',3),(9,'EUR',0.9570,1.0570,'Adam','NowakOperator','2018-08-23 18:46:55',3),(10,'EUR',0.9590,1.0590,'Alojzy','Operator2','2018-08-24 18:47:32',4),(11,'EUR',0.9900,1.0100,'Alojzy','Operator2','2018-08-31 19:51:50',4);
/*!40000 ALTER TABLE `daneeuro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danefrank`
--

DROP TABLE IF EXISTS `danefrank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `danefrank` (
  `idFrank` int(11) NOT NULL AUTO_INCREMENT,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'CHF',
  `bid` decimal(9,4) DEFAULT NULL,
  `ask` decimal(9,4) DEFAULT NULL,
  `imieOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwiskoOperatora` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `operator_idOperator` int(11) NOT NULL,
  PRIMARY KEY (`idFrank`,`operator_idOperator`),
  UNIQUE KEY `idDolar_UNIQUE` (`idFrank`),
  KEY `fk_dolar_operator1_idx` (`operator_idOperator`),
  CONSTRAINT `fk_dolar_operator100` FOREIGN KEY (`operator_idOperator`) REFERENCES `operator` (`idoperator`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danefrank`
--

LOCK TABLES `danefrank` WRITE;
/*!40000 ALTER TABLE `danefrank` DISABLE KEYS */;
INSERT INTO `danefrank` VALUES (1,'CHF',0.9500,1.0500,'Adam','NowakOperator','2018-08-20 19:57:58',3),(2,'CHF',0.9600,1.0600,'Adam','NowakOperator','2018-08-20 19:58:18',3),(3,'CHF',0.9567,1.0567,'Alojzy','Operator2','2018-08-20 19:58:52',4),(4,'CHF',0.9950,1.0050,'Adam','NowakOperator','2018-08-20 22:16:41',3),(5,'CHF',0.9567,1.0567,'Adam','NowakOperator','2018-08-20 22:50:48',3),(6,'CHF',0.9644,1.0644,'Adam','NowakOperator','2018-08-21 00:28:40',3),(7,'CHF',0.9567,1.0567,'Adam','NowakOperator','2018-08-23 19:37:36',3),(8,'CHF',0.9600,1.0200,'Alojzy','Operator2','2018-09-10 19:19:36',4);
/*!40000 ALTER TABLE `danefrank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klientfirmowy`
--

DROP TABLE IF EXISTS `klientfirmowy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `klientfirmowy` (
  `idKlientFirmowy` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(100) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `regon` varchar(9) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nip` varchar(10) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kod` varchar(6) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `miasto` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `ulica` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nrDomu` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nrLokalu` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `imiePracownika` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwiskoPracownika` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `telefonPracownika` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `uzytkownik_idUzytkownik` int(11) NOT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idKlientFirmowy`,`uzytkownik_idUzytkownik`,`administrator_idAdministrator`),
  UNIQUE KEY `idKlientFirmowy_UNIQUE` (`idKlientFirmowy`),
  KEY `fk_klientFirmowy_uzytkownik1_idx` (`uzytkownik_idUzytkownik`),
  KEY `fk_klientFirmowy_administrator1_idx` (`administrator_idAdministrator`),
  CONSTRAINT `fk_klientFirmowy_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_klientFirmowy_uzytkownik1` FOREIGN KEY (`uzytkownik_idUzytkownik`) REFERENCES `uzytkownik` (`iduzytkownik`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klientfirmowy`
--

LOCK TABLES `klientfirmowy` WRITE;
/*!40000 ALTER TABLE `klientfirmowy` DISABLE KEYS */;
INSERT INTO `klientfirmowy` VALUES (1,'Firma 1 sp. z o.o.','571438091','1149902325',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1),(2,'Firma 2 S.A.','496611200','8263150422',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-08-25 00:00:00',8,1),(3,'Firma 3 Sp. z o.o.','156671555','5219342780','00-000','Kraków','','','','Jan','Krakowski','012000000','2018-09-02 00:00:00',10,1),(4,'Firma 4 S.A.','53070321','5683598903','','','','','','','','','2018-09-02 00:00:00',11,1),(5,'Firma 5 Sp. Kom.','32598768','7583850787','','','','','','','','','2018-09-02 00:00:00',12,1),(6,'Firma 6 Sp. J.','573801058','7629740073','00-006','KrakÃ³w','DÅuga','1','','Adam','Adamski','0123456789','2018-09-03 06:43:09',13,1),(7,'Firma 7 Jerzy Krakowski','658330794','1182759960','00-007','KrakÃ³w','MÅyÅska','7','7','Jerzy','Krakowski','987654321','2018-09-03 08:04:20',14,1),(8,'Firma 8 Adam ÅÃ³dzki','417989940','1132180407','00-008','ÅÃ³dÅº','ÅÃ³dzka','8','8','Adam','ÅÃ³dzki','456987123','2018-09-03 08:51:23',15,1),(9,'Firma 9 Maurycy BÅoÅski','951766480','5292898110','00-009','WaÅbrzych','GÅÃ³wna','9','9','Maurycy','BÅoÅski','159357852','2018-09-10 19:47:38',25,2);
/*!40000 ALTER TABLE `klientfirmowy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klientprywatny`
--

DROP TABLE IF EXISTS `klientprywatny`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `klientprywatny` (
  `idKlientPrywatny` int(11) NOT NULL AUTO_INCREMENT,
  `imie` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwisko` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `pesel` varchar(11) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kod` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `miasto` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `ulica` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nrLokalu` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nrDomu` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `telefon` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `uzytkownik_idUzytkownik` int(11) NOT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idKlientPrywatny`,`uzytkownik_idUzytkownik`,`administrator_idAdministrator`),
  UNIQUE KEY `idKlientPrywatny_UNIQUE` (`idKlientPrywatny`),
  KEY `fk_klientPrywatny_uzytkownik1_idx` (`uzytkownik_idUzytkownik`),
  KEY `fk_klientPrywatny_administrator1_idx` (`administrator_idAdministrator`),
  CONSTRAINT `fk_klientPrywatny_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_klientPrywatny_uzytkownik1` FOREIGN KEY (`uzytkownik_idUzytkownik`) REFERENCES `uzytkownik` (`iduzytkownik`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klientprywatny`
--

LOCK TABLES `klientprywatny` WRITE;
/*!40000 ALTER TABLE `klientprywatny` DISABLE KEYS */;
INSERT INTO `klientprywatny` VALUES (1,'Jan','Kowalski','85100897963',NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,1),(2,'Eugeniusz','Zawadzki','85021055855',NULL,NULL,NULL,NULL,NULL,NULL,'2018-08-30 00:00:00',9,1),(3,'Zbigniew','PoznaÅski','45101701713','00-003','PoznaÅ','PoznaÅska','3','3','741852963','2018-09-03 11:39:25',16,1),(4,'Bartłomiej','Gdański','86011749332','00-004','GdaÅsk','GdaÅska','','4','852963741','2018-09-03 12:10:27',17,1),(5,'JÃ³zef','MaÅomÃ³wny','97121675309','01-005','KrakÃ³w','DÅuga','','5','159357852','2018-09-10 21:26:57',26,2);
/*!40000 ALTER TABLE `klientprywatny` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operator`
--

DROP TABLE IF EXISTS `operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `operator` (
  `idOperator` int(11) NOT NULL AUTO_INCREMENT,
  `imie` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `nazwisko` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `pesel` varchar(11) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `telefon` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `dataDodania` datetime DEFAULT NULL,
  `uzytkownik_idUzytkownik` int(11) NOT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idOperator`,`uzytkownik_idUzytkownik`,`administrator_idAdministrator`),
  UNIQUE KEY `idOperator_UNIQUE` (`idOperator`),
  KEY `fk_operator_uzytkownik1_idx` (`uzytkownik_idUzytkownik`),
  KEY `fk_operator_administrator1_idx` (`administrator_idAdministrator`),
  CONSTRAINT `fk_operator_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_operator_uzytkownik1` FOREIGN KEY (`uzytkownik_idUzytkownik`) REFERENCES `uzytkownik` (`iduzytkownik`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operator`
--

LOCK TABLES `operator` WRITE;
/*!40000 ALTER TABLE `operator` DISABLE KEYS */;
INSERT INTO `operator` VALUES (3,'Adam','NowakOperator','97092463266','123456','2018-08-17 00:00:00',3,1),(4,'Alojzy','Operator2','88092799413','987456','2018-08-17 00:00:00',6,1),(5,'Lucyna','Lublińska','97062405601','987654321','2018-09-03 19:29:22',19,1),(6,'Józef','Olsztyński','83033099610','456987123','2018-09-03 19:39:41',20,1),(7,'Barbara','WrocÅawska','58022612930','8426951357','2018-09-03 19:50:45',21,1);
/*!40000 ALTER TABLE `operator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rachunekchf`
--

DROP TABLE IF EXISTS `rachunekchf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rachunekchf` (
  `idRachunekCHF` int(11) NOT NULL AUTO_INCREMENT,
  `nrRachunku` varchar(28) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'CHF',
  `dataUtworzenia` datetime DEFAULT NULL,
  `klientFirmowy_idKlientFirmowy` int(11) DEFAULT NULL,
  `klientPrywatny_idKlientPrywatny` int(11) DEFAULT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idRachunekCHF`,`administrator_idAdministrator`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idRachunekCHF`),
  KEY `fk_rachunekUSD_administrator1_idx` (`administrator_idAdministrator`),
  KEY `fk_rachunekUSD_klientPrywatny1_idx` (`klientPrywatny_idKlientPrywatny`),
  KEY `fk_rachunekUSD_klientFirmowy1_idx` (`klientFirmowy_idKlientFirmowy`),
  CONSTRAINT `fk_rachunekUSD_administrator100` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_rachunekUSD_klientFirmowy100` FOREIGN KEY (`klientFirmowy_idKlientFirmowy`) REFERENCES `klientfirmowy` (`idklientfirmowy`),
  CONSTRAINT `fk_rachunekUSD_klientPrywatny100` FOREIGN KEY (`klientPrywatny_idKlientPrywatny`) REFERENCES `klientprywatny` (`idklientprywatny`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rachunekchf`
--

LOCK TABLES `rachunekchf` WRITE;
/*!40000 ALTER TABLE `rachunekchf` DISABLE KEYS */;
INSERT INTO `rachunekchf` VALUES (1,'68109019719811723887946280','CHF','2018-09-07 22:00:00',6,NULL,2),(2,'46124050192112596910089834','CHF','2018-09-07 22:03:40',NULL,4,2),(3,'56124010958625173409523305','CHF','2018-09-08 07:31:03',3,NULL,1),(4,'44894100066048958868749909','CHF','2018-09-08 07:33:23',NULL,1,1),(5,'54124013982798875622371302','CHF','2018-09-08 08:31:52',1,NULL,1),(6,'80847000017663321185319995','CHF','2018-09-10 20:02:45',NULL,2,2);
/*!40000 ALTER TABLE `rachunekchf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rachunekeur`
--

DROP TABLE IF EXISTS `rachunekeur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rachunekeur` (
  `idRachunekEUR` int(11) NOT NULL AUTO_INCREMENT,
  `nrRachunku` varchar(28) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'EUR',
  `dataUtworzenia` datetime DEFAULT NULL,
  `klientFirmowy_idKlientFirmowy` int(11) DEFAULT NULL,
  `klientPrywatny_idKlientPrywatny` int(11) DEFAULT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idRachunekEUR`,`administrator_idAdministrator`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idRachunekEUR`),
  KEY `fk_rachunekUSD_administrator1_idx` (`administrator_idAdministrator`),
  KEY `fk_rachunekUSD_klientPrywatny1_idx` (`klientPrywatny_idKlientPrywatny`),
  KEY `fk_rachunekUSD_klientFirmowy1_idx` (`klientFirmowy_idKlientFirmowy`),
  CONSTRAINT `fk_rachunekUSD_administrator10` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_rachunekUSD_klientFirmowy10` FOREIGN KEY (`klientFirmowy_idKlientFirmowy`) REFERENCES `klientfirmowy` (`idklientfirmowy`),
  CONSTRAINT `fk_rachunekUSD_klientPrywatny10` FOREIGN KEY (`klientPrywatny_idKlientPrywatny`) REFERENCES `klientprywatny` (`idklientprywatny`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rachunekeur`
--

LOCK TABLES `rachunekeur` WRITE;
/*!40000 ALTER TABLE `rachunekeur` DISABLE KEYS */;
INSERT INTO `rachunekeur` VALUES (1,'30106001352400870367787925','EUR','2018-09-07 21:57:59',3,NULL,2),(2,'08891300051170066804744991','EUR','2018-09-07 22:01:18',NULL,3,2),(3,'12883810155137668848327894','EUR','2018-09-08 07:27:48',1,NULL,1),(4,'60124043310519964295359638','EUR','2018-09-08 09:03:45',NULL,1,2),(5,'68109027894964609635713452','EUR','2018-09-10 20:00:24',7,NULL,1),(6,'19124029623084086053401987','EUR','2018-09-10 20:03:33',8,NULL,2);
/*!40000 ALTER TABLE `rachunekeur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rachunekpln`
--

DROP TABLE IF EXISTS `rachunekpln`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rachunekpln` (
  `idRachunekPLN` int(11) NOT NULL AUTO_INCREMENT,
  `nrRachunku` varchar(28) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'PLN',
  `dataUtworzenia` datetime DEFAULT NULL,
  `klientFirmowy_idKlientFirmowy` int(11) DEFAULT NULL,
  `klientPrywatny_idKlientPrywatny` int(11) DEFAULT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idRachunekPLN`,`administrator_idAdministrator`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idRachunekPLN`),
  KEY `fk_rachunekPLN_klientFirmowy1_idx` (`klientFirmowy_idKlientFirmowy`),
  KEY `fk_rachunekPLN_klientPrywatny1_idx` (`klientPrywatny_idKlientPrywatny`),
  KEY `fk_rachunekPLN_administrator1_idx` (`administrator_idAdministrator`),
  CONSTRAINT `fk_rachunekPLN_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_rachunekPLN_klientFirmowy1` FOREIGN KEY (`klientFirmowy_idKlientFirmowy`) REFERENCES `klientfirmowy` (`idklientfirmowy`),
  CONSTRAINT `fk_rachunekPLN_klientPrywatny1` FOREIGN KEY (`klientPrywatny_idKlientPrywatny`) REFERENCES `klientprywatny` (`idklientprywatny`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rachunekpln`
--

LOCK TABLES `rachunekpln` WRITE;
/*!40000 ALTER TABLE `rachunekpln` DISABLE KEYS */;
INSERT INTO `rachunekpln` VALUES (1,'18102000038338478208868002','PLN','2018-08-25 00:00:00',1,NULL,1),(2,'39102033528515547264625938','PLN','2018-08-30 00:00:00',NULL,1,1),(3,'80102000036365242045120384','PLN','2018-08-30 00:00:00',NULL,2,1),(4,'41102000037984856407376631','PLN','2018-08-30 00:00:00',2,NULL,1),(5,'54109019428259805562630919','PLN','2018-09-02 00:00:00',3,NULL,1),(6,'18841000030296322399224924','PLN','2018-09-02 00:00:00',4,NULL,1),(7,'31124027440485172595265294','PLN','2018-09-02 00:00:00',5,NULL,1),(8,'75902500046313130738227091','PLN','2018-09-03 06:43:09',6,NULL,1),(9,'83109026888476772561315260','PLN','2018-09-03 08:04:20',7,NULL,1),(10,'44822000045107336539062522','PLN','2018-09-03 08:51:23',8,NULL,1),(11,'18841000030296322399224924','PLN','2018-09-03 11:39:25',NULL,3,1),(12,'57147010381039418947972690','PLN','2018-09-03 12:10:27',NULL,4,1),(13,'41194012102994127406520299','PLN','2018-09-10 19:47:38',9,NULL,2),(14,'19124037380052805861026302','PLN','2018-09-10 21:26:57',NULL,5,2);
/*!40000 ALTER TABLE `rachunekpln` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rachunekusd`
--

DROP TABLE IF EXISTS `rachunekusd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rachunekusd` (
  `idRachunekUSD` int(11) NOT NULL AUTO_INCREMENT,
  `nrRachunku` varchar(28) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `znak` varchar(5) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT 'USD',
  `dataUtworzenia` datetime DEFAULT NULL,
  `klientFirmowy_idKlientFirmowy` int(11) DEFAULT NULL,
  `klientPrywatny_idKlientPrywatny` int(11) DEFAULT NULL,
  `administrator_idAdministrator` int(11) NOT NULL,
  PRIMARY KEY (`idRachunekUSD`,`administrator_idAdministrator`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idRachunekUSD`),
  KEY `fk_rachunekUSD_administrator1_idx` (`administrator_idAdministrator`),
  KEY `fk_rachunekUSD_klientPrywatny1_idx` (`klientPrywatny_idKlientPrywatny`),
  KEY `fk_rachunekUSD_klientFirmowy1_idx` (`klientFirmowy_idKlientFirmowy`),
  CONSTRAINT `fk_rachunekUSD_administrator1` FOREIGN KEY (`administrator_idAdministrator`) REFERENCES `administrator` (`idadministrator`),
  CONSTRAINT `fk_rachunekUSD_klientFirmowy1` FOREIGN KEY (`klientFirmowy_idKlientFirmowy`) REFERENCES `klientfirmowy` (`idklientfirmowy`),
  CONSTRAINT `fk_rachunekUSD_klientPrywatny1` FOREIGN KEY (`klientPrywatny_idKlientPrywatny`) REFERENCES `klientprywatny` (`idklientprywatny`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rachunekusd`
--

LOCK TABLES `rachunekusd` WRITE;
/*!40000 ALTER TABLE `rachunekusd` DISABLE KEYS */;
INSERT INTO `rachunekusd` VALUES (1,'70102000030275778613874273','USD','2018-08-25 00:00:00',1,NULL,1),(2,'55102000039022406671770509','USD','2018-08-30 00:00:00',2,NULL,1),(3,'14102000030756221952454877','USD','2018-08-30 00:00:00',NULL,1,1),(4,'10102000039881553096643545','USD','2018-08-30 00:00:00',NULL,2,1),(8,'89124047778556464335562066','USD','2018-09-07 07:24:57',3,NULL,1),(10,'22888410275839322558696979','USD','2018-09-07 19:46:03',4,NULL,1),(11,'70160011561795243244187733','USD','2018-09-07 21:37:28',NULL,3,1),(12,'28880200025482604088176550','USD','2018-09-07 21:39:53',5,NULL,1),(13,'77932100017041252749603810','USD','2018-09-10 20:04:09',7,NULL,2),(14,'70203000749613347167068720','USD','2018-09-10 20:04:30',9,NULL,2);
/*!40000 ALTER TABLE `rachunekusd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rola`
--

DROP TABLE IF EXISTS `rola`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rola` (
  `idRola` int(11) NOT NULL AUTO_INCREMENT,
  `rola` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`idRola`),
  UNIQUE KEY `idRola_UNIQUE` (`idRola`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rola`
--

LOCK TABLES `rola` WRITE;
/*!40000 ALTER TABLE `rola` DISABLE KEYS */;
INSERT INTO `rola` VALUES (1,'admin'),(2,'operator'),(3,'klientFirmowy'),(4,'klientPrywatny');
/*!40000 ALTER TABLE `rola` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uzytkownik`
--

DROP TABLE IF EXISTS `uzytkownik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `uzytkownik` (
  `idUzytkownik` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `haslo` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `rola_idRola` int(11) NOT NULL,
  `usd` tinyint(1) DEFAULT '0',
  `eur` tinyint(1) DEFAULT '0',
  `chf` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idUzytkownik`,`rola_idRola`),
  UNIQUE KEY `idUzytkownik_UNIQUE` (`idUzytkownik`),
  KEY `fk_uzytkownik_rola1_idx` (`rola_idRola`),
  CONSTRAINT `fk_uzytkownik_rola1` FOREIGN KEY (`rola_idRola`) REFERENCES `rola` (`idrola`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uzytkownik`
--

LOCK TABLES `uzytkownik` WRITE;
/*!40000 ALTER TABLE `uzytkownik` DISABLE KEYS */;
INSERT INTO `uzytkownik` VALUES (2,'admin1','dcca2ed163582435afa9d42ce361eb4',1,0,0,0),(3,'operator1','d8ddbfe9417ef7d2c0f3e69413dc323e',2,0,0,0),(4,'firma1','d0d7469653a6fd15fdd2a310bc31b813',3,1,1,1),(5,'prywatny1','84953676d38b245ce7776291bc1f1ab',4,1,1,1),(6,'operator2','1b73da48ed7f4ef541b624618660a1',2,0,0,0),(8,'firma2','37372861819a5a306aa292df23685bc',3,1,0,0),(9,'prywatny2','5ef2ebe3a9c2ddcc7564f25b317aec',4,1,0,1),(10,'firma3','8ef7a3c534b1a811279044478255b',3,1,1,1),(11,'firma4','f8f8e365bffb725b5f86337e79c1a69',3,0,0,0),(12,'firma5','6af81d569fe6eab59a7eb6f444dbbfe',3,1,0,0),(13,'firma6','b2fd4283fcbc7a69caacc164bdafa9',3,0,0,1),(14,'firma7','d2e72d778e241e327f61ee2f0a1dedc',3,1,1,0),(15,'firma8','1a498d60cb2029798689ac1826f5cba4',3,0,1,0),(16,'prywatny3','62b58472d4d1ae3e6569fe4878563',4,0,1,0),(17,'prywatny4','34941c102c5ec25f1e1f35345feaea8',4,0,0,1),(19,'operator3','51950a9c6eeab47821b86222829514',2,0,0,0),(20,'operator4','ad2d32cbfec96cb8b6a99976c14b93c3',2,0,0,0),(21,'operator5','9a25c292c0cb8b551b57df326a97dff0',2,0,0,0),(24,'admin2','958821297f6ffda49ae314467b9733c2',1,0,0,0),(25,'firma9','5ba42c83e163e4076eef95513b2cb60',3,1,0,0),(26,'prywatny5','776559327b077dd5cb4201262ddcf7',4,0,0,0);
/*!40000 ALTER TABLE `uzytkownik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapisyrachchf`
--

DROP TABLE IF EXISTS `zapisyrachchf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zapisyrachchf` (
  `idOperacji` int(11) NOT NULL AUTO_INCREMENT,
  `tytulOperacji` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kwotaWN` decimal(9,2) DEFAULT '0.00',
  `kwotaMA` decimal(9,2) DEFAULT '0.00',
  `kurs` decimal(9,4) DEFAULT NULL,
  `dataOperacji` datetime DEFAULT NULL,
  `rachunekCHF_idRachunekCHF` int(11) NOT NULL,
  PRIMARY KEY (`idOperacji`,`rachunekCHF_idRachunekCHF`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idOperacji`),
  KEY `fk_zapisyRachCHF_rachunekCHF1_idx` (`rachunekCHF_idRachunekCHF`),
  CONSTRAINT `fk_zapisyRachCHF_rachunekCHF1` FOREIGN KEY (`rachunekCHF_idRachunekCHF`) REFERENCES `rachunekchf` (`idrachunekchf`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapisyrachchf`
--

LOCK TABLES `zapisyrachchf` WRITE;
/*!40000 ALTER TABLE `zapisyrachchf` DISABLE KEYS */;
INSERT INTO `zapisyrachchf` VALUES (1,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 22:00:00',1),(2,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 22:03:40',2),(3,'Aktywacja',0.00,0.00,0.0000,'2018-09-08 07:31:03',3),(4,'Aktywacja',0.00,0.00,0.0000,'2018-09-08 07:33:23',4),(5,'Aktywacja',0.00,0.00,0.0000,'2018-09-08 08:31:52',5),(6,'Kup CHF',100.00,0.00,3.8781,'2018-09-08 20:53:08',5),(7,'Sprzedaj CHF',0.00,10.00,3.5111,'2018-09-08 20:54:25',5),(8,'Sprzedaj CHF',0.00,13.00,3.5111,'2018-09-08 21:03:18',5),(9,'Kup CHF',135.00,0.00,3.8781,'2018-09-09 14:29:53',4),(10,'Sprzedaj CHF',0.00,10.00,3.5111,'2018-09-09 14:46:53',4),(11,'Kup CHF',199.00,0.00,3.8781,'2018-09-10 18:06:00',2),(12,'Aktywacja',0.00,0.00,0.0000,'2018-09-10 20:02:45',6),(13,'Kup CHF',458.00,0.00,3.7434,'2018-09-10 20:05:38',1),(14,'Sprzedaj CHF',0.00,52.85,3.5232,'2018-09-10 20:05:53',1);
/*!40000 ALTER TABLE `zapisyrachchf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapisyracheur`
--

DROP TABLE IF EXISTS `zapisyracheur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zapisyracheur` (
  `idOperacji` int(11) NOT NULL AUTO_INCREMENT,
  `tytulOperacji` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kwotaWN` decimal(9,2) DEFAULT '0.00',
  `kwotaMA` decimal(9,2) DEFAULT '0.00',
  `kurs` decimal(9,4) DEFAULT NULL,
  `dataOperacji` datetime DEFAULT NULL,
  `rachunekEUR_idRachunekEUR` int(11) NOT NULL,
  PRIMARY KEY (`idOperacji`,`rachunekEUR_idRachunekEUR`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idOperacji`),
  KEY `fk_zapisyRachEUR_rachunekEUR1_idx` (`rachunekEUR_idRachunekEUR`),
  CONSTRAINT `fk_zapisyRachEUR_rachunekEUR1` FOREIGN KEY (`rachunekEUR_idRachunekEUR`) REFERENCES `rachunekeur` (`idrachunekeur`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapisyracheur`
--

LOCK TABLES `zapisyracheur` WRITE;
/*!40000 ALTER TABLE `zapisyracheur` DISABLE KEYS */;
INSERT INTO `zapisyracheur` VALUES (1,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 21:57:59',1),(2,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 22:01:18',2),(3,'Aktywacja',0.00,0.00,0.0000,'2018-09-08 07:27:48',3),(4,'Aktywacja',0.00,0.00,0.0000,'2018-09-08 09:03:45',4),(5,'Kup EUR',100.00,0.00,4.3430,'2018-09-08 20:29:23',3),(6,'Sprzedaj EUR',0.00,10.00,4.2570,'2018-09-08 20:34:39',3),(7,'Sprzedaj EUR',0.00,11.00,4.2570,'2018-09-08 21:06:00',3),(8,'Kup EUR',100.00,0.00,4.3430,'2018-09-09 14:25:14',4),(9,'Sprzedaj EUR',0.00,25.00,4.2570,'2018-09-09 14:25:58',4),(10,'Kup EUR',230.00,0.00,4.3430,'2018-09-10 19:59:22',2),(11,'Aktywacja',0.00,0.00,0.0000,'2018-09-10 20:00:24',5),(12,'Kup EUR',177.00,0.00,4.3430,'2018-09-10 20:01:27',5),(13,'Aktywacja',0.00,0.00,0.0000,'2018-09-10 20:03:33',6);
/*!40000 ALTER TABLE `zapisyracheur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapisyrachpln`
--

DROP TABLE IF EXISTS `zapisyrachpln`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zapisyrachpln` (
  `idOperacji` int(11) NOT NULL AUTO_INCREMENT,
  `tytulOperacji` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kwotaWN` decimal(9,2) DEFAULT '0.00',
  `kwotaMA` decimal(9,2) DEFAULT '0.00',
  `dataOperacji` datetime DEFAULT NULL,
  `rachunekPLN_idRachunekPLN` int(11) NOT NULL,
  `zapisyRachUSD_idOperacji` int(11) DEFAULT NULL,
  `zapisyRachEUR_idOperacji` int(11) DEFAULT NULL,
  `zapisyRachCHF_idOperacji` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOperacji`,`rachunekPLN_idRachunekPLN`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idOperacji`),
  KEY `fk_operacjeRachPLN_rachunekPLN1_idx` (`rachunekPLN_idRachunekPLN`),
  KEY `fk_zapisyRachPLN_zapisyRachUSD1_idx` (`zapisyRachUSD_idOperacji`),
  KEY `fk_zapisyRachPLN_zapisyRachEUR1_idx` (`zapisyRachEUR_idOperacji`),
  KEY `fk_zapisyRachPLN_zapisyRachCHF1_idx` (`zapisyRachCHF_idOperacji`),
  CONSTRAINT `fk_operacjeRachPLN_rachunekPLN1` FOREIGN KEY (`rachunekPLN_idRachunekPLN`) REFERENCES `rachunekpln` (`idrachunekpln`),
  CONSTRAINT `fk_zapisyRachPLN_zapisyRachCHF1` FOREIGN KEY (`zapisyRachCHF_idOperacji`) REFERENCES `zapisyrachchf` (`idoperacji`),
  CONSTRAINT `fk_zapisyRachPLN_zapisyRachEUR1` FOREIGN KEY (`zapisyRachEUR_idOperacji`) REFERENCES `zapisyracheur` (`idoperacji`),
  CONSTRAINT `fk_zapisyRachPLN_zapisyRachUSD1` FOREIGN KEY (`zapisyRachUSD_idOperacji`) REFERENCES `zapisyrachusd` (`idoperacji`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapisyrachpln`
--

LOCK TABLES `zapisyrachpln` WRITE;
/*!40000 ALTER TABLE `zapisyrachpln` DISABLE KEYS */;
INSERT INTO `zapisyrachpln` VALUES (1,'Przelew przychodzący',10000.00,0.00,'2018-08-25 00:00:00',1,NULL,NULL,NULL),(6,'Kup USD',0.00,388.65,'2018-08-25 19:29:41',1,12,NULL,NULL),(7,'Kup USD',0.00,388.65,'2018-08-25 19:41:17',1,14,NULL,NULL),(8,'Sprzedaj USD',35.16,0.00,'2018-08-25 20:33:32',1,15,NULL,NULL),(9,'Kup USD',0.00,1165.95,'2018-08-25 20:42:08',1,16,NULL,NULL),(10,'Kup USD',0.00,128.25,'2018-08-25 20:48:10',1,17,NULL,NULL),(11,'Kup USD',0.00,777.30,'2018-08-25 20:48:22',1,18,NULL,NULL),(12,'Kup USD',0.00,3886.50,'2018-08-25 20:48:42',1,19,NULL,NULL),(13,'Sprzedaj USD',35.16,0.00,'2018-08-25 20:49:53',1,20,NULL,NULL),(14,'Sprzedaj USD',351.63,0.00,'2018-08-25 20:50:04',1,21,NULL,NULL),(15,'Sprzedaj USD',703.26,0.00,'2018-08-25 20:51:40',1,22,NULL,NULL),(16,'Kup USD',0.00,1869.20,'2018-08-25 21:07:13',1,23,NULL,NULL),(17,'Sprzedaj USD',733.62,0.00,'2018-08-25 21:07:36',1,24,NULL,NULL),(18,'Sprzedaj USD',366.81,0.00,'2018-08-26 11:34:31',1,25,NULL,NULL),(19,'Sprzedaj USD',36.49,0.00,'2018-08-27 19:46:11',1,26,NULL,NULL),(20,'Sprzedaj USD',36.49,0.00,'2018-08-27 19:46:28',1,27,NULL,NULL),(21,'Kup USD',0.00,371.88,'2018-08-28 00:44:45',1,28,NULL,NULL),(22,'Sprzedaj USD',36.22,0.00,'2018-08-28 17:35:39',1,29,NULL,NULL),(23,'Sprzedaj USD',724.38,0.00,'2018-08-28 19:07:19',1,30,NULL,NULL),(24,'Kup USD',0.00,738.26,'2018-08-28 19:09:08',1,31,NULL,NULL),(25,'Sprzedaj USD',36.22,0.00,'2018-08-28 19:25:10',1,32,NULL,NULL),(26,'Kup USD',0.00,36.91,'2018-08-28 19:25:26',1,33,NULL,NULL),(27,'Sprzedaj USD',36.22,0.00,'2018-08-28 19:59:28',1,34,NULL,NULL),(28,'Sprzedaj USD',36.22,0.00,'2018-08-28 21:50:20',1,35,NULL,NULL),(29,'Sprzedaj USD',36.22,0.00,'2018-08-28 23:23:38',1,36,NULL,NULL),(30,'Sprzedaj USD',362.19,0.00,'2018-08-28 23:50:22',1,37,NULL,NULL),(31,'Sprzedaj USD',36.42,0.00,'2018-08-29 18:56:56',1,38,NULL,NULL),(32,'Sprzedaj USD',36.39,0.00,'2018-08-30 21:12:13',1,39,NULL,NULL),(33,'Przelew przychodzący',10000.00,0.00,'2018-08-30 00:00:00',2,NULL,NULL,NULL),(34,'Przelew przychodzący',10000.00,0.00,'2018-08-30 00:00:00',3,NULL,NULL,NULL),(35,'Przelew przychodzący',10000.00,0.00,'2018-08-30 00:00:00',4,NULL,NULL,NULL),(36,'Kup USD',0.00,741.82,'2018-08-30 22:17:43',4,43,NULL,NULL),(37,'Kup USD',0.00,741.82,'2018-08-30 22:32:49',2,44,NULL,NULL),(38,'Kup USD',0.00,1112.73,'2018-08-30 22:36:16',3,45,NULL,NULL),(39,'Sprzedaj USD',36.39,0.00,'2018-08-30 22:37:02',3,46,NULL,NULL),(40,'Sprzedaj USD',36.48,0.00,'2018-08-31 19:59:15',1,47,NULL,NULL),(41,'Kup USD',0.00,557.64,'2018-08-31 19:59:58',4,48,NULL,NULL),(42,'Aktywacja rachunku',10000.00,0.00,'2018-09-02 00:00:00',5,NULL,NULL,NULL),(43,'Aktywacja rachunku',10000.00,0.00,'2018-09-02 00:00:00',6,NULL,NULL,NULL),(44,'Aktywacja rachunku',10000.00,0.00,'2018-09-02 00:00:00',7,NULL,NULL,NULL),(45,'Aktywacja rachunku',10000.00,0.00,'2018-09-03 06:43:09',8,NULL,NULL,NULL),(46,'Aktywacja rachunku',10000.00,0.00,'2018-09-03 08:04:20',9,NULL,NULL,NULL),(47,'Aktywacja rachunku',10000.00,0.00,'2018-09-03 08:51:23',10,NULL,NULL,NULL),(48,'Aktywacja rachunku',10000.00,0.00,'2018-09-03 11:39:25',11,NULL,NULL,NULL),(49,'Aktywacja rachunku',10000.00,0.00,'2018-09-03 12:10:27',12,NULL,NULL,NULL),(50,'Kup USD',0.00,37.73,'2018-09-04 11:15:39',1,49,NULL,NULL),(51,'Sprzedaj USD',36.26,0.00,'2018-09-08 07:36:31',1,55,NULL,NULL),(52,'Kup USD',0.00,37.37,'2018-09-08 07:38:46',1,56,NULL,NULL),(53,'Sprzedaj USD',36.26,0.00,'2018-09-08 18:07:47',1,57,NULL,NULL),(54,'Kup USD',0.00,37.37,'2018-09-08 18:08:17',1,58,NULL,NULL),(55,'Kup EUR',0.00,434.30,'2018-09-08 20:29:23',1,NULL,5,NULL),(56,'Sprzedaj EUR',42.57,0.00,'2018-09-08 20:34:39',1,NULL,6,NULL),(57,'Kup CHF',0.00,387.81,'2018-09-08 20:53:08',1,NULL,NULL,6),(58,'Sprzedaj CHF',35.11,0.00,'2018-09-08 20:54:25',1,NULL,NULL,7),(59,'Sprzedaj CHF',45.64,0.00,'2018-09-08 21:03:18',1,NULL,NULL,8),(60,'Sprzedaj EUR',46.83,0.00,'2018-09-08 21:06:00',1,NULL,7,NULL),(61,'Sprzedaj USD',21.76,0.00,'2018-09-08 21:06:37',1,59,NULL,NULL),(62,'Sprzedaj USD',36.26,0.00,'2018-09-09 13:28:46',1,60,NULL,NULL),(63,'Sprzedaj USD',36.26,0.00,'2018-09-09 14:23:29',2,61,NULL,NULL),(64,'Kup EUR',0.00,434.30,'2018-09-09 14:25:14',2,NULL,8,NULL),(65,'Sprzedaj EUR',106.43,0.00,'2018-09-09 14:25:58',2,NULL,9,NULL),(66,'Kup CHF',0.00,523.54,'2018-09-09 14:29:53',2,NULL,NULL,9),(67,'Sprzedaj CHF',35.11,0.00,'2018-09-09 14:46:53',2,NULL,NULL,10),(68,'Kup CHF',0.00,771.74,'2018-09-10 18:06:00',12,NULL,NULL,11),(69,'Aktywacja rachunku',10000.00,0.00,'2018-09-10 19:47:38',13,NULL,NULL,NULL),(70,'Kup EUR',0.00,998.89,'2018-09-10 19:59:22',11,NULL,10,NULL),(71,'Kup EUR',0.00,768.71,'2018-09-10 20:01:27',9,NULL,12,NULL),(72,'Kup CHF',0.00,1714.48,'2018-09-10 20:05:38',8,NULL,NULL,13),(73,'Sprzedaj CHF',186.20,0.00,'2018-09-10 20:05:53',8,NULL,NULL,14),(74,'Aktywacja rachunku',10000.00,0.00,'2018-09-10 21:26:57',14,NULL,NULL,NULL);
/*!40000 ALTER TABLE `zapisyrachpln` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapisyrachusd`
--

DROP TABLE IF EXISTS `zapisyrachusd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zapisyrachusd` (
  `idOperacji` int(11) NOT NULL AUTO_INCREMENT,
  `tytulOperacji` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `kwotaWN` decimal(9,2) DEFAULT '0.00',
  `kwotaMA` decimal(9,2) DEFAULT '0.00',
  `kurs` decimal(9,4) DEFAULT NULL,
  `dataOperacji` datetime DEFAULT NULL,
  `rachunekUSD_idRachunekUSD` int(11) NOT NULL,
  PRIMARY KEY (`idOperacji`,`rachunekUSD_idRachunekUSD`),
  UNIQUE KEY `idRachunekPLN_UNIQUE` (`idOperacji`),
  KEY `fk_operacjeRachUSD_rachunekUSD1_idx` (`rachunekUSD_idRachunekUSD`),
  CONSTRAINT `fk_operacjeRachUSD_rachunekUSD1` FOREIGN KEY (`rachunekUSD_idRachunekUSD`) REFERENCES `rachunekusd` (`idrachunekusd`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapisyrachusd`
--

LOCK TABLES `zapisyrachusd` WRITE;
/*!40000 ALTER TABLE `zapisyrachusd` DISABLE KEYS */;
INSERT INTO `zapisyrachusd` VALUES (9,'Aktywacja',0.00,0.00,0.0000,'2018-08-25 00:00:00',1),(12,'Kup USD',100.00,0.00,3.8865,'2018-08-25 19:29:41',1),(14,'Kup USD',100.00,0.00,3.8865,'2018-08-25 19:41:17',1),(15,'Sprzedaj USD',0.00,10.00,3.5163,'2018-08-25 20:33:32',1),(16,'Kup USD',300.00,0.00,3.8865,'2018-08-25 20:42:08',1),(17,'Kup USD',33.00,0.00,3.8865,'2018-08-25 20:48:10',1),(18,'Kup USD',200.00,0.00,3.8865,'2018-08-25 20:48:22',1),(19,'Kup USD',1000.00,0.00,3.8865,'2018-08-25 20:48:42',1),(20,'Sprzedaj USD',0.00,10.00,3.5163,'2018-08-25 20:49:53',1),(21,'Sprzedaj USD',0.00,100.00,3.5163,'2018-08-25 20:50:04',1),(22,'Sprzedaj USD',0.00,200.00,3.5163,'2018-08-25 20:51:40',1),(23,'Kup USD',500.00,0.00,3.7384,'2018-08-25 21:07:13',1),(24,'Sprzedaj USD',0.00,200.00,3.6681,'2018-08-25 21:07:36',1),(25,'Sprzedaj USD',0.00,100.00,3.6681,'2018-08-26 11:34:31',1),(26,'Sprzedaj USD',0.00,10.00,3.6489,'2018-08-27 19:46:11',1),(27,'Sprzedaj USD',0.00,10.00,3.6489,'2018-08-27 19:46:28',1),(28,'Kup USD',100.00,0.00,3.7188,'2018-08-28 00:44:45',1),(29,'Sprzedaj USD',0.00,10.00,3.6219,'2018-08-28 17:35:39',1),(30,'Sprzedaj USD',0.00,200.00,3.6219,'2018-08-28 19:07:19',1),(31,'Kup USD',200.00,0.00,3.6913,'2018-08-28 19:09:08',1),(32,'Sprzedaj USD',0.00,10.00,3.6219,'2018-08-28 19:25:10',1),(33,'Kup USD',10.00,0.00,3.6913,'2018-08-28 19:25:26',1),(34,'Sprzedaj USD',0.00,10.00,3.6219,'2018-08-28 19:59:28',1),(35,'Sprzedaj USD',0.00,10.00,3.6219,'2018-08-28 21:50:20',1),(36,'Sprzedaj USD',0.00,10.00,3.6219,'2018-08-28 23:23:38',1),(37,'Sprzedaj USD',0.00,100.00,3.6219,'2018-08-28 23:50:22',1),(38,'Sprzedaj USD',0.00,10.00,3.6416,'2018-08-29 18:56:56',1),(39,'Sprzedaj USD',0.00,10.00,3.6393,'2018-08-30 21:12:13',1),(40,'Aktywacja',0.00,0.00,0.0000,'2018-08-30 00:00:00',2),(41,'Aktywacja',0.00,0.00,0.0000,'2018-08-30 00:00:00',3),(42,'Aktywacja',0.00,0.00,0.0000,'2018-08-30 00:00:00',4),(43,'Kup USD',200.00,0.00,3.7091,'2018-08-30 22:17:43',2),(44,'Kup USD',200.00,0.00,3.7091,'2018-08-30 22:32:49',3),(45,'Kup USD',300.00,0.00,3.7091,'2018-08-30 22:36:16',4),(46,'Sprzedaj USD',0.00,10.00,3.6393,'2018-08-30 22:37:02',4),(47,'Sprzedaj USD',0.00,10.00,3.6477,'2018-08-31 19:59:15',1),(48,'Kup USD',150.00,0.00,3.7176,'2018-08-31 19:59:58',2),(49,'Kup USD',10.00,0.00,3.7731,'2018-09-04 11:15:39',1),(50,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 07:24:57',8),(52,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 19:46:03',10),(53,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 21:37:28',11),(54,'Aktywacja',0.00,0.00,0.0000,'2018-09-07 21:39:53',12),(55,'Sprzedaj USD',0.00,10.00,3.6260,'2018-09-08 07:36:31',1),(56,'Kup USD',10.00,0.00,3.7370,'2018-09-08 07:38:46',1),(57,'Sprzedaj USD',0.00,10.00,3.6260,'2018-09-08 18:07:47',1),(58,'Kup USD',10.00,0.00,3.7370,'2018-09-08 18:08:17',1),(59,'Sprzedaj USD',0.00,6.00,3.6260,'2018-09-08 21:06:37',1),(60,'Sprzedaj USD',0.00,10.00,3.6260,'2018-09-09 13:28:46',1),(61,'Sprzedaj USD',0.00,10.00,3.6260,'2018-09-09 14:23:29',3),(62,'Aktywacja',0.00,0.00,0.0000,'2018-09-10 20:04:09',13),(63,'Aktywacja',0.00,0.00,0.0000,'2018-09-10 20:04:30',14);
/*!40000 ALTER TABLE `zapisyrachusd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'kantor'
--
/*!50003 DROP PROCEDURE IF EXISTS `dodajAdministratora` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `dodajAdministratora`(loginIN varchar(45), haslo varchar(45), idRolaIN int(11), imieIN varchar(45), nazwiskoIN varchar(45), peselIN varchar(11), telefon varchar(45), dataDodania datetime, administrator_idAdministrator int(11))
BEGIN

DECLARE dodano boolean;
DECLARE imieOUT varchar(45);
DECLARE nazwiskoOUT varchar(45);
DECLARE loginOUT varchar(45);
DECLARE peselOUT varchar(11);

SELECT imie, nazwisko, login, pesel INTO imieOUT, nazwiskoOUT, loginOUT, peselOUT FROM uzytkownik INNER JOIN rola ON rola_idRola = idRola INNER JOIN administrator ON idUzytkownik = uzytkownik_idUzytkownik WHERE login = loginIN OR pesel = peselIN LIMIT 1;

IF loginOUT IS NOT NULL OR peselOUT IS NOT NULL THEN
SET dodano = false;
SELECT dodano, imieOUT, nazwiskoOUT, loginOUT, peselOUT;
ELSE

START TRANSACTION;
INSERT INTO `kantor`.`uzytkownik`
(`idUzytkownik`,
`login`,
`haslo`,
`rola_idRola`)
VALUES
(null, loginIN, haslo, idRolaIN);

INSERT INTO `kantor`.`administrator`
(`idAdministrator`,
`imie`,
`nazwisko`,
`pesel`,
`telefon`,
`dataDodania`,
`uzytkownik_idUzytkownik`,
`administrator_idAdministrator`)
VALUES
(null, imieIN, nazwiskoIN, peselIN, telefon, dataDodania, last_insert_id(), administrator_idAdministrator);

COMMIT;

SET dodano = true;
SELECT dodano; 
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `dodajKlientaFirmowego` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `dodajKlientaFirmowego`(loginIN varchar(45), haslo varchar(45), idRolaIN int(11), nazwaIN varchar(100), regonIN varchar(9), nipIN varchar(10), kod varchar(6), miasto varchar(45), ulica varchar(45), nrDomu varchar(45), nrLokalu varchar(45), imiePracownika varchar(45), nazwiskoPracownika varchar(45), telefonPracownika varchar(45), dataDodania datetime, administrator_idAdministrator int(11), nrRachunkuIN varchar(28))
BEGIN

DECLARE dodano boolean;
DECLARE nazwaOUT varchar(100);
DECLARE loginOUT varchar(45);
DECLARE regonOUT varchar(9);
DECLARE nipOUT varchar(10);
DECLARE nrRachunkuOUT varchar(28);

SELECT nazwa, login, regon, nip, nrRachunku INTO nazwaOUT, loginOUT, regonOUT, nipOUT, nrRachunkuOUT FROM uzytkownik INNER JOIN rola ON rola_idRola = idRola INNER JOIN klientfirmowy ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekpln ON idKlientFirmowy = klientFirmowy_idKlientFirmowy WHERE login = loginIN OR regon = regonIN OR nip = nipIN OR nrRachunku = nrRachunkuIN LIMIT 1;

IF loginOUT IS NOT NULL OR regonOUT IS NOT NULL OR nipOUT IS NOT NULL OR nrRachunkuOUT IS NOT NULL THEN
SET dodano = false;
SELECT dodano, nazwaOUT, loginOUT, regonOUT, nipOUT, nrRachunkuOUT;
ELSE

START TRANSACTION;
INSERT INTO `kantor`.`uzytkownik`
(`idUzytkownik`,
`login`,
`haslo`,
`rola_idRola`)
VALUES
(null, loginIN, haslo, idRolaIN);

INSERT INTO `kantor`.`klientfirmowy`
(`idKlientFirmowy`,
`nazwa`,
`regon`,
`nip`,
`kod`,
`miasto`,
`ulica`,
`nrDomu`,
`nrLokalu`,
`imiePracownika`,
`nazwiskoPracownika`,
`telefonPracownika`,
`dataDodania`,
`uzytkownik_idUzytkownik`,
`administrator_idAdministrator`)
VALUES
(null, nazwaIN, regonIN, nipIN, kod, miasto, ulica, nrDomu, nrLokalu, imiePracownika, nazwiskoPracownika, telefonPracownika, dataDodania, last_insert_id(), administrator_idAdministrator);

INSERT INTO `kantor`.`rachunekpln`
(`idRachunekPLN`,
`nrRachunku`,
`znak`,
`dataUtworzenia`,
`klientFirmowy_idKlientFirmowy`,
`klientPrywatny_idKlientPrywatny`,
`administrator_idAdministrator`)
VALUES
(null, nrRachunkuIN, default, dataDodania, last_insert_id(), null, administrator_idAdministrator);

INSERT INTO `kantor`.`zapisyrachpln`
(`idOperacji`,
`tytulOperacji`,
`kwotaWN`,
`kwotaMA`,
`dataOperacji`,
`rachunekPLN_idRachunekPLN`,
`zapisyRachUSD_idOperacji`)
VALUES
(null, 'Aktywacja rachunku', 10000.00, default, dataDodania, last_insert_id(), null);
COMMIT;

SET dodano = true;
SELECT dodano; 
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `dodajKlientaPrywatnego` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `dodajKlientaPrywatnego`(loginIN varchar(45), haslo varchar(45), idRolaIN int(11), imieIN varchar(45), nazwiskoIN varchar(45), peselIN varchar(11), kod varchar(6), miasto varchar(45), ulica varchar(45), nrDomu varchar(45), nrLokalu varchar(45), telefon varchar(45), dataDodania datetime, administrator_idAdministrator int(11), nrRachunkuIN varchar(28))
BEGIN

DECLARE dodano boolean;
DECLARE imieOUT varchar(45);
DECLARE nazwiskoOUT varchar(45);
DECLARE loginOUT varchar(45);
DECLARE peselOUT varchar(11);
DECLARE nrRachunkuOUT varchar(28);

SELECT imie, nazwisko, login, pesel, nrRachunku INTO imieOUT, nazwiskoOUT, loginOUT, peselOUT, nrRachunkuOUT FROM uzytkownik INNER JOIN rola ON rola_idRola = idRola INNER JOIN klientPrywatny ON idUzytkownik = uzytkownik_idUzytkownik INNER JOIN rachunekpln ON idKlientPrywatny = klientPrywatny_idKlientPrywatny WHERE login = loginIN OR pesel = peselIN OR nrRachunku = nrRachunkuIN LIMIT 1;

IF loginOUT IS NOT NULL OR peselOUT IS NOT NULL OR nrRachunkuOUT IS NOT NULL THEN
SET dodano = false;
SELECT dodano, imieOUT, nazwiskoOUT, loginOUT, peselOUT, nrRachunkuOUT;
ELSE

START TRANSACTION;
INSERT INTO `kantor`.`uzytkownik`
(`idUzytkownik`,
`login`,
`haslo`,
`rola_idRola`)
VALUES
(null, loginIN, haslo, idRolaIN);

INSERT INTO `kantor`.`klientprywatny`
(`idKlientPrywatny`,
`imie`,
`nazwisko`,
`pesel`,
`kod`,
`miasto`,
`ulica`,
`nrLokalu`,
`nrDomu`,
`telefon`,
`dataDodania`,
`uzytkownik_idUzytkownik`,
`administrator_idAdministrator`)
VALUES
(null, imieIN, nazwiskoIN, peselIN, kod, miasto, ulica, nrLokalu, nrDomu, telefon, dataDodania, last_insert_id(), administrator_idAdministrator);

INSERT INTO `kantor`.`rachunekpln`
(`idRachunekPLN`,
`nrRachunku`,
`znak`,
`dataUtworzenia`,
`klientFirmowy_idKlientFirmowy`,
`klientPrywatny_idKlientPrywatny`,
`administrator_idAdministrator`)
VALUES
(null, nrRachunkuIN, default, dataDodania, null, last_insert_id(), administrator_idAdministrator);

INSERT INTO `kantor`.`zapisyrachpln`
(`idOperacji`,
`tytulOperacji`,
`kwotaWN`,
`kwotaMA`,
`dataOperacji`,
`rachunekPLN_idRachunekPLN`,
`zapisyRachUSD_idOperacji`)
VALUES
(null, 'Aktywacja rachunku', 10000.00, default, dataDodania, last_insert_id(), null);
COMMIT;

SET dodano = true;
SELECT dodano; 
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `dodajOperatora` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `dodajOperatora`(loginIN varchar(45), haslo varchar(45), idRolaIN int(11), imieIN varchar(45), nazwiskoIN varchar(45), peselIN varchar(11), telefon varchar(45), dataDodania datetime, administrator_idAdministrator int(11))
BEGIN

DECLARE dodano boolean;
DECLARE imieOUT varchar(45);
DECLARE nazwiskoOUT varchar(45);
DECLARE loginOUT varchar(45);
DECLARE peselOUT varchar(11);

SELECT imie, nazwisko, login, pesel INTO imieOUT, nazwiskoOUT, loginOUT, peselOUT FROM uzytkownik INNER JOIN rola ON rola_idRola = idRola INNER JOIN operator ON idUzytkownik = uzytkownik_idUzytkownik WHERE login = loginIN OR pesel = peselIN LIMIT 1;

IF loginOUT IS NOT NULL OR peselOUT IS NOT NULL THEN
SET dodano = false;
SELECT dodano, imieOUT, nazwiskoOUT, loginOUT, peselOUT;
ELSE

START TRANSACTION;
INSERT INTO `kantor`.`uzytkownik`
(`idUzytkownik`,
`login`,
`haslo`,
`rola_idRola`)
VALUES
(null, loginIN, haslo, idRolaIN);

INSERT INTO `kantor`.`operator`
(`idOperator`,
`imie`,
`nazwisko`,
`pesel`,
`telefon`,
`dataDodania`,
`uzytkownik_idUzytkownik`,
`administrator_idAdministrator`)
VALUES
(null, imieIN, nazwiskoIN, peselIN, telefon, dataDodania, last_insert_id(), administrator_idAdministrator);
COMMIT;

SET dodano = true;
SELECT dodano; 
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-10 21:28:44
