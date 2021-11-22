drop table if exists `pt_student_pt_teacher`;
drop table if exists `monthly`;
drop table if exists `certificate`;
drop table if exists `career`;
drop table if exists `sns`;
drop table if exists `pt_student`;
drop table if exists `pt_teacher`;
drop table if exists `user_base`;
drop table if exists `authority`;
drop table if exists `refresh_token`;

-- ssafy_web_db.authority definition

CREATE TABLE `authority` (
                             `authority_name` varchar(50) NOT NULL,
                             PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.testuser definition

-- CREATE TABLE `testuser` (
--                             `ID` int NOT NULL AUTO_INCREMENT,
--                             `USER_NAME` varchar(45) NOT NULL,
--                             `PASSWORD` varchar(45) NOT NULL,
--                             `EMAIL` varchar(100) DEFAULT NULL,
--                             `CREATED_TIME` datetime NOT NULL,
--                             `UPDATED_TIME` datetime DEFAULT NULL,
--                             `USER_TYPE` varchar(45) NOT NULL,
--                             `DOB` date NOT NULL,
--                             PRIMARY KEY (`ID`),
--                             UNIQUE KEY `USER_NAME_UNIQUE` (`USER_NAME`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.user_base definition

CREATE TABLE `user_base` (
                             `role` varchar(31) NOT NULL,
                             `age` int NOT NULL,
                             `user_id` bigint NOT NULL AUTO_INCREMENT,
                             `created_date` datetime(6) DEFAULT NULL,
                             `modified_date` datetime(6) DEFAULT NULL,
                             `address_detailed_address` varchar(255) DEFAULT NULL,
                             `address_street` varchar(255) DEFAULT NULL,
                             `address_zipcode` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `gender` int DEFAULT NULL,
                             `nickname` varchar(255) DEFAULT NULL,
                             `password` varchar(255) DEFAULT NULL,
                             `profile_picture_addr` varchar(255) DEFAULT NULL,
                             `tel` varchar(255) DEFAULT NULL,
                             `username` varchar(255) DEFAULT NULL,
                             `authority` varchar(50) DEFAULT NULL,
                             PRIMARY KEY (`user_id`),
                             UNIQUE KEY `UK_17g9khvrjbxkae551jj8baem2` (`email`),
                             UNIQUE KEY `UK_h3845mxnmkcprpwi3eux7krwt` (`nickname`),
                             KEY `FK7cv1ethobb7uxhdjvneoojnbk` (`authority`),
                             CONSTRAINT `FK7cv1ethobb7uxhdjvneoojnbk` FOREIGN KEY (`authority`) REFERENCES `authority` (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.pt_student definition

CREATE TABLE `pt_student` (
                              `pt_student_id` bigint NOT NULL,
                              PRIMARY KEY (`pt_student_id`),
                              CONSTRAINT `FKfbg46p6dv2kx7ljtu5g3sioqn` FOREIGN KEY (`pt_student_id`) REFERENCES `user_base` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.pt_teacher definition

CREATE TABLE `pt_teacher` (
                              `description` varchar(255) DEFAULT NULL,
                              `major` varchar(255) DEFAULT NULL,
                              `price` int NOT NULL,
                              `star_rating` float NOT NULL,
                              `pt_teacher_id` bigint NOT NULL,
                              PRIMARY KEY (`pt_teacher_id`),
                              CONSTRAINT `FKha16vj5r5o16u0eg6j3qsvc6d` FOREIGN KEY (`pt_teacher_id`) REFERENCES `user_base` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.sns definition

CREATE TABLE `sns` (
                       `sns_id` bigint NOT NULL AUTO_INCREMENT,
                       `platform` varchar(255) DEFAULT NULL,
                       `url` varchar(255) DEFAULT NULL,
                       `pt_teacher_id` bigint DEFAULT NULL,
                       PRIMARY KEY (`sns_id`),
                       KEY `FKo8bl9cuy3jnhaxb9vnuvywini` (`pt_teacher_id`),
                       CONSTRAINT `FKo8bl9cuy3jnhaxb9vnuvywini` FOREIGN KEY (`pt_teacher_id`) REFERENCES `pt_teacher` (`pt_teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.career definition

CREATE TABLE `career` (
                          `career_id` bigint NOT NULL AUTO_INCREMENT,
                          `company` varchar(255) DEFAULT NULL,
                          `end_date` date DEFAULT NULL,
                          `role` varchar(255) DEFAULT NULL,
                          `start_date` date DEFAULT NULL,
                          `pt_teacher_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`career_id`),
                          KEY `FKn4qky9eyec1a9thl0effrhgqf` (`pt_teacher_id`),
                          CONSTRAINT `FKn4qky9eyec1a9thl0effrhgqf` FOREIGN KEY (`pt_teacher_id`) REFERENCES `pt_teacher` (`pt_teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.certificate definition

CREATE TABLE `certificate` (
                               `certificate_id` bigint NOT NULL AUTO_INCREMENT,
                               `date` date DEFAULT NULL,
                               `cert_name` varchar(255) DEFAULT NULL,
                               `publisher` varchar(255) DEFAULT NULL,
                               `pt_teacher_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`certificate_id`),
                               KEY `FK8jbmq3hnetxk7cxn6hcvpgws6` (`pt_teacher_id`),
                               CONSTRAINT `FK8jbmq3hnetxk7cxn6hcvpgws6` FOREIGN KEY (`pt_teacher_id`) REFERENCES `pt_teacher` (`pt_teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.monthly definition

CREATE TABLE `monthly` (
                           `monthly_id` bigint NOT NULL AUTO_INCREMENT,
                           `height` int NOT NULL,
                           `month` int DEFAULT NULL,
                           `weight` int NOT NULL,
                           `pt_student_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`monthly_id`),
                           UNIQUE KEY `UKl2x2i25lvbdtda0odhr06dlbe` (`pt_student_id`,`month`),
                           CONSTRAINT `FKakv41cuv46liil3c5ag21gcej` FOREIGN KEY (`pt_student_id`) REFERENCES `pt_student` (`pt_student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ssafy_web_db.pt_student_pt_teacher definition

CREATE TABLE `pt_student_pt_teacher` (
                                         `pt_student_pt_teacher_id` bigint NOT NULL AUTO_INCREMENT,
                                         `reservation_date` datetime(6) DEFAULT NULL,
                                         `description` varchar(255) not null,
                                         `pt_student_id` bigint DEFAULT NULL,
                                         `pt_teacher_id` bigint DEFAULT NULL,
                                         PRIMARY KEY (`pt_student_pt_teacher_id`),
                                         UNIQUE KEY `UKp29lb4o9uvbja8y7gbs0bmmjp` (`pt_teacher_id`,`reservation_date`),
                                         KEY `FKjmg2wr1mh6rc7dcfungkws46l` (`pt_student_id`),
                                         CONSTRAINT `FK4i941psqignu9orw9o8fppn97` FOREIGN KEY (`pt_teacher_id`) REFERENCES `pt_teacher` (`pt_teacher_id`),
                                         CONSTRAINT `FKjmg2wr1mh6rc7dcfungkws46l` FOREIGN KEY (`pt_student_id`) REFERENCES `pt_student` (`pt_student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



create table refresh_token (
                               user_email varchar(255) not null,
                               value varchar(255),
                               primary key (user_email)
) engine=InnoDB