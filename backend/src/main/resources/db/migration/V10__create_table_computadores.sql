create table computadores (
   id char(36) PRIMARY KEY NOT NULL,
   hostname varchar(50) NOT NULL,
   modelo varchar(15) NOT NULL,
   cpu varchar(15) NOT NULL,
   memoria varchar(15) NOT NULL,
   disco varchar(15) NOT NULL,
   sistema_operacional varchar(15) NOT NULL,
   observacao LONGTEXT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY (fk_id_setores)
        REFERENCES setores(id)
);
