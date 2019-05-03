package br.com.objetive.biblioteca.estatistica;

import java.util.List;

public class Estatistica {
    
    private Integer qtdLivros;
    private Integer qtdEmprestimos;
    private List<EstatisticaEmprestimoUsuario> emprestimosUsuario;

    public Integer getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(Integer qtdLivros) {
        this.qtdLivros = qtdLivros;
    }

    public Integer getQtdEmprestimos() {
        return qtdEmprestimos;
    }

    public void setQtdEmprestimos(Integer qtdEmprestimos) {
        this.qtdEmprestimos = qtdEmprestimos;
    }

    public List<EstatisticaEmprestimoUsuario> getEmprestimosUsuario() {
        return emprestimosUsuario;
    }

    public void setEmprestimosUsuario(List<EstatisticaEmprestimoUsuario> emprestimosUsuario) {
        this.emprestimosUsuario = emprestimosUsuario;
    }

}
