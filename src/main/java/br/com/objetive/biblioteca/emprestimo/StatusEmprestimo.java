package br.com.objetive.biblioteca.emprestimo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = StatusEmprestimoDeserializer.class)
public enum StatusEmprestimo {

	EMPRESTADO(0, "Emprestado"), 
    DEVOLVIDO(1, "Devolvido"); 
	
    private int id;
    private String descricao;

    StatusEmprestimo(int id, String desc) {
        this.id = id;
        this.descricao = desc;
    }

    public int getId() {
        return this.id;
    }

    public String getDescricao() {
		return this.descricao;
    }

    public static StatusEmprestimo fromValue(int value) {
        for (StatusEmprestimo tipo : values()) {
            if (tipo.getId() == value) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("value not valid");
    }

    public String toJson() {
        return this.id + " - " + this.descricao;
    }
}
