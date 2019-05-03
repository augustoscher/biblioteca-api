package br.com.objetive.biblioteca.emprestimo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface EmprestimoRepository extends PagingAndSortingRepository<Emprestimo, String> {

    Page<Emprestimo> findByStatus(Pageable pageable, StatusEmprestimo status);

    @Query(value = "select e.* from emprestimo e left outer join turma t on e.turma_uuid=t.uuid left outer join pessoa p on e.pessoa_uuid=p.uuid " +
                   "where e.status = :status and (upper(t.nome) like upper(:turmaNome) or upper(p.nome) like upper(:pessoaNome))",
            countQuery = "select count(*) from emprestimo left outer join turma t on e.turma_uuid=t.uuid left outer join pessoa p on e.pessoa_uuid=p.uuid " +
                         "where e.status = :status and (upper(t.nome) like upper(:turmaNome) or upper(p.nome) like upper(:pessoaNome))",
            nativeQuery = true)
    Page<Emprestimo> findByTurmaPessoa(@Param("status") Integer status, @Param("turmaNome") String turmaNome, @Param("pessoaNome") String pessoaNome, Pageable pageable);

    @Query(value = "select e.* from emprestimo e where e.status = :status and exists (select 1 from emprestimo_livro el where el.emprestimo_uuid = e.uuid and el.status = :status)", //
            nativeQuery = true)
    List<Emprestimo> findAllEmprestados(@Param("status") Integer emprestado);

    Page<Emprestimo> findByStatusAndTurmaNomeContainingIgnoringCaseOrPessoaNomeContainingIgnoringCase(Pageable pageable, StatusEmprestimo status, String nomePessoa, String nomeTurma);

}
