package com.container.sdk.dto.deserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.container.sdk.dto.JwtUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

public class JwtUserDeserializer extends JsonDeserializer<JwtUser> {

	@Override
	public JwtUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        return deserializeUser(jp, jsonNode);
	}
	
	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
	
	public JwtUser deserializeUser(JsonParser jp, JsonNode userNode) throws JsonProcessingException, IOException {
		LinkedList<SimpleGrantedAuthority> authorities = deserializeAuthorities(jp, userNode.get("authorities"));
        JsonNode password = readJsonNode(userNode, "password");
        JwtUser result =  new JwtUser(
        		readJsonNode(userNode, "id").asLong(),
                readJsonNode(userNode, "username").asText(), 
                password.asText(""), 
                authorities,
                readJsonNode(userNode, "enabled").asBoolean()
        );

        if(password.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
	}
	
	public LinkedList<SimpleGrantedAuthority> deserializeAuthorities(JsonParser p, JsonNode authoritiesNode) throws JsonProcessingException, IOException {
		LinkedList<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        Iterator<JsonNode> elements = authoritiesNode.elements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return grantedAuthorities;
	}
}
