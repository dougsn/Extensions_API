CREATE TABLE IF NOT EXISTS `users` (
  `id` char(36) PRIMARY KEY NOT NULL,
  `username` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL
);