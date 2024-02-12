-- DDL
CREATE TABLE `manager` (
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `product` (
  `cost_price` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `discription` varchar(255) DEFAULT NULL,
  `manager_id` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKjr0yud7fty8ma1jvn0hif3no7` (`manager_id`),
  CONSTRAINT `FKjr0yud7fty8ma1jvn0hif3no7` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

-- Active: 1707207920389@@127.0.0.1@3306@cafe
CREATE TABLE `token` (
  `expiration_date` datetime(6) DEFAULT NULL,
  `manager_id` varchar(255) NOT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `delete_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci