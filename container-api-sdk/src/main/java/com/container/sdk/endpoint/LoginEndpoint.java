package com.container.sdk.endpoint;

import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.container.sdk.dto.UserCredentialsDTO;

public interface LoginEndpoint {
	
	static final String AUTHENTICATE = "/authenticate";
	static final String AUTHORIZATION_TOKEN = "authorization";
	
	public JwtAuthenticationResponseDTO performLogin(UserCredentialsDTO credentials) throws Exception;
}
