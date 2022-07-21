package com.container.auth.service.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.container.commons.util.RawIteratorMapper;
import com.container.enums.UserStatusEnum;
import com.container.sdk.dto.JwtUser;

public class JwtUserMapper {

	public static JwtUser map(Object[] rawUser, Collection<SimpleGrantedAuthority> authorities) {
		RawIteratorMapper iterator = new RawIteratorMapper(rawUser);
		return JwtUser.builder().id(iterator.nextLong())
				.username(iterator.nextString())
				.password(iterator.nextString())
				.authorities(authorities)
				.enabled(UserStatusEnum.ENABLED.getStatus().equals(iterator.nextInteger())).build();
				
	}

	public static Collection<SimpleGrantedAuthority> mapAuthorities(Object[] rawAuthorities) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Object authority : rawAuthorities) {
			authorities.add(new SimpleGrantedAuthority((String) authority));
		}
		return authorities;
	}

}
