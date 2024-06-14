create table terminal (
   id char(36) PRIMARY KEY NOT NULL,
   usuario varchar(200) NOT NULL,
   modelo varchar(200) NOT NULL,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY(fk_id_setores)
       REFERENCES setores(id)
);