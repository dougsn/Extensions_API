create table setores (
   id char(36) PRIMARY KEY NOT NULL,
   nome varchar(60) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



--CREATE TABLE user (
--    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
--    name VARCHAR(30) NOT NULL,
--    email VARCHAR(150) NOT NULL,
--    password VARCHAR(64) NOT NULL,
--    role ENUM('USER','ADMIN') NOT NULL,
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
--
--CREATE TABLE funcionarios (
--    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
--    name VARCHAR(30) NOT NULL,
--    ramal VARCHAR(30) NOT NULL,
--    email VARCHAR(150) NOT NULL,
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    fk_id_setores BIGINT,
--    FOREIGN KEY (fk_id_setores)
--        REFERENCES setores(id)
--);