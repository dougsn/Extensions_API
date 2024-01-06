create table setores (
   id char(36) PRIMARY KEY NOT NULL,
   nome varchar(60) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO setores (id, nome) VALUES ("7bf808f8-da36-44ea-8fbd-79653a80023e", "TI");