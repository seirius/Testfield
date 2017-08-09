-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: testfield
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PATH` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (71,'/files/manual/manual_69/1606 - flcl fooly_cooly furi_kuri gainax.png'),(72,'/files/manual/manual_69/7324_flcl.jpg'),(73,'/files/manual/manual_69/1197241127903.jpg'),(74,'/files/manual/manual_69/FLCL 1600x1200 Wallpaper 001.jpg'),(75,'/files/manual/manual_69/flcl02_1600.jpg'),(76,'/files/manual/manual_69/flcl6mn3.png'),(77,'/files/manual/manual_69/061_FLCL_Toumei_no_Kagami.jpg'),(78,'/files/manual/manual_69/1617 - flcl fooly_cooly furi_kuri gainax.jpg'),(79,'/files/manual/manual_69/1639 - flcl fooly_cooly furi_kuri gainax.jpg'),(80,'/files/manual/manual_69/7321_flcl.jpg'),(81,'/files/manual/manual_69/ninamori1600.jpg'),(82,'/files/manual/manual_69/Ride-on-Shooting-Star-flcl-9982482-1600-1200.jpg'),(83,'/files/manual/manual_69/bocetoseirius.jpg'),(84,'/files/manual/manual_69/cn.PNG'),(85,'/files/manual/manual_69/mh1.PNG'),(86,'/files/manual/manual_69/mhf 2011-03-13 02-44-14-82.bmp'),(87,'/files/manual/manual_69/mhf 2011-03-13 14-27-30-46.bmp'),(88,'/files/manual/manual_69/mhf 2011-03-13 17-56-55-35.bmp'),(89,'/files/manual/manual_69/mhf 2011-03-13 18-15-11-29.bmp'),(90,'/files/manual/manual_69/mhf 2011-03-13 22-10-51-32.bmp'),(91,'/files/manual/manual_69/mhf 2011-03-17 23-35-51-17.bmp'),(92,'/files/manual/manual_69/mhf 2011-03-19 12-45-01-96.bmp'),(93,'/files/manual/manual_69/mhf 2011-03-19 12-45-08-37.bmp'),(94,'/files/manual/manual_69/mhf 2011-03-22 01-49-42-74.bmp'),(95,'/files/manual/manual_69/mhf 2011-03-22 01-49-48-12.bmp'),(96,'/files/manual/manual_69/mhf 2011-03-22 13-19-45-47.bmp'),(97,'/files/manual/manual_69/mhf 2011-03-22 13-19-56-66.bmp'),(98,'/files/manual/manual_69/mhf 2011-03-23 14-32-43-99.bmp'),(99,'/files/manual/manual_69/mhf 2011-03-23 14-47-48-73.bmp'),(100,'/files/manual/manual_69/mhf 2011-03-23 14-47-56-83.bmp'),(101,'/files/manual/manual_69/mhf 2011-03-23 14-48-04-87.bmp'),(102,'/files/manual/manual_69/mhf 2011-03-23 14-48-15-34.bmp'),(103,'/files/manual/manual_67/289.jpg'),(104,'/files/manual/manual_67/4357322_Furi_Kuri__1.jpg'),(105,'/files/manual/manual_67/5918398_460s.jpg'),(106,'/files/manual/manual_67/All-hail-The-King-in-the-North.jpg'),(107,'/files/manual/manual_67/Avatar.PNG'),(108,'/files/manual/manual_67/avatarsei.PNG'),(109,'/files/manual/manual_67/Bjmi8JdCUAAHifi.png'),(110,'/files/manual/manual_67/Constantine-final.jpg'),(111,'/files/manual/manual_67/facebook_profile_poro_13_9.png'),(112,'/files/manual/manual_67/facebook_wide_poro_13_9.png'),(113,'/files/manual/manual_67/fce8876a3923f70ea5f2277e4fa12995.png'),(114,'/files/manual/manual_67/flcl_takun0039.jpg'),(115,'/files/manual/manual_67/furi-kuri.jpg'),(116,'/files/manual/manual_67/i-am-not-pretty-i-am-kawaii-a-kawaii-potato.png'),(117,'/files/manual/manual_67/smile.jpg'),(118,'/files/manual/manual_67/Sons_of_Big_Boss_by_Zonnex.jpg'),(119,'/files/manual/manual_67/tumblr_mjey4539rP1qjebjqo3_1280.jpg'),(120,'/files/manual/manual_67/wallpaper-105437.jpg'),(121,'/files/manual/manual_69/wallup-404488.jpg');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_manual`
--

DROP TABLE IF EXISTS `file_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_manual` (
  `MANUAL_ID` int(11) NOT NULL,
  `FILE_ID` int(11) NOT NULL,
  PRIMARY KEY (`MANUAL_ID`,`FILE_ID`),
  KEY `FILE_ID` (`FILE_ID`),
  CONSTRAINT `file_manual_ibfk_1` FOREIGN KEY (`MANUAL_ID`) REFERENCES `manual` (`ID`),
  CONSTRAINT `file_manual_ibfk_2` FOREIGN KEY (`FILE_ID`) REFERENCES `file` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_manual`
--

LOCK TABLES `file_manual` WRITE;
/*!40000 ALTER TABLE `file_manual` DISABLE KEYS */;
INSERT INTO `file_manual` VALUES (69,71),(69,72),(69,73),(69,74),(69,75),(69,76),(69,77),(69,78),(69,79),(69,80),(69,81),(69,82),(69,83),(69,84),(69,85),(69,86),(69,87),(69,88),(69,89),(69,90),(69,91),(69,92),(69,93),(69,94),(69,95),(69,96),(69,97),(69,98),(69,99),(69,100),(69,101),(69,102),(67,103),(67,104),(67,105),(67,106),(67,107),(67,108),(67,109),(67,110),(67,111),(67,112),(67,113),(67,114),(67,115),(67,116),(67,117),(67,118),(67,119),(67,120),(69,121);
/*!40000 ALTER TABLE `file_manual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_user`
--

DROP TABLE IF EXISTS `file_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_user` (
  `USER_NICK` varchar(30) NOT NULL,
  `FILE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_NICK`,`FILE_ID`),
  KEY `FILE_ID` (`FILE_ID`),
  CONSTRAINT `file_user_ibfk_1` FOREIGN KEY (`USER_NICK`) REFERENCES `user_testfield` (`USER_NICK`),
  CONSTRAINT `file_user_ibfk_2` FOREIGN KEY (`FILE_ID`) REFERENCES `file` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_user`
--

LOCK TABLES `file_user` WRITE;
/*!40000 ALTER TABLE `file_user` DISABLE KEYS */;
INSERT INTO `file_user` VALUES ('andriy',71),('andriy',72),('andriy',73),('andriy',74),('andriy',75),('andriy',76),('andriy',77),('andriy',78),('andriy',79),('andriy',80),('andriy',81),('andriy',82),('andriy',83),('andriy',84),('andriy',85),('andriy',86),('andriy',87),('andriy',88),('andriy',89),('andriy',90),('andriy',91),('andriy',92),('andriy',93),('andriy',94),('andriy',95),('andriy',96),('andriy',97),('andriy',98),('andriy',99),('andriy',100),('andriy',101),('andriy',102),('andriy',103),('andriy',104),('andriy',105),('andriy',106),('andriy',107),('andriy',108),('andriy',109),('andriy',110),('andriy',111),('andriy',112),('andriy',113),('andriy',114),('andriy',115),('andriy',116),('andriy',117),('andriy',118),('andriy',119),('andriy',120),('andriy',121);
/*!40000 ALTER TABLE `file_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `font_color`
--

DROP TABLE IF EXISTS `font_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `font_color` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `R` int(3) NOT NULL,
  `G` int(3) NOT NULL,
  `B` int(3) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `font_color`
--

LOCK TABLES `font_color` WRITE;
/*!40000 ALTER TABLE `font_color` DISABLE KEYS */;
INSERT INTO `font_color` VALUES (1,99,121,54),(2,150,222,0),(3,0,0,0),(4,0,0,0),(5,0,0,0),(6,0,0,0),(7,0,0,0),(8,0,0,0),(9,0,0,0),(10,0,0,0),(11,0,0,0),(12,0,0,0),(13,0,0,222),(14,0,0,0),(15,0,0,0),(16,0,0,0),(17,0,0,0),(18,0,0,0),(19,0,0,0);
/*!40000 ALTER TABLE `font_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `font_conf`
--

DROP TABLE IF EXISTS `font_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `font_conf` (
  `type` int(1) NOT NULL,
  `css_style` varchar(255) NOT NULL DEFAULT 'initial',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `font_conf`
--

LOCK TABLES `font_conf` WRITE;
/*!40000 ALTER TABLE `font_conf` DISABLE KEYS */;
INSERT INTO `font_conf` VALUES (1,'initial',1),(2,'initial',2),(1,'initial',3),(2,'initial',4),(1,'initial',5),(2,'initial',6),(1,'initial',7),(2,'\"Orbitron\", sans-serif',8),(1,'#333',9),(2,'\"Orbitron\", sans-serif',10),(1,'#333',11),(2,'\"Orbitron\", sans-serif',12),(1,'#333',13),(2,'\"Orbitron\", sans-serif',14),(1,'rgb(0, 0, 0)',18),(1,'rgb(0, 0, 0)',23),(2,'\"Orbitron\", sans-serif',24),(1,'rgb(5, 0, 0)',25),(2,'\"Orbitron\", sans-serif',26),(1,'rgb(44, 33, 22)',33),(2,'\"Orbitron\", sans-serif',34);
/*!40000 ALTER TABLE `font_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `font_family`
--

DROP TABLE IF EXISTS `font_family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `font_family` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CSS_STYLE` varchar(255) NOT NULL,
  `FONT_NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `font_family`
--

LOCK TABLES `font_family` WRITE;
/*!40000 ALTER TABLE `font_family` DISABLE KEYS */;
INSERT INTO `font_family` VALUES (2,'\"Orbitron\", sans-serif','Orbitron'),(3,'\"Times New Roman\", Georgia, Serif','Times New Roman'),(4,'\"Comic Sans MS\", \"Comic Sans\", cursive','Comic Sans MS');
/*!40000 ALTER TABLE `font_family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual`
--

DROP TABLE IF EXISTS `manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NICK` varchar(30) NOT NULL,
  `TITLE` varchar(255) NOT NULL,
  `VISIBILITY` int(2) NOT NULL DEFAULT '0',
  `DATE_CREATION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DATE_LAST_MOD` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CURRENT_STATE` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `USER_NICK` (`USER_NICK`),
  CONSTRAINT `manual_ibfk_1` FOREIGN KEY (`USER_NICK`) REFERENCES `user_testfield` (`USER_NICK`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual`
--

LOCK TABLES `manual` WRITE;
/*!40000 ALTER TABLE `manual` DISABLE KEYS */;
INSERT INTO `manual` VALUES (66,'andriy','<span style=\"font-size: 36px;\">Temporal title</span>',1,'2017-04-08 21:25:09','2017-04-15 18:26:40',0),(67,'andriy','Hai hai hai',1,'2017-04-15 18:35:25','2017-04-17 20:08:20',0),(68,'andriy','Temporal title',1,'2017-04-15 23:10:33','2017-04-15 23:10:33',0),(69,'andriy','League of legends 3.0',0,'2017-04-19 19:45:24','2017-05-04 18:51:46',0),(70,'admin','Temporal title',1,'2017-04-24 18:38:19','2017-04-24 18:38:19',0),(71,'admin','Temporal title',1,'2017-04-24 18:43:25','2017-04-24 18:43:25',0),(72,'admin','Temporal title',1,'2017-04-24 18:47:49','2017-04-24 18:47:49',0),(73,'admin','Temporal title',1,'2017-04-24 18:47:58','2017-04-24 18:47:58',0),(74,'andriy','Nat pls',1,'2017-04-25 19:33:48','2017-04-25 19:34:09',0),(75,'andriy','Temporal title',1,'2017-04-30 13:31:11','2017-04-30 13:31:11',0),(76,'andriy','Temporal title',1,'2017-04-30 13:33:40','2017-04-30 13:33:40',0),(77,'andriy','Temporal title',1,'2017-04-30 13:33:43','2017-04-30 13:33:43',0),(78,'yuna','Hi hoooo Yunaleskina',1,'2017-06-09 17:08:04','2017-06-09 17:11:26',0),(79,'pizza','Temporal title',1,'2017-06-09 17:13:13','2017-06-09 17:13:13',0),(80,'andriy','Temporal title',1,'2017-06-11 12:31:56','2017-06-11 12:31:56',0),(81,'andriy','Temporal title',1,'2017-06-11 12:38:17','2017-06-11 12:38:17',0),(82,'andriy','Temporal title',1,'2017-08-08 19:16:18','2017-08-08 19:16:18',0),(83,'andriy','Hola hola',1,'2017-08-08 19:18:38','2017-08-08 19:18:51',0),(84,'andriy','Rafaeeeeel',1,'2017-08-08 19:18:58','2017-08-08 19:19:05',0);
/*!40000 ALTER TABLE `manual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_block`
--

DROP TABLE IF EXISTS `manual_block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_block` (
  `ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MANUAL_ROW` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BLOCK_ORDER` int(5) NOT NULL,
  `CONTENT` text,
  PRIMARY KEY (`ID`),
  KEY `MANUAL_ROW` (`MANUAL_ROW`),
  CONSTRAINT `manual_block_ibfk_1` FOREIGN KEY (`MANUAL_ROW`) REFERENCES `manual_row` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_block`
--

LOCK TABLES `manual_block` WRITE;
/*!40000 ALTER TABLE `manual_block` DISABLE KEYS */;
INSERT INTO `manual_block` VALUES ('05c83a6f-c207-413f-9c22-699783e28801','65f38818-a11a-434d-aa6a-7c3a138b24d7',1,'Write something new here!'),('05f830d5-8f18-45f6-b23d-40a88ee42e62','520973c1-4623-426d-b85d-a41bbb2bb0aa',2,'New Block'),('08e4d74c-d2d9-47c0-b1a4-db38916b8e87','c7c15095-db75-4167-ae3c-7ae50ced7439',3,'<p>Across matches, players also earn rewards that are applied to their account. Player accounts begin at level one and progress through a maximum level of 30 with experience points earned at the end of every match. As a player progresses they unlock content initially barred to new players. These include \'summoner spells\' - high-impact, long cooldown spells with a specific use. Any champion can choose to equip two or eleven summoner spells before a game - some summoner spells are unique to certain game modes and some have been removed over the game\'s history. Players can also customize Rune pages. Runes grant their champion small, perpetual bonuses to stats, and can be gained through spending \'Influence Points\'(IP) which is granted by playing. IP can be used to unlock both Runes and new champions. Additionally, players can unlock masteries, which grant unique bonuses not necessarily tied to stats, as Runes are. Some masteries, called \'keystones\', are much more powerful than others and only one can be selected per game, with certain champions synergizing more with certain keystones. Player level is separate from character level; both a level 30 account and a level 5 account would begin at character level 1 at the start of a new game.\n</p><p>\n</p><p>Accounts are given rankings based on the Elo rating system, with proprietary adjustments.[16] These ratings are used in automated matchmaking to make games with players of comparable skill level on each team.</p>'),('113de3c6-b912-4693-a94d-5562b17eaf00','2c2b8431-46f8-4c24-aaac-297af5aabce9',1,'League of Legends was generally well received at release, and has grown in popularity, with an active and expansive fanbase. By July 2012, League of Legends was the most played PC game in North America and Europe in terms of the number of hours played.[3] As of January 2014, over 67 million people played League of Legends per month, 27 million per day, and over 7.5 million concurrently during peak hours.[4] League has among the largest footprints of any game in streaming media communities on platforms such as YouTube and Twitch.tv; it routinely ranks first in the most-watched hours.[5][6] In September 2016 the company estimated that there are over 100 million active players each month.[7][8] The game\'s popularity has led it to expand into merchandise, with toys, accessories, apparel, as well as tie-ins to other media through music videos, web series, documentaries, and books.'),('15903520-d703-4c85-9c2f-b3d737d92f30','88deb6cf-9f33-401e-8972-637ac2e43920',1,'<table class=\"table table-bordered\"><tbody><tr><td>asfasasfasf</td><td>wegweg</td><td>sdgsdgsdg</td></tr><tr><td>asfasfasfasfasfasf</td><td>sdgsdg</td><td>sdgsdgsdgv</td></tr></tbody></table><p><br></p>'),('173af07b-a917-4b9b-a703-792a249e2259','51fa712a-d949-4fd3-9787-1391d0940d38',1,'Write something new here!'),('17628d44-7839-4edc-a9e2-71e08d9c059e','7278c413-860e-4827-a9c2-4ece2907e529',3,'New Block'),('20fef4ac-6335-465e-95d6-6b861642b7a8','2c2b8431-46f8-4c24-aaac-297af5aabce9',2,'League of Legends has an active and widespread competitive scene. In North America and Europe, Riot Games organizes the League Championship Series (LCS), located in Los Angeles and Berlin respectively, which consists of 10 professional teams in each continent.[9] Similar regional competitions exist in China (LPL), South Korea (LCK), Taiwan (LMS), Southeast Asia (GPL), and various other regions.[10] These regional competitions culminate with the annual World Championship. The 2016 World Championship had 43 million unique viewers and a total prize pool of over 6 million USD.[11]'),('294bc486-6e86-4a7d-84c5-882e135dd159','cc3f7450-237a-43dc-91e5-32cc88e5e02d',1,'<p class=\"text-center\">Cabrón! Cómo hago para centrar sin un botón!</p><p class=\"text-center\">Me toca meterme en código!</p>'),('2b66b0df-eb26-4026-bd11-4962bc478b78','58af6ac6-9fe9-4000-a1bb-dd84baccf38c',1,'<p style=\"text-align: left;\">Who is there?</p><p><br><div style=\"text-align: center;\"><img src=\"http://79.108.123.27:8090/Testfield/files/manual/gafas2.PNG\" style=\"width: 967px; float: none;\"></div></p>'),('2bea8850-6ae0-4b00-8026-c59df3a2ff43','81a826d0-852e-46e2-85a8-2d7e7c819f0d',3,'<p style=\"margin-top: 0.5em; margin-bottom: 0.5em; line-height: inherit;\">Some objectives are \'neutral\', meaning that they will not attack champions who pass by, but champions can choose to pick a fight with them if they wish to gain a reward at the cost of having to fight for it. They include:\r</p><p style=\"margin-top: 0.5em; margin-bottom: 0.5em; line-height: inherit;\">Jungle monsters - Neutral monsters spawn at various intervals in the Jungle, and provide the player with gold, experience, and sometimes other rewards for killing them. They are the most common neutral objective, and come in many varieties. One summoner spell, called Smite, allows the bearer to instantaneously deal a large amount of true damage to the target minion or monster, and is thus very useful for securing kills on neutral monsters. Two of the most contested neutral monsters are the Red Brambleback (red buff) and the Blue Sentinel (blue buff). If a champion holding either of these buffs is killed, then the buff is transferred to the killer. This can happen any number of times, at least until the buff wears off.\r</p><p style=\"margin-top: 0.5em; margin-bottom: 0.5em; line-height: inherit;\">Elemental drakes/Elder Dragon - Elemental drakes are powerful monsters located in the bottom half of the river. All members of the team that kills the drake are provided with buffs that last the entire game and accrue cumulatively. These buffs differ depending of the type of drake killed. A random elemental drake will respawn six minutes after the previous one is killed. The Elder Dragon spawns instead after 35 minutes have passed in-game. When killed, it provides a stronger buff than an individual elemental drake (and also enhances the buffs of all previous drakes), but it wears off with time, unlike the earlier drake rewards. The drakes are flavored after the Four Elements, with each drake granting a thematically appropriate buff to the team that kills it.\r</p><p style=\"margin-top: 0.5em; margin-bottom: 0.5em; line-height: inherit;\">Rift Herald - The Rift Herald is a powerful enemy located in the upper side of the River. Killing the Rift Herald provides a buff that can be picked up by a member of the killing team which lasts for 20 minutes, persisting even if the holder is killed. This monster will never respawn after it is killed.\r</p><p style=\"margin-top: 0.5em; margin-bottom: 0.5em; line-height: inherit;\">Baron Nashor - Baron Nashor is the most powerful neutral enemy, located in the upper side of the River. It will spawn after twenty minutes, replacing the Rift Herald. All living members of the team that kills Baron Nashor are given a buff which grants massively increased combat stats, an shorter recall time, and causes surrounding minions to become more powerful. Baron Nashor will respawn seven minutes after it is killed.</p>'),('3933c98c-4a45-4cc5-865d-a9e3bf63f464','7278c413-860e-4827-a9c2-4ece2907e529',2,'<p><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/5918398_460s.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/4357322_Furi_Kuri__1.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/289.jpg\"><img style=\"width: 201px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/avatarsei.PNG\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/All-hail-The-King-in-the-North.jpg\"><img style=\"width: 319px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/Avatar.PNG\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/flcl_takun0039.jpg\"><img style=\"width: 240px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/fce8876a3923f70ea5f2277e4fa12995.png\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/facebook_wide_poro_13_9.png\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/furi-kuri.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/Constantine-final.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/smile.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/i-am-not-pretty-i-am-kawaii-a-kawaii-potato.png\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/facebook_profile_poro_13_9.png\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/Sons_of_Big_Boss_by_Zonnex.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/Bjmi8JdCUAAHifi.png\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/tumblr_mjey4539rP1qjebjqo3_1280.jpg\"><img style=\"width: 426px;\" src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_67/wallpaper-105437.jpg\">New Block</p>'),('41832bf5-8b9b-4469-a290-494f4378f9fd','332d70c5-7733-4482-9212-ddf3d0c49579',1,'<table class=\"table table-bordered\"><tbody><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td></tr></tbody></table><p><br></p>'),('46e5398d-e152-4f8e-990a-e7992901b31a','5894806c-5cb3-4414-b805-42cecb3f97be',1,'Write something new here!'),('4a2ba888-6cdb-4e31-a619-e491214dcc72','375a9d90-7870-4163-8105-ddfaca0b4a86',1,'HOOOOOOOOOOOOOOOOOOOOOOLA'),('526e867c-f834-4b47-88af-43a49eaf5bf7','b72835a1-a929-4244-8fa3-f617588ffaf7',1,'New Block'),('5b100573-d7a4-4059-a899-74cefb41bcfc','9e255a15-6679-4db9-8876-6a0f0b1b02f9',1,'<p style=\"text-align: center;\"><img src=\"http://79.108.123.27:8090/Testfield/files/manual/manual_69/wallup-404488.jpg\" style=\"width: 100%;\"><br></p>'),('5dae0e69-201c-4358-bee6-9a7c50cc61a8','077e35b3-2678-4dd5-83fc-7daf436d9cce',2,'<p>La fuente de letra es mazo fea</p>'),('5eb3a8cc-683f-4fb0-844c-26e26eabd2dc','d0e45a9b-346c-4b23-b1a6-f0be0e1cb64b',1,'League of Legends (abbreviated LoL) is a multiplayer online battle arena video game developed and published by Riot Games for Microsoft Windows and macOS. The game follows a freemium model and is supported by microtransactions, and was inspired by the Warcraft III: The Frozen Throne mod, Defense of the Ancients.[123]asfasfasfas<span style=\"background-color: rgb(107, 165, 74);\">asfasfasfasf</span>'),('670d5562-abde-475d-a76c-c9e546078109','d86ba915-d204-4987-8d33-6cb1200d7806',1,'Write something new here!'),('6764d4be-1c90-49f0-b9f3-b7418fd88465','f86b637f-da05-4914-a3df-4a5f9c5fe6aa',1,'New Block'),('6dc7e82d-9d12-4654-8632-0af01441afaf','332d70c5-7733-4482-9212-ddf3d0c49579',2,'<p><br></p><table class=\"table table-bordered\"><tbody><tr><td><b><i>dasfas</i></b></td><td>asfasf</td><td>asfasf</td><td>asfasf</td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr></tbody></table><p><br></p>'),('73f3c8ba-d842-4623-a1fd-8985451c4a51','077e35b3-2678-4dd5-83fc-7daf436d9cce',1,'Prueba de lo que hace esto'),('76209b8e-8a4d-48c6-bffb-7d3befc439fa','81a826d0-852e-46e2-85a8-2d7e7c819f0d',2,'<ul style=\"margin: 0.3em 0px 0px 1.6em; padding: 0px; list-style-image: url(&quot;data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%3F%3E%0A%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20version%3D%221.1%22%20width%3D%225%22%20height%3D%2213%22%3E%0A%3Ccircle%20cx%3D%222.5%22%20cy%3D%229.5%22%20r%3D%222.5%22%20fill%3D%22%2300528c%22%2F%3E%0A%3C%2Fsvg%3E%0A&quot;);\"><li style=\"margin-bottom: 0.1em;\">Turrets - Each lane is guarded by powerful defensive structures called turrets. Turrets deal exceptionally high damage and will attack enemy minions and players that approach them. Turrets prioritize enemy minions in their vicinity, but will immediately attack enemy players if they attack allied players. Thus, by advancing an allied minion wave into the range of a turret, a player can do damage to the structure without themselves being attacked. When destroyed, turrets provide gold and experience. Turrets that are destroyed are destroyed permanently for that match and will not respawn. Some turrets, depending on location, will regenerate health over time if they are damaged but not destroyed.\r</li><li style=\"margin-bottom: 0.1em;\">Inhibitor - Each lane contains one Inhibitor. Inhibitors may be attacked after a team has destroyed the three turrets guarding each lane. Destroying an Inhibitor will cause the allied Nexus to spawn Super Minions, more powerful Minions that provide a buff to surrounding Minions and are very hard to kill. If destroyed, Inhibitors will respawn after five minutes.\r</li><li style=\"margin-bottom: 0.1em;\">Nexus - Each team has a Nexus that can only be damaged once all the turrets in a lane and that lane\'s inhibitor is destroyed. Destruction of the enemy\'s team Nexus ends the game.</li></ul>'),('7874d387-6446-4715-adf8-2ae8c965520f','520973c1-4623-426d-b85d-a41bbb2bb0aa',4,'New Block'),('7cceba5d-e36b-45df-a0eb-91cc79d0b926','520973c1-4623-426d-b85d-a41bbb2bb0aa',5,'New Block'),('7cdb6318-5700-4726-82f3-d5c152c2901c','8264516f-b7c3-45ff-898b-67e5f47727de',2,'Write something new here!'),('7d0c86ad-0f0c-4ec1-a594-ee77ee2f6caa','9007af97-1868-4ff4-8b18-33b20b9fd32d',1,'Write something new here!'),('7e2263ad-3b4f-46ae-9283-7108c3e0c5bc','332d70c5-7733-4482-9212-ddf3d0c49579',3,'New Block'),('826e81c3-7060-4db0-b2c6-79bfc1b2c73f','36c73a10-204d-4139-b213-cc2004b88006',1,'<div style=\"text-align: justify;\">Write something new here!</div>'),('858cd75d-2365-42c7-98bf-92574dba5986','53ba7046-61f0-49de-948b-6fe030dc0318',1,'Write something new here!'),('898553cb-bf2f-4b72-ad2b-9f980614bf03','d0e45a9b-346c-4b23-b1a6-f0be0e1cb64b',2,'In League of Legends, players assume the role of an unseen \"summoner\" that controls a \"champion\" with unique abilities and battle against a team of other players or computer-controlled champions. The goal is usually to destroy the opposing team\'s \"nexus\", a structure which lies at the heart of a base protected by defensive structures, although other distinct game modes exist as well. Each League of Legends match is discrete, with all champions starting off fairly weak but increasing in strength by accumulating items and experience over the course of the game.[2] The champions and setting blend a variety of elements, including high fantasy, steampunk, folklore, and Lovecraftian horror.dfhdfh'),('8a6df8f2-fb32-4caa-a089-dd77d2c5f7a0','ff6071fd-fe80-4875-b47e-c1b257997c0a',1,'Yuna yunaleskina yuna Bijuu DAMAAAA'),('8af3f1bb-81a7-4eba-86d9-f728ea192908','520973c1-4623-426d-b85d-a41bbb2bb0aa',1,'Write something new here!'),('95fa758b-5db7-4391-a1b9-0d2e3fa314dc','4cf7ec29-375a-47d4-9592-cba9c7d3d67f',1,'<h3 style=\"background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; margin: 0.3em 0px 0px; overflow: hidden; padding-top: 0.5em; padding-bottom: 0px; border-bottom: 0px; line-height: 1.6;\">Game maps\r</h3><div>League of Legends consists of three main maps, or \"Fields of Justice.\" Each have different terrain, objectives and victory conditions, as well as varied summoner spells and items. A fourth map, the Crystal Scar, was discontinued.</div>'),('ba4d5f1e-9b01-4193-9aff-bffbff6c3447','efe71ede-6af3-4813-acae-e97d9d0b3fa9',1,'<p>Powered by the super duper app <b>TESTFIELD</b>&nbsp;</p><p>Created by <b>SeiRiuS</b></p><p style=\"text-align: center; \"><img src=\"http://79.108.123.27:8090/Testfield/files/manual/IMG_20140131_221623.jpg\" style=\"width: 25%;\"><br></p>'),('bb049969-66e8-4a8d-9b6e-bd750b5c6d2a','81a826d0-852e-46e2-85a8-2d7e7c819f0d',1,'<h4 style=\"background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; margin: 0.3em 0px 0px; overflow: hidden; padding-top: 0.5em; padding-bottom: 0px; border-bottom: 0px; line-height: 1.6;\">Summoner\'s Rift\r</h4><div>Summoner\'s Rift is the most popular map in League of Legends.[13] On this map type, two teams of five players compete to destroy an enemy building called a Nexus, which is guarded by the enemy team and a number of defensive structures called turrets, or towers.[17] One nexus is located in each enemy base on opposite sides of the map, in the lower-left and upper-right hand corners. These structures continually create weak non-player characters known as minions, which advance toward the enemy base along three lanes: top, middle (mid), and bottom (bot) lanes. Players compete to advance these waves of minions into the enemy base, which allows them to destroy enemy structures and ultimately win the match. Between lanes are neutral areas of the map known as the \'jungle\', arrayed in four quadrants. Uniquely dangerous monsters populate the jungle and grant bonuses to their killer. The jungle is also home to three types of plants that can aid champions in different ways. A shallow river divides the map between the teams, but doesn\'t actually impede movement; all champions can wade through it no differently than dry land.\r</div><div>Each team wishes to defend their own structures and destroy the other team\'s structures. These include:[18]</div>'),('bc5d43ea-3ead-40f5-9bcb-d1d56ff7228c','c7c15095-db75-4167-ae3c-7ae50ced7439',2,'League of Legends is a 3D, third-person multiplayer online battle arena (MOBA) game.[12] The game consists of 3 current running game modes: Summoner\'s Rift, Twisted Treeline, and Howling Abyss.[13] Another game mode, The Crystal Scar, has since been removed.[14] Players compete in matches, lasting anywhere from 20 to 60 minutes. In each game mode teams work together to achieve a victory condition, typically destroying the core building (called the Nexus) in the enemy team\'s base after bypassing a line of defensive structures called turrets, or towers.'),('c0c9ef57-c2f0-4276-8ae8-10e9a4cc199d','fa983367-0703-46e2-b22e-c9a40b80b245',1,'Write something new here!'),('c376a9e5-ebc3-4d7e-85b7-f3b0e7bbc741','079d6cc8-fd15-477e-a5d1-51939082cc94',1,'Write something new here!'),('c72606d9-5cb0-4442-8ceb-1e0b2395d05a','c7c15095-db75-4167-ae3c-7ae50ced7439',4,'In all game modes, players control characters called champions, chosen or assigned every match, who each have a set of unique abilities that determine their playstyle - one passive, or innate, ability that cannot be activated and thus gives a perpetual bonus or effect, three normal, or \'basic\', abilities, and a powerful \'ultimate\' ability that can only be unlocked once the character reaches level 6. Ultimate abilities are vastly more powerful than regular abilities and thus have much longer cooldowns (period of time before they can be used again).[15] A champion\'s full set of abilities is referred to as its \'kit\'. The use of champions\' abilities is limited by cooldowns and a resource (usually some form of mana or energy). If a champion runs out of their resource, they cannot cast spells, even if they are off cooldown, and must wait for it to regenerate. Some champions do not have a resource, being limited only by cooldowns, and others have ways of restoring their respective resource. Each champion also has an \'auto\' or \'basic attack\' in which they deal damage to the target unit within range simply by right-clicking them, with no cost - some champions are melee and have to be closer to use their basic attack, while others are ranged, although to compensate melee champions are usually more durable. The rate at which a champion can basic attack is determined by their attack speed, a stat that can be improved through items. Some champions additionally use ammo and must reload after enacting a certain number of basic attacks. Champions begin every match at level one, and then gain experience over the course of the match to achieve a maximum level of 18. Gaining champion levels in matches allows players to unlock their champion\'s special abilities and augment them in a number of ways unique to each character. If a champion loses all their health, they are defeated, but are automatically revived in their base after a \'respawn timer\' ends - the timer increases in duration as the game goes on. Players also begin each match with a low amount of gold, and can earn additional gold throughout the match in a variety of ways: by killing non-player characters known as minions and monsters; by killing or helping to kill enemy players; by destroying enemy structures; passively over time; and through unique item interactions or champion abilities. This gold can then be spent throughout the match to buy in-game items that further augment each champion\'s abilities and game play in a variety of ways. Champion experience, gold earned, and items bought are specific to each match and do not carry over to subsequent matches. Thus, all players begin each match on more-or-less equal footing relative to their opposing team.'),('cbc227aa-53ed-4c67-a05d-f9a9e21cfdc7','8b306d7d-1d9d-4098-be24-92eaeae96f21',1,'Write something new here!'),('df50d472-2961-4c69-b4de-6593f6e729e1','1f9efad9-516f-435f-98b3-e8e8c70a30ca',1,'Write something new here!'),('ebc6a4a4-5799-4254-8dd0-559e3a25a86a','8264516f-b7c3-45ff-898b-67e5f47727de',1,'New Block'),('f0111c25-b342-4f78-9f8f-e1149987eed1','cc3f7450-237a-43dc-91e5-32cc88e5e02d',2,'Estaá super guay!'),('f33944e8-c82a-4e81-af84-d19f46f991fa','520973c1-4623-426d-b85d-a41bbb2bb0aa',3,'New Block'),('f367ed83-7c35-41ff-b80d-5aabe500f60d','077e35b3-2678-4dd5-83fc-7daf436d9cce',3,'<p>muahahahaha</p>');
/*!40000 ALTER TABLE `manual_block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_conf`
--

DROP TABLE IF EXISTS `manual_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_conf` (
  `manual` int(11) NOT NULL,
  `manual_background` varchar(255) NOT NULL DEFAULT 'none',
  `font_color` int(11) NOT NULL,
  `font_family` int(11) NOT NULL,
  PRIMARY KEY (`manual`),
  KEY `manual_conf_family_idx` (`font_family`),
  KEY `manual_conf_color_idx` (`font_color`),
  CONSTRAINT `manual_conf_color` FOREIGN KEY (`font_color`) REFERENCES `font_color` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `manual_conf_family` FOREIGN KEY (`font_family`) REFERENCES `font_family` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `manual_conf_ibfk_1` FOREIGN KEY (`manual`) REFERENCES `manual` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_conf`
--

LOCK TABLES `manual_conf` WRITE;
/*!40000 ALTER TABLE `manual_conf` DISABLE KEYS */;
INSERT INTO `manual_conf` VALUES (66,'none',1,2),(67,'none',2,2),(68,'none',3,2),(69,'none',4,4),(70,'none',5,2),(71,'none',6,2),(72,'none',7,2),(73,'none',8,2),(74,'none',9,2),(75,'none',10,2),(76,'none',11,2),(77,'none',12,2),(78,'none',13,4),(79,'none',14,4),(80,'none',15,2),(81,'none',16,2),(82,'none',17,2),(83,'none',18,2),(84,'none',19,2);
/*!40000 ALTER TABLE `manual_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_page`
--

DROP TABLE IF EXISTS `manual_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_page` (
  `ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MANUAL` int(11) NOT NULL,
  `PAGE_ORDER` int(5) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MANUAL` (`MANUAL`),
  CONSTRAINT `manual_page_ibfk_1` FOREIGN KEY (`MANUAL`) REFERENCES `manual` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_page`
--

LOCK TABLES `manual_page` WRITE;
/*!40000 ALTER TABLE `manual_page` DISABLE KEYS */;
INSERT INTO `manual_page` VALUES ('0665da02-e45d-4dc5-82c3-81798459ae9d',77,1),('1153eb52-5ae5-475b-a250-5d6e1d93e0df',78,1),('2c3a8781-4635-4f2d-95d3-602c68f66255',69,3),('319ba7b9-ed5c-42d0-bec0-f50583144344',82,1),('3297a84d-d7d0-4e39-8cb5-0bf3de5552c3',84,1),('3bd02351-066a-4278-9dd1-e66c7ec21cc7',67,1),('55b04450-fc5d-4e37-a4a3-c2644f8a779d',66,1),('5c768ec2-414a-4d90-9e22-b77b61b656fe',80,1),('63237e2c-aa90-4210-b966-b8e4a8904f2c',70,1),('8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',69,1),('914b4e67-d840-4399-8eb1-a6343d10a75b',76,1),('957be799-3bb4-47e7-b7b3-568390602fba',81,1),('aa8d1cff-6595-453e-8913-81ef870de2b4',83,1),('b0f9c54f-e8b8-48ee-8c1b-0dfd230f14fd',73,1),('b7bdf6f1-5055-43f2-8814-16a81ba71ea2',71,1),('ba311ef6-1f07-40ac-8cbb-77f0aac8a46e',69,4),('c85116b4-0f91-4452-84a9-1a14a9567f77',69,2),('cac42984-19fa-4327-b10f-66c6b351ab62',72,1),('d5fb23f3-0532-40e2-94ec-0e1eed9b5277',66,2),('e5e4e722-6962-44ef-b124-7396c4ae139f',74,1),('e673f258-3fab-457e-83c2-63c2f7081265',68,1),('f5c79379-49fb-48a8-9199-adf82b5dbf07',79,1),('fb612edb-6b10-40d7-ac56-6d79c28ff9b9',75,1);
/*!40000 ALTER TABLE `manual_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_row`
--

DROP TABLE IF EXISTS `manual_row`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_row` (
  `ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MANUAL_PAGE` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ROW_ORDER` int(5) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MANUAL_PAGE` (`MANUAL_PAGE`),
  CONSTRAINT `manual_row_ibfk_1` FOREIGN KEY (`MANUAL_PAGE`) REFERENCES `manual_page` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_row`
--

LOCK TABLES `manual_row` WRITE;
/*!40000 ALTER TABLE `manual_row` DISABLE KEYS */;
INSERT INTO `manual_row` VALUES ('077e35b3-2678-4dd5-83fc-7daf436d9cce','63237e2c-aa90-4210-b966-b8e4a8904f2c',1),('079d6cc8-fd15-477e-a5d1-51939082cc94','fb612edb-6b10-40d7-ac56-6d79c28ff9b9',1),('1f9efad9-516f-435f-98b3-e8e8c70a30ca','957be799-3bb4-47e7-b7b3-568390602fba',1),('22e1f1ee-cfee-4284-8078-57d0db9b4dd3','63237e2c-aa90-4210-b966-b8e4a8904f2c',3),('27089c9f-389f-481c-b306-407d3171c636','e673f258-3fab-457e-83c2-63c2f7081265',1),('2c2b8431-46f8-4c24-aaac-297af5aabce9','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',2),('332d70c5-7733-4482-9212-ddf3d0c49579','3bd02351-066a-4278-9dd1-e66c7ec21cc7',1),('36c73a10-204d-4139-b213-cc2004b88006','5c768ec2-414a-4d90-9e22-b77b61b656fe',1),('375a9d90-7870-4163-8105-ddfaca0b4a86','d5fb23f3-0532-40e2-94ec-0e1eed9b5277',1),('4cf7ec29-375a-47d4-9592-cba9c7d3d67f','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',5),('51fa712a-d949-4fd3-9787-1391d0940d38','319ba7b9-ed5c-42d0-bec0-f50583144344',1),('520973c1-4623-426d-b85d-a41bbb2bb0aa','f5c79379-49fb-48a8-9199-adf82b5dbf07',1),('53ba7046-61f0-49de-948b-6fe030dc0318','aa8d1cff-6595-453e-8913-81ef870de2b4',1),('5894806c-5cb3-4414-b805-42cecb3f97be','0665da02-e45d-4dc5-82c3-81798459ae9d',1),('58af6ac6-9fe9-4000-a1bb-dd84baccf38c','e5e4e722-6962-44ef-b124-7396c4ae139f',1),('65f38818-a11a-434d-aa6a-7c3a138b24d7','3297a84d-d7d0-4e39-8cb5-0bf3de5552c3',1),('7278c413-860e-4827-a9c2-4ece2907e529','3bd02351-066a-4278-9dd1-e66c7ec21cc7',2),('81a826d0-852e-46e2-85a8-2d7e7c819f0d','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',6),('8264516f-b7c3-45ff-898b-67e5f47727de','e673f258-3fab-457e-83c2-63c2f7081265',1),('88deb6cf-9f33-401e-8972-637ac2e43920','55b04450-fc5d-4e37-a4a3-c2644f8a779d',1),('8b306d7d-1d9d-4098-be24-92eaeae96f21','b0f9c54f-e8b8-48ee-8c1b-0dfd230f14fd',1),('9007af97-1868-4ff4-8b18-33b20b9fd32d','914b4e67-d840-4399-8eb1-a6343d10a75b',1),('9e255a15-6679-4db9-8876-6a0f0b1b02f9','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',3),('b72835a1-a929-4244-8fa3-f617588ffaf7','c85116b4-0f91-4452-84a9-1a14a9567f77',1),('c7c15095-db75-4167-ae3c-7ae50ced7439','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',4),('cc3f7450-237a-43dc-91e5-32cc88e5e02d','63237e2c-aa90-4210-b966-b8e4a8904f2c',2),('d0e45a9b-346c-4b23-b1a6-f0be0e1cb64b','8ce9ba76-480f-47c9-b561-68d8ce2f9dd9',1),('d86ba915-d204-4987-8d33-6cb1200d7806','b7bdf6f1-5055-43f2-8814-16a81ba71ea2',1),('efe71ede-6af3-4813-acae-e97d9d0b3fa9','e5e4e722-6962-44ef-b124-7396c4ae139f',2),('f86b637f-da05-4914-a3df-4a5f9c5fe6aa','2c3a8781-4635-4f2d-95d3-602c68f66255',1),('fa983367-0703-46e2-b22e-c9a40b80b245','cac42984-19fa-4327-b10f-66c6b351ab62',1),('ff6071fd-fe80-4875-b47e-c1b257997c0a','1153eb52-5ae5-475b-a250-5d6e1d93e0df',1);
/*!40000 ALTER TABLE `manual_row` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_block_width_type`
--

DROP TABLE IF EXISTS `rel_block_width_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_block_width_type` (
  `WIDTH_TYPE` int(2) NOT NULL,
  `MANUAL_BLOCK` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `AMOUNT` int(4) DEFAULT NULL,
  PRIMARY KEY (`WIDTH_TYPE`,`MANUAL_BLOCK`),
  KEY `MANUAL_BLOCK` (`MANUAL_BLOCK`),
  CONSTRAINT `rel_block_width_type_ibfk_1` FOREIGN KEY (`WIDTH_TYPE`) REFERENCES `width_type` (`WIDTH_TYPE`),
  CONSTRAINT `rel_block_width_type_ibfk_2` FOREIGN KEY (`MANUAL_BLOCK`) REFERENCES `manual_block` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_block_width_type`
--

LOCK TABLES `rel_block_width_type` WRITE;
/*!40000 ALTER TABLE `rel_block_width_type` DISABLE KEYS */;
INSERT INTO `rel_block_width_type` VALUES (1,'05c83a6f-c207-413f-9c22-699783e28801',12),(1,'05f830d5-8f18-45f6-b23d-40a88ee42e62',6),(1,'08e4d74c-d2d9-47c0-b1a4-db38916b8e87',12),(1,'173af07b-a917-4b9b-a703-792a249e2259',12),(1,'17628d44-7839-4edc-a9e2-71e08d9c059e',12),(1,'20fef4ac-6335-465e-95d6-6b861642b7a8',12),(1,'2bea8850-6ae0-4b00-8026-c59df3a2ff43',12),(1,'3933c98c-4a45-4cc5-865d-a9e3bf63f464',12),(1,'41832bf5-8b9b-4469-a290-494f4378f9fd',12),(1,'4a2ba888-6cdb-4e31-a619-e491214dcc72',12),(1,'526e867c-f834-4b47-88af-43a49eaf5bf7',12),(1,'5b100573-d7a4-4059-a899-74cefb41bcfc',12),(1,'5eb3a8cc-683f-4fb0-844c-26e26eabd2dc',6),(1,'6764d4be-1c90-49f0-b9f3-b7418fd88465',12),(1,'6dc7e82d-9d12-4654-8632-0af01441afaf',12),(1,'76209b8e-8a4d-48c6-bffb-7d3befc439fa',12),(1,'7874d387-6446-4715-adf8-2ae8c965520f',8),(1,'7cceba5d-e36b-45df-a0eb-91cc79d0b926',4),(1,'7e2263ad-3b4f-46ae-9283-7108c3e0c5bc',12),(1,'858cd75d-2365-42c7-98bf-92574dba5986',12),(1,'898553cb-bf2f-4b72-ad2b-9f980614bf03',12),(1,'95fa758b-5db7-4391-a1b9-0d2e3fa314dc',12),(1,'ba4d5f1e-9b01-4193-9aff-bffbff6c3447',12),(1,'bb049969-66e8-4a8d-9b6e-bd750b5c6d2a',12),(1,'bc5d43ea-3ead-40f5-9bcb-d1d56ff7228c',12),(1,'c72606d9-5cb0-4442-8ceb-1e0b2395d05a',12),(1,'ebc6a4a4-5799-4254-8dd0-559e3a25a86a',12),(1,'f33944e8-c82a-4e81-af84-d19f46f991fa',6),(2,'05f830d5-8f18-45f6-b23d-40a88ee42e62',2),(2,'08e4d74c-d2d9-47c0-b1a4-db38916b8e87',12),(2,'17628d44-7839-4edc-a9e2-71e08d9c059e',6),(2,'2bea8850-6ae0-4b00-8026-c59df3a2ff43',12),(2,'3933c98c-4a45-4cc5-865d-a9e3bf63f464',6),(2,'41832bf5-8b9b-4469-a290-494f4378f9fd',6),(2,'5b100573-d7a4-4059-a899-74cefb41bcfc',12),(2,'5eb3a8cc-683f-4fb0-844c-26e26eabd2dc',12),(2,'6dc7e82d-9d12-4654-8632-0af01441afaf',6),(2,'76209b8e-8a4d-48c6-bffb-7d3befc439fa',12),(2,'7e2263ad-3b4f-46ae-9283-7108c3e0c5bc',6),(2,'898553cb-bf2f-4b72-ad2b-9f980614bf03',12),(2,'95fa758b-5db7-4391-a1b9-0d2e3fa314dc',12),(2,'ba4d5f1e-9b01-4193-9aff-bffbff6c3447',12),(2,'bb049969-66e8-4a8d-9b6e-bd750b5c6d2a',12),(2,'bc5d43ea-3ead-40f5-9bcb-d1d56ff7228c',12),(2,'c72606d9-5cb0-4442-8ceb-1e0b2395d05a',12),(3,'05f830d5-8f18-45f6-b23d-40a88ee42e62',12),(3,'08e4d74c-d2d9-47c0-b1a4-db38916b8e87',12),(3,'15903520-d703-4c85-9c2f-b3d737d92f30',12),(3,'294bc486-6e86-4a7d-84c5-882e135dd159',12),(3,'2b66b0df-eb26-4026-bd11-4962bc478b78',12),(3,'2bea8850-6ae0-4b00-8026-c59df3a2ff43',6),(3,'46e5398d-e152-4f8e-990a-e7992901b31a',12),(3,'5dae0e69-201c-4358-bee6-9a7c50cc61a8',6),(3,'5eb3a8cc-683f-4fb0-844c-26e26eabd2dc',12),(3,'670d5562-abde-475d-a76c-c9e546078109',12),(3,'73f3c8ba-d842-4623-a1fd-8985451c4a51',4),(3,'76209b8e-8a4d-48c6-bffb-7d3befc439fa',6),(3,'7cdb6318-5700-4726-82f3-d5c152c2901c',12),(3,'7d0c86ad-0f0c-4ec1-a594-ee77ee2f6caa',12),(3,'826e81c3-7060-4db0-b2c6-79bfc1b2c73f',12),(3,'8a6df8f2-fb32-4caa-a089-dd77d2c5f7a0',12),(3,'8af3f1bb-81a7-4eba-86d9-f728ea192908',12),(3,'c0c9ef57-c2f0-4276-8ae8-10e9a4cc199d',12),(3,'c376a9e5-ebc3-4d7e-85b7-f3b0e7bbc741',12),(3,'c72606d9-5cb0-4442-8ceb-1e0b2395d05a',2),(3,'cbc227aa-53ed-4c67-a05d-f9a9e21cfdc7',12),(3,'df50d472-2961-4c69-b4de-6593f6e729e1',12),(3,'f0111c25-b342-4f78-9f8f-e1149987eed1',12),(3,'f367ed83-7c35-41ff-b80d-5aabe500f60d',2),(4,'05f830d5-8f18-45f6-b23d-40a88ee42e62',12),(4,'08e4d74c-d2d9-47c0-b1a4-db38916b8e87',12),(4,'113de3c6-b912-4693-a94d-5562b17eaf00',12),(4,'20fef4ac-6335-465e-95d6-6b861642b7a8',12),(4,'2bea8850-6ae0-4b00-8026-c59df3a2ff43',12),(4,'5eb3a8cc-683f-4fb0-844c-26e26eabd2dc',12),(4,'76209b8e-8a4d-48c6-bffb-7d3befc439fa',12),(4,'898553cb-bf2f-4b72-ad2b-9f980614bf03',12),(4,'c72606d9-5cb0-4442-8ceb-1e0b2395d05a',12);
/*!40000 ALTER TABLE `rel_block_width_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_tag_manual`
--

DROP TABLE IF EXISTS `rel_tag_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_tag_manual` (
  `TAG` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MANUAL` int(11) NOT NULL,
  PRIMARY KEY (`TAG`,`MANUAL`),
  KEY `MANUAL` (`MANUAL`),
  CONSTRAINT `rel_tag_manual_ibfk_1` FOREIGN KEY (`TAG`) REFERENCES `tag` (`ID`),
  CONSTRAINT `rel_tag_manual_ibfk_2` FOREIGN KEY (`MANUAL`) REFERENCES `manual` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_tag_manual`
--

LOCK TABLES `rel_tag_manual` WRITE;
/*!40000 ALTER TABLE `rel_tag_manual` DISABLE KEYS */;
/*!40000 ALTER TABLE `rel_tag_manual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `ID` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_conf`
--

DROP TABLE IF EXISTS `user_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_conf` (
  `user_nick` varchar(30) NOT NULL,
  PRIMARY KEY (`user_nick`),
  CONSTRAINT `user_conf_ibfk_1` FOREIGN KEY (`user_nick`) REFERENCES `user_testfield` (`USER_NICK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_conf`
--

LOCK TABLES `user_conf` WRITE;
/*!40000 ALTER TABLE `user_conf` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `USER_NICK` varchar(30) NOT NULL,
  `EMAIL` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`USER_NICK`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`USER_NICK`) REFERENCES `user_testfield` (`USER_NICK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('admin','rabenter@gmail.com'),('andriy','andsei91@gmail.com'),('formBuilder','formBuilder'),('infinity','infinity'),('lannister','lannister'),('meteos','meteos'),('pizza','pizza'),('Test','andsei92@gmail.com'),('yuna','yunayuna');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_testfield`
--

DROP TABLE IF EXISTS `user_testfield`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_testfield` (
  `USER_NICK` varchar(30) NOT NULL,
  `PASSWORD` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DATE_CREATION` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_NICK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_testfield`
--

LOCK TABLES `user_testfield` WRITE;
/*!40000 ALTER TABLE `user_testfield` DISABLE KEYS */;
INSERT INTO `user_testfield` VALUES ('admin','eagle','2017-04-24 20:37:40'),('andriy','andriy','2016-12-04 16:26:49'),('formBuilder','formBuilder','2017-06-04 16:13:15'),('infinity','infinity','2017-06-09 19:02:48'),('lannister','lannister','2017-06-10 11:13:42'),('meteos','meteos','2017-06-10 01:40:12'),('pizza','pizza','2017-06-09 19:12:51'),('Test','testtest','2017-01-13 07:30:43'),('yuna','yunayuna','2017-06-09 19:07:47');
/*!40000 ALTER TABLE `user_testfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `width_type`
--

DROP TABLE IF EXISTS `width_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `width_type` (
  `WIDTH_TYPE` int(2) NOT NULL,
  `DESCRIPTION` varchar(60) NOT NULL,
  PRIMARY KEY (`WIDTH_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `width_type`
--

LOCK TABLES `width_type` WRITE;
/*!40000 ALTER TABLE `width_type` DISABLE KEYS */;
INSERT INTO `width_type` VALUES (1,'Phones'),(2,'Tablets'),(3,'Default'),(4,'Large display');
/*!40000 ALTER TABLE `width_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-08 22:03:04
