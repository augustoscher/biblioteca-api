package br.com.objetive.biblioteca.livro;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface LivroRepository extends PagingAndSortingRepository<Livro, String> {

	Page<Livro> findByAutorNomeContainingOrCodigoLivreContainingOrTituloContainingAllIgnoreCase(Pageable pageable, String nomeAutor, String codigoLivre, String titulo);

}
