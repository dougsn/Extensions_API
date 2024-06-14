create table manutencao_catraca (
   id char(36) PRIMARY KEY NOT NULL,
   dia DATE NOT NULL,
   defeito varchar(200) NOT NULL,
   observacao varchar(200) NOT NULL,
   created_by varchar(200) NOT NULL,
   updated_by varchar(200),
   updated_at DATETIME,
   fk_id_catracas char(36) NOT NULL,
   FOREIGN KEY(fk_id_catracas)
       REFERENCES catracas(id)
);