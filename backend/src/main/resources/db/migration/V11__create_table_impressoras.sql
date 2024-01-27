create table impressoras (
   id char(36) PRIMARY KEY NOT NULL,
   marca varchar(50) NOT NULL,
   modelo varchar(15) NOT NULL,
   ip varchar(15) NOT NULL,
   tonner LONGTEXT NOT NULL,
   observacao LONGTEXT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY (fk_id_setores)
        REFERENCES setores(id)
);
