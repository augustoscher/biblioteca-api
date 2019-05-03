package br.com.objetive.biblioteca.livro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import br.com.objetive.biblioteca.autor.Autor;
import br.com.objetive.biblioteca.editora.Editora;

@Entity
public class Livro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @NonNull
    private String titulo;

    @NonNull
    private String subTitulo;

    @NonNull
    @Column(unique = true)
    private String isbn;

    @ManyToOne
    private Editora editora;

    @ManyToOne
    private Autor autor;

    private String edicao;
    private String descricao;
    private String codigoBarras;
    private String codigoLivre;
    private Boolean emprestado;
    private String userLastUpdate; 
    private Date createdAt;
    private Date updatedAt;
    
    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(Boolean emprestado) {
        this.emprestado = emprestado;
    }

    public String getCodigoLivre() {
		return codigoLivre;
	}

	public void setCodigoLivre(String codigoLivre) {
		this.codigoLivre = codigoLivre;
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
