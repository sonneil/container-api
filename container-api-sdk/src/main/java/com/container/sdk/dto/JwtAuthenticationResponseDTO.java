package com.container.sdk.dto;

import java.io.Serializable;

import com.container.sdk.dto.deserializer.JwtAuthenticationResponseDeserializer;
import com.container.sdk.dto.deserializer.JwtUserDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonDeserialize(using = JwtAuthenticationResponseDeserializer.class)
public class JwtAuthenticationResponseDTO implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	@JsonDeserialize(using = JsonDeserializer.class)
	private final String token;
	@JsonDeserialize(using = JsonDeserializer.class)
	private final String refreshToken;
	@JsonDeserialize(using = JwtUserDeserializer.class)
	private final JwtUser user;

}