package br.com.objetive.biblioteca.estatistica;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.objetive.biblioteca.utils.StreamUtils;

@Service
public class EstatisticaService {

    @Autowired
    private EstatisticaEmprestimoUsuarioRepository emprestimoRepository;

    @Autowired
    private EstatisticaLivroRepository livroRepository;

    public void atualizarLivros(Integer qtdLivro) {
        livroRepository.save(getEstatisticaLivro(qtdLivro));
    }

    public void atualizarEmprestimoPorUsuario(String usuario, Integer qtdEmprestimo) {
        qtdEmprestimo = (qtdEmprestimo == null ? 0 : qtdEmprestimo);
        emprestimoRepository.save(getEstatisticaEmprestimo(usuario, qtdEmprestimo));
    }

    private EstatisticaLivro getEstatisticaLivro(Integer qtdLivros) {
        EstatisticaLivro e;
        if (livroRepository.count() > 0) {
            e = buscarEstatisticaLivro();
            Integer livros = (e.getQtdLivros() + qtdLivros);
            e.setQtdLivros(livros);
        } else {
            e = new EstatisticaLivro();
            e.setQtdLivros(qtdLivros);
        }
        return e;
    }

    private EstatisticaEmprestimoUsuario getEstatisticaEmprestimo(String usuario, Integer qtdEmprestimo) {
        EstatisticaEmprestimoUsuario e;
        List<EstatisticaEmprestimoUsuario> list = emprestimoRepository.findByUsuarioIgnoringCase(usuario);
        if (CollectionUtils.isEmpty(list)) {
            e = new EstatisticaEmprestimoUsuario();
            e.setUsuario(usuario);
            e.setQtdEmprestimos(qtdEmprestimo);
        } else {
            e = list.get(0);
            Integer emprestimos = (e.getQtdEmprestimos() + qtdEmprestimo);
            e.setQtdEmprestimos(emprestimos);
        }
        return e;
    }

    private EstatisticaLivro buscarEstatisticaLivro() {
        List<EstatisticaLivro> list = StreamUtils.asStream(livroRepository.findAll()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
        	return new EstatisticaLivro();
        } else {
        	return list.get(0);
        }
    }

    private List<EstatisticaEmprestimoUsuario> buscarEstatisticaEmprestimo() {
        List<EstatisticaEmprestimoUsuario> list = StreamUtils.asStream(emprestimoRepository.findAll()).collect(Collectors.toList());
        return list;
    }

    public Estatistica getEstatistica() {
        Estatistica estatistica = new Estatistica();
        try {
            EstatisticaLivro estatLivro = buscarEstatisticaLivro();
        	List<EstatisticaEmprestimoUsuario> estatEmprestimos = buscarEstatisticaEmprestimo();
        	
        	estatistica.setQtdLivros(estatLivro.getQtdLivros());
        	estatistica.setEmprestimosUsuario(estatEmprestimos);
        	estatistica.setQtdEmprestimos(getTotalEmprestimos(estatEmprestimos));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return estatistica;
    }

    private Integer getTotalEmprestimos(List<EstatisticaEmprestimoUsuario> estatEmprestimos) {
        Integer soma = 0;
        for (EstatisticaEmprestimoUsuario item : estatEmprestimos) {
            soma += item.getQtdEmprestimos();
        }
        return soma;
    }
}
