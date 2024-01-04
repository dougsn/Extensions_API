create table setores (
   id char(36) PRIMARY KEY NOT NULL,
   name varchar(60) NOT NULL,
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
--CREATE TABLE setores (
--    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
--    name VARCHAR(30) NOT NULL
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
--
--INSERT INTO setores (name) VALUE ("Geral");
--
--INSERT INTO funcionarios (name, ramal, email, fk_id_setores) VALUES ("Douglas Nascimento", "266", "douglas.nascimento@renave.ind.br", 1)