create table projeto (
   id char(36) PRIMARY KEY NOT NULL,
   nome varchar(200) NOT NULL,
   descricao TEXT NOT NULL,
   created_by varchar(200) NOT NULL,
   updated_by varchar(200),
   created_at DATE,
   updated_at DATE,
   fk_id_status char(36) NOT NULL,
   FOREIGN KEY(fk_id_status)
       REFERENCES status(id)
);