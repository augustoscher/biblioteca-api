package br.com.objetive.biblioteca.emprestimo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.objetive.biblioteca.livro.Livro;

@Entity
public class EmprestimoLivro {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
    
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprestimo_uuid")
    private Emprestimo emprestimo;
    
    @ManyToOne
    private Livro livro;
	
    private String usuarioEmprestimo;
    private String usuarioDevolucao;
    
	@Enumerated
    @Column(columnDefinition = "smallint")
    private StatusEmprestimo status = StatusEmprestimo.EMPRESTADO;
    private String userLastUpdate; 
    private Date createdAt;
    private Date updatedAt;
    
    public EmprestimoLivro() {
    	
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Emprestimo getEmprestimo() {
		return emprestimo;
	}
	
	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public String getUsuarioEmprestimo() {
		return usuarioEmprestimo;
	}

	public void setUsuarioEmprestimo(String usuarioEmprestimo) {
		this.usuarioEmprestimo = usuarioEmprestimo;
	}

	public String getUsuarioDevolucao() {
		return usuarioDevolucao;
	}

	public void setUsuarioDevolucao(String usuarioDevolucao) {
		this.usuarioDevolucao = usuarioDevolucao;
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
    
}
