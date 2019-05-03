create table estatistica_livro(
    uuid varchar(255) primary key,
    qtd_livros integer,
    qtd_exemplares integer
);

create table estatistica_emprestimo_usuario(
    uuid varchar(255) primary key,
    usuario varchar(100),
    qtd_emprestimos integer
);