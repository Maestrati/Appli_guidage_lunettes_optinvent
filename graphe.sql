-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 25, 2018 at 03:20 PM
-- Server version: 5.6.34-log
-- PHP Version: 7.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `graphe`
--

-- --------------------------------------------------------

--
-- Table structure for table `lien`
--

CREATE TABLE `lien` (
  `idLien` int(11) NOT NULL,
  `noeudDepart` int(11) NOT NULL,
  `noeudArrivee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_roman_ci;

--
-- Dumping data for table `lien`
--

INSERT INTO `lien` (`idLien`, `noeudDepart`, `noeudArrivee`) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 1, 3),
(4, 3, 1),
(5, 2, 3),
(6, 3, 2),
(7, 2, 4),
(8, 4, 2),
(9, 4, 6),
(10, 6, 4),
(11, 3, 5),
(12, 5, 3),
(13, 3, 7),
(14, 7, 3),
(15, 5, 7),
(16, 7, 5),
(17, 7, 8),
(18, 8, 7),
(19, 7, 9),
(20, 9, 7),
(21, 9, 21),
(22, 21, 9),
(23, 21, 20),
(24, 20, 21),
(25, 19, 20),
(26, 20, 19),
(27, 19, 17),
(28, 17, 19),
(29, 17, 16),
(30, 16, 17),
(31, 16, 18),
(32, 18, 16),
(33, 18, 20),
(34, 20, 18),
(35, 16, 15),
(36, 15, 16),
(37, 15, 14),
(38, 14, 15),
(39, 14, 13),
(40, 13, 14),
(41, 13, 12),
(42, 12, 13),
(43, 13, 10),
(44, 10, 13),
(45, 13, 11),
(46, 11, 13),
(47, 10, 11),
(48, 11, 10),
(49, 21, 22),
(50, 22, 21),
(51, 22, 25),
(52, 25, 22),
(53, 22, 23),
(54, 23, 22),
(55, 23, 24),
(56, 24, 23),
(57, 24, 26),
(58, 26, 24),
(59, 25, 26),
(60, 26, 25);

-- --------------------------------------------------------

--
-- Table structure for table `noeud`
--

CREATE TABLE `noeud` (
  `idNoeud` int(11) NOT NULL,
  `lieu` text COLLATE utf8_roman_ci NOT NULL,
  `Longitude` double NOT NULL,
  `Latitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_roman_ci;

--
-- Dumping data for table `noeud`
--

INSERT INTO `noeud` (`idNoeud`, `lieu`, `Longitude`, `Latitude`) VALUES
(1, 'Métro 4 Cantons', -3.138981, 50.605356),
(2, 'Rond Point 4 Cantons (1)', -3.138337, 50.605895),
(3, 'Rond Point 4 Cantons (2)', -3.137942, 50.605711),
(4, 'Grillage Parking Administration', -3.138323, 50.60644),
(5, 'Entrée Principale', -3.13712, 50.605998),
(6, 'Entrée Administration', -3.137654, 50.605865),
(7, 'Avenue Paul Langevin (1)', -3.136749, 50.605865),
(8, 'Restaurant Universitaire Le Sully', -3.136598, 50.605559),
(9, 'Avenue Paul Langevin (2)', -3.135191, 50.606321),
(10, 'Entrée Cour (1)', -3.137072, 50.606321),
(11, 'Entrée Cour (2)', -3.137259, 50.606654),
(12, 'Entrée Bâtiment C', -3.136696, 50.606229),
(13, 'Entrée Bâtiment E', -3.136756, 50.606449),
(14, 'Entrée Laboratoire de Mécanique des Fluides', -3.13625, 50.60658),
(15, 'Entrée Laboratoire de Mécanique', -3.135893, 50.606668),
(16, 'Grillage', -3.135443, 50.606702),
(17, 'Entrée Livraison', -3.135617, 50.607083),
(18, 'Point Intermédiaire Parking Fonderie', -3.135144, 50.606651),
(19, 'Entrée Fonderie', -3.135206, 50.60713),
(20, 'Grillage Parking Fonderie', -3.134755, 50.607118),
(21, 'Avenue Paul Langevin (3)', -3.134408, 50.607095),
(22, 'Entrée Bâtiment D', -3.134222, 50.607366),
(23, 'Avenue Paul Langevin (4)/ Rue Elisée Reclus (1)', -3.134254, 50.607924),
(24, 'Rue Elisée Reclus (2)', -3.133472, 50.607954),
(25, 'Parking B7', -3.133472, 50.607421),
(26, 'Entrée B7', -3.133079, 50.607643);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lien`
--
ALTER TABLE `lien`
  ADD PRIMARY KEY (`idLien`);

--
-- Indexes for table `noeud`
--
ALTER TABLE `noeud`
  ADD PRIMARY KEY (`idNoeud`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
