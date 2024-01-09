create table setores (
   id char(36) PRIMARY KEY NOT NULL,
   nome varchar(60) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);