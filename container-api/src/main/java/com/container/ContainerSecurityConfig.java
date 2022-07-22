package com.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.container.auth.filter.JwtAuthenticationEntryPoint;
import com.container.auth.filter.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
public class ContainerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	@Qualifier("jwtUserDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("Configuring SPRING SECURITY");
		auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
	        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.OPTIONS).permitAll()
	        .antMatchers("/auth/token/validate**").permitAll()
	        .antMatchers("/authenticate**").permitAll()
	        .antMatchers("/v2/api-docs**").permitAll()
	        .antMatchers("/swagger-ui.html/**").permitAll()
	        .antMatchers("/swagger-ui.html**").permitAll()
	        .antMatchers("/webjars/**").permitAll()
	        .antMatchers("/swagger-resources/**").permitAll()
	        .antMatchers("/**").hasRole("ADMIN")
	        .anyRequest().authenticated();
        
        http
        	.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }
    
    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

}