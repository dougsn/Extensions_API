create table caixa_email (
   id char(36) PRIMARY KEY NOT NULL,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY (fk_id_setores)
        REFERENCES setores(id),
   conta varchar(100) NOT NULL,
   senha varchar(50) NOT NULL
);
