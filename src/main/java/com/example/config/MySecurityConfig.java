package com.example.config;


import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebSecurity(debug = true)//@EnableWebSecurity this annotation create spring security filter chain
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //this class is going to help you to create spring security filter chain (GUN Man)


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ram").password("{bcrypt}$2a$12$GkLDP8PZQMJT05I.L5NcsuP.Tj7TeRgJm3vywSGQG9FiD3qpO9vmG").roles("admin");
        // auth.inMemoryAuthentication().withUser("vishnu").password("{noop}$2a$12$GkLDP8PZQMJT05I.L5NcsuP.Tj7TeRgJm3vywSGQG9FiD3qpO9vmG").roles("admin");
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/step1").authenticated()
                .antMatchers("/step2").authenticated()
                .antMatchers("/hello").permitAll()
                .and().formLogin().and().httpBasic();
    }
}

