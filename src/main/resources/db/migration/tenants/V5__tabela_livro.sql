create table livro(
    uuid varchar(255) primary key,
    titulo varchar(100),
    sub_titulo varchar(100),
    isbn varchar(100),
    editora_uuid varchar(255),
    autor_uuid varchar(255),
    edicao varchar(100),
    descricao varchar(255),
    codigo_barras varchar(255),
    codigo_livre varchar(255),
    user_last_update varchar(100),
    created_at timestamp,
    updated_at timestamp,
    constraint UC_ISBN unique (isbn),
    foreign key (editora_uuid) REFERENCES editora(uuid),
    foreign key (autor_uuid) REFERENCES autor(uuid)
);


