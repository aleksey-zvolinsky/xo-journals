CREATE TABLE `lock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `set_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`key`)
);

ALTER TABLE `user` add `email` varchar(255) NOT NULL;
