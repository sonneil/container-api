package com.container.auth.util;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.container.sdk.dto.JwtAuthenticationResponseDTO;
import com.container.sdk.dto.JwtUser;

import io.jsonwebtoken.Claims;

public interface JwtUtil {

	public String getIdFromToken(String token);

	public Long getUserIdFromToken(String token);

	public String getUsernameFromToken(String token);

	public List<SimpleGrantedAuthority> getRolesFromToken(String token);

	public Date getCreatedDateFromToken(String token);

	public Date getExpirationDateFromToken(String token);

	public Claims getClaimsFromToken(String token);

	public Boolean isTokenExpired(String token);

	public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset);

	public JwtAuthenticationResponseDTO generateToken(JwtUser user);

	public JwtAuthenticationResponseDTO refreshToken(JwtUser user);

	public Boolean validateToken(String token);
}