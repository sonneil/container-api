package com.container.sdk.controller;

import org.springframework.stereotype.Component;

import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.container.sdk.dto.UserCredentialsDTO;
import com.container.sdk.endpoint.LoginEndpoint;
import com.container.sdk.service.RestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class LoginRestController implements LoginEndpoint {
	private final RestService restService;
	
	public JwtAuthenticationResponseDTO performLogin(UserCredentialsDTO credentials) throws Exception {
		JwtAuthenticationResponseDTO authResponse = restService.postResultOne(JwtAuthenticationResponseDTO.class, AUTHENTICATE , credentials);
		return authResponse;
	}
}
