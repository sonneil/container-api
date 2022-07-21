package com.container.auth.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.container.auth.util.JwtUtil;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(value = "jwtUtilImpl")
	private JwtUtil jwtTokenUtil;

	private String tokenHeader = "authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Authorization, refreshauthorization, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials");
		if (response.getHeader("Access-Control-Allow-Credentials") == null)
			response.addHeader("Access-Control-Allow-Credentials", "true");
		if (response.getHeader("Access-Control-Allow-Methods") == null)
			response.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");

		String token;
		
		String header = request.getHeader(this.tokenHeader);
		token = header == null ? null : request.getHeader(this.tokenHeader);

		if (token != null && !token.equals("")) {
			logger.info("token obtained for user:");
			if (jwtTokenUtil.isTokenExpired(token)) {
				response.setStatus(490);
				return;
			}

			if (jwtTokenUtil.validateToken(token)) {

				String username = jwtTokenUtil.getUsernameFromToken(token);
				List<SimpleGrantedAuthority> autorities = jwtTokenUtil.getRolesFromToken(token);
				logger.info("user = " + username + " -> checking authentication");

				if (SecurityContextHolder.getContext().getAuthentication() == null) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, null, autorities);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					logger.info("authenticated user " + username + ", setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);

	}

}