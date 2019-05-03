package br.com.objetive.biblioteca.estatistica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class EstatisticaLivro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @NonNull
    private Integer qtdLivros;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(Integer qtdLivros) {
        this.qtdLivros = qtdLivros;
    }

}
