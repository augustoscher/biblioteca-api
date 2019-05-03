package br.com.objetive.biblioteca.emprestimo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import br.com.objetive.biblioteca.pessoa.Pessoa;
import br.com.objetive.biblioteca.turma.Turma;

@Entity(name = "emprestimo")
public class Emprestimo {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
	
	@OneToOne
    @JoinColumn(name="turma_uuid")
	private Turma turma;
	
    @OneToOne
    @JoinColumn(name="pessoa_uuid")
	private Pessoa pessoa;

    private StatusEmprestimo status = StatusEmprestimo.EMPRESTADO;
	private String observacao;
    private String userLastUpdate; 
    private Date createdAt;
    private Date updatedAt;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emprestimo", cascade = CascadeType.ALL) //bidirecional
    private List<EmprestimoLivro> livros;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public String getUserLastUpdate() {
		return userLastUpdate;
	}

	public void setUserLastUpdate(String userLastUpdate) {
		this.userLastUpdate = userLastUpdate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<EmprestimoLivro> getLivros() {
		return livros;
	}

	public void setLivros(List<EmprestimoLivro> livros) {
		this.livros = livros;
	}
    
}
