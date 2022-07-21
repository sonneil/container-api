package com.container.sdk.dto.deserializer;

import java.io.IOException;

import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

public class JwtAuthenticationResponseDeserializer extends JsonDeserializer<JwtAuthenticationResponseDTO> {

	private JwtUserDeserializer userDeserializer = new JwtUserDeserializer();
	
	@Override
	public JwtAuthenticationResponseDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JwtAuthenticationResponseDTO result =  new JwtAuthenticationResponseDTO(
        		readJsonNode(jsonNode, "token").asText(),
                readJsonNode(jsonNode, "refreshToken").asText(), 
                userDeserializer.deserializeUser(jp, readJsonNode(jsonNode, "user"))
        );
        return result;
	}
	
	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
