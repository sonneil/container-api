package com.container.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.container.auth.service.LoginService;
import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.container.sdk.dto.UserCredentialsDTO;
import com.container.sdk.endpoint.LoginEndpoint;

@RestController
@RequestMapping(value = LoginEndpoint.AUTHENTICATE)
public class LoginController implements LoginEndpoint {

	@Autowired
	private LoginService loginHandler;
	
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JwtAuthenticationResponseDTO performLogin(@RequestBody UserCredentialsDTO credentials) {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
        .getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
		        .getResponse();
		JwtAuthenticationResponseDTO authToken = loginHandler.performLogin(req, credentials);
		response.addCookie(new Cookie(AUTHORIZATION_TOKEN, authToken.getToken()));
		return authToken;
	}
}