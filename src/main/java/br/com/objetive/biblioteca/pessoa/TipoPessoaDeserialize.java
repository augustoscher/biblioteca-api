package br.com.objetive.biblioteca.pessoa;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Deserealizer de TipoPessoa
 * 
 * @author Augusto Scher
 *
 */
public class TipoPessoaDeserialize extends JsonDeserializer<TipoPessoa> {

	@Override
    public TipoPessoa deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        TipoPessoa type = null;
        try{
            if(node.get("id") != null){
            		int id = Integer.valueOf(node.get("id").asText());
            		type = TipoPessoa.fromValue(id);
            } 
        }catch(Exception e){
            type = null;
        }
        return type;
    }
}
