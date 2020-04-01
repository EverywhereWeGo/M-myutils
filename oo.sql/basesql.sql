CREATE TABLE `basetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` varchar(255) DEFAULT NULL,
  `update_user` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;