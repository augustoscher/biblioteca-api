package br.com.objetive.biblioteca.turma;

import java.util.Date;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity(name = "turma")
public class Turma {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @NonNull
    private String nome;
    private String userLastUpdate;
    private Date createdAt;
    private Date updatedAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
