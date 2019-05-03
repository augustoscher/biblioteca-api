alter table livro add column emprestado boolean;
update livro set emprestado = true
where exists (
	select 1 from emprestimo_livro
	where emprestimo_livro.livro_uuid = livro.uuid
	and emprestimo_livro.status = 0
);