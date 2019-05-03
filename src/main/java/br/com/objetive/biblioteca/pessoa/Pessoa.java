package br.com.objetive.biblioteca.pessoa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity(name =  "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
    
    private String codigo;
    
    @NonNull
	private String nome;
    
    @Enumerated
    @Column(columnDefinition = "smallint")
    private TipoPessoa tipo;
    private String userLastUpdate; 
    private Date createdAt;
    private Date updatedAt;
    
    public Pessoa() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getCodigo() {
    		return codigo;
    }
    
    public void setCodigo(String codigo) {
    		this.codigo = codigo;
    }

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
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
