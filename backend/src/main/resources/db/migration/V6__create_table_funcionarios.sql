create table funcionarios (
   id char(36) PRIMARY KEY NOT NULL,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY (fk_id_setores)
        REFERENCES setores(id),
   nome varchar(100) NOT NULL,
   ramal varchar(7) NOT NULL,
   email varchar(100) NOT NULL
);
