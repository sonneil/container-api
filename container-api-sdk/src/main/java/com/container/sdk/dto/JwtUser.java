package com.container.sdk.dto;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.container.sdk.dto.deserializer.JwtUserDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;

@Builder
@JsonDeserialize(using = JwtUserDeserializer.class)
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private Collection<SimpleGrantedAuthority> authorities;
	private boolean enabled;

	public JwtUser() {}

	public JwtUser(Long id, String username, String password, Collection<SimpleGrantedAuthority> authorities,
			boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
	}
	
	public JwtUser(Long id, String username, String password, Collection<SimpleGrantedAuthority> authorities,
			boolean enabled, String authorizationToken) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
	}
	
	public void eraseCredentials() {
		password = null;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}