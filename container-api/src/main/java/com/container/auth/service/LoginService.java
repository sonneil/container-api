package com.container.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.container.auth.util.JwtUtil;
import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.container.sdk.dto.JwtUser;
import com.container.sdk.dto.UserCredentialsDTO;

@Component
public class LoginService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	public JwtAuthenticationResponseDTO performLogin(HttpServletRequest req, UserCredentialsDTO credentials) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(credentials.getUser(),
				credentials.getPassword());
		Authentication auth = authManager.authenticate(authReq);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);

		HttpSession session = req.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

		JwtUser user;
		try {
			user = (JwtUser) auth.getPrincipal();
		} catch (Exception e) {
			throw e;
		}
		final JwtAuthenticationResponseDTO token = jwtTokenUtil.generateToken(user);
		return token;
	}

}
