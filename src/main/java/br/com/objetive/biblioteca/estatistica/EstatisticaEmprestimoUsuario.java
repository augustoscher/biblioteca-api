package br.com.objetive.biblioteca.estatistica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class EstatisticaEmprestimoUsuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @NonNull
    private String usuario;
    private Integer qtdEmprestimos;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String user) {
        this.usuario = user;
    }

    public Integer getQtdEmprestimos() {
        return qtdEmprestimos;
    }

    public void setQtdEmprestimos(Integer qtdEmprestimos) {
        this.qtdEmprestimos = qtdEmprestimos;
    }

}
