package com.container.auth.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.container.auth.repository.UserDetailsRepository;
import com.container.auth.service.mapper.JwtUserMapper;
import com.container.sdk.dto.JwtUser;

@Service
@Transactional
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Collection<SimpleGrantedAuthority> authorities = JwtUserMapper.mapAuthorities((Object[])userRepo.retrieveUserRoles(username)); 
        JwtUser user = JwtUserMapper.map((Object[])userRepo.retrieveUserCredentials(username), authorities);
        return user;
    }
}
