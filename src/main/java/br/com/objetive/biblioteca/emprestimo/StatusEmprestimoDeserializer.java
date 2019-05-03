package br.com.objetive.biblioteca.emprestimo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * Deserealizer de StatusEmprestimo
 * 
 * @author Augusto Scher
 *
 */
public class StatusEmprestimoDeserializer extends JsonDeserializer<StatusEmprestimo> {

	@Override
	public StatusEmprestimo deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        StatusEmprestimo type = null;
        try{
            if(node.get("id") != null){
        		int id = Integer.valueOf(node.get("id").asText());
        		type = StatusEmprestimo.fromValue(id);
            } 
        }catch(Exception e){
            type = null;
        }
        return type;
	}

}
