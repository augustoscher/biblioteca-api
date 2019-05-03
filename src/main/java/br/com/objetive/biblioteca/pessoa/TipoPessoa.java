package br.com.objetive.biblioteca.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TipoPessoaDeserialize.class)
public enum TipoPessoa {

    ALUNO(0, "Aluno"), 
    PROFESSOR(1,"Professor"), 
    FUNCIONARIO(2, "Funcionário"), 
    OUTRO(3, "Outro");
	
    private int id;
    private String descricao;

    TipoPessoa(int id, String dsc) {
        this.id = id;
        this.descricao = dsc;
    }

    public int getId() {
        return this.id;
    }
    
    public String getDescricao() {
		return this.descricao;
    }

    public static TipoPessoa fromValue(int value) {
        for (TipoPessoa tipo : values()) {
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
