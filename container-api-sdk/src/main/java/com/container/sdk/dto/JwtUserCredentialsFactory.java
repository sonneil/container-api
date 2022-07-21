package com.container.sdk.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserCredentialsFactory {

    private JwtUserCredentialsFactory() {}

    public static JwtUser create(Object rawUser) {
    	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return new JwtUser(1l, "user", "$2a$10$ZRMCpIGh/rKzwv91a4eE9.oNoe9dKtn00cNqNDva7SBiVgSNCLos.", mapToGrantedAuthorities(authorities), true);
    }

    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(List<SimpleGrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_"+authority.getAuthority().toUpperCase()))
                .collect(Collectors.toList());
    }
}