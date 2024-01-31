create table antenas (
   id char(36) PRIMARY KEY NOT NULL,
   ip varchar(60) NOT NULL,
   localizacao varchar(60) NOT NULL,
   ssid varchar(60) NOT NULL,
   senha varchar(60) NOT NULL,
   fk_id_locais char(36) NOT NULL,
   FOREIGN KEY (fk_id_locais)
        REFERENCES locais(id),
   fk_id_modelos char(36) NOT NULL,
   FOREIGN KEY (fk_id_modelos)
        REFERENCES modelos(id),
   fk_id_tipo_antena char(36) NOT NULL,
   FOREIGN KEY (fk_id_tipo_antena)
        REFERENCES tipo_antena(id)
);