package com.container.sdk.dto;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

@Getter
public class JwtTokenUser extends JwtUser {

	private static final long serialVersionUID = 1L;
	private String authorizationToken;

	public JwtTokenUser() {}
	
	public JwtTokenUser(Long id, String username, String password, Collection<SimpleGrantedAuthority> authorities, Boolean enabled, String authorizationToken) {
		super(id, username, password, authorities, enabled);
		this.authorizationToken = authorizationToken;
	}
	
	


}