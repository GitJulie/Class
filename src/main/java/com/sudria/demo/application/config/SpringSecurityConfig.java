package com.sudria.demo.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  // Create 2 users for demo
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").roles("USER")
        .and()
        .withUser("Alexandre").password("{noop}1234").roles("USER", "ADMIN")
        .and()
        .withUser("Julie").password("{noop}abcd").roles("USER", "ADMIN");
  }

  // Secure the endpoins with HTTP Basic authentication
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v1/animals/**").hasRole("USER")
        .antMatchers(HttpMethod.POST, "/api/v1/animals").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/v1/animals/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/api/v1/animals/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/v1/animals/**").hasRole("ADMIN")
        .and()
        .csrf().disable()
        .formLogin().disable();
  }
}