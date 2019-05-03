create table emprestimo(
    uuid varchar(255) primary key,
    turma_uuid varchar(255),
    pessoa_uuid varchar(255),
    observacao varchar(255),
    user_last_update varchar(100),
    created_at timestamp,
    updated_at timestamp,
    foreign key (turma_uuid) REFERENCES turma(uuid),
    foreign key (pessoa_uuid) REFERENCES pessoa(uuid)
);

create table emprestimolivro(
    uuid varchar(255) primary key,
    emprestimo_uuid varchar(255),
    livro_uuid varchar(255),
    usuario_emprestimo varchar(100),
    usuario_devolucao varchar(100),
	status smallint,
    user_last_update varchar(100),
    created_at timestamp,
    updated_at timestamp,
    foreign key (emprestimo_uuid) REFERENCES emprestimo(uuid),
    foreign key (livro_uuid) REFERENCES livro(uuid)
);

   



