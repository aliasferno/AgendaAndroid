-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-03-2024 a las 00:40:39
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_agenda24`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE `contacto` (
  `cod_contacto` int(11) NOT NULL,
  `nom_contacto` varchar(200) NOT NULL,
  `ape_contacto` varchar(200) NOT NULL,
  `telefono_contacto` varchar(45) DEFAULT NULL,
  `telefono_contacto2` varchar(45) DEFAULT NULL,
  `email_contacto` varchar(45) DEFAULT NULL,
  `persona_cod_persona` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `contacto`
--

INSERT INTO `contacto` (`cod_contacto`, `nom_contacto`, `ape_contacto`, `telefono_contacto`, `telefono_contacto2`, `email_contacto`, `persona_cod_persona`) VALUES
(3, 'Oscar', 'Lindo', '099978229', '0', 'lindooscar635@gmail.com', 1),
(4, 'Athelstan', 'First Of England', '09995607656', '0', 'athelstan@gamil.com', 1),
(5, 'Bjorn', 'Ironside', '0995390885', '0', 'bjorn@gmail.com', 1),
(6, 'Winston ', 'Churchill', '0995667418', '0', 'winston@gmail.com', 1),
(7, 'Akira', 'Toritama', '09953907711', '0', 'akira@gmail.com', 1),
(8, 'Thorfinn', 'Ragnarsson', '09955566129', '0', 'thorfinn@gmail.com', 1),
(10, 'Maria', 'Mercedes', '099978228', '0992347456', '', 6),
(11, 'Henry', 'Tudor', '099978222', '0992347455', 'henry@gmail.com', 6),
(12, 'Henry', 'Tudor', '099978223', '0992347459', 'henry@outlook.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `cod_persona` int(11) NOT NULL,
  `ci_persona` varchar(12) NOT NULL,
  `nom_persona` varchar(200) NOT NULL,
  `ape_persona` varchar(200) NOT NULL,
  `clave_persona` varchar(45) NOT NULL,
  `correo_persona` varchar(100) NOT NULL,
  `intentos_fallidos` int(11) DEFAULT 0,
  `bloqueada` tinyint(4) DEFAULT 0,
  `fecha_nacimiento` year(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`cod_persona`, `ci_persona`, `nom_persona`, `ape_persona`, `clave_persona`, `correo_persona`, `intentos_fallidos`, `bloqueada`, `fecha_nacimiento`) VALUES
(1, '1805450374', 'Oscar ', 'Lindo', '1889', 'lindooscar635@gmail.com', 0, 0, '1997'),
(3, '1805450382', 'Ivar', 'Ragnarsson', '4567', 'ivar@gmail.com', 0, 0, '1987'),
(4, '1805450344', 'Ragnar', 'Lothbrok', '1990', 'ragnar@gmail.com', 0, 0, '1990'),
(6, '1234567890', 'Torvi', 'Reinarsson', '1990', 'torvi@gmail.com', 0, 0, '1995'),
(35, '123456678', 'Bjorn', 'Ragnarsson', '1880', 'bjorn@gmail.com', 0, 0, '1999'),
(36, '123455666', 'Elizabeth', 'Windsor', '1988', 'elizabeth@gmail.com', 0, 0, '1999'),
(37, '109292822', 'Mary', 'Of Scotland', '1990', 'mary@gmail.com', 0, 0, '1998'),
(38, '127373737', 'William', 'Wallace', '1991', 'will@gmail.com', 0, 0, '1992'),
(39, '1938383', 'Erik', 'El Rojo', '1993', 'erik@gmail.com', 0, 0, '1993'),
(45, '17363553', 'Nicolas', 'Flamel', '1996', 'nicolas@gmail.com', 0, 0, '1997');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD PRIMARY KEY (`cod_contacto`),
  ADD KEY `fk_contacto_persona_idx` (`persona_cod_persona`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`cod_persona`),
  ADD UNIQUE KEY `ci_persona_UNIQUE` (`ci_persona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contacto`
--
ALTER TABLE `contacto`
  MODIFY `cod_contacto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `cod_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD CONSTRAINT `fk_contacto_persona` FOREIGN KEY (`persona_cod_persona`) REFERENCES `persona` (`cod_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
