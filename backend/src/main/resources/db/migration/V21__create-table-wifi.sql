create table wifi (
   id char(36) PRIMARY KEY NOT NULL,
   ip varchar(200) NOT NULL,
   usuario varchar(200) NOT NULL,
   senha_browser varchar(200) NOT NULL,
   ssid varchar(200) NOT NULL,
   senha_wifi varchar(200) NOT NULL,
   fk_id_setores char(36) NOT NULL,
   FOREIGN KEY(fk_id_setores)
       REFERENCES setores(id)
);