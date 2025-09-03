package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuthAuthenticationSuccessHandler successHandler;

    public SecurityConfig(OAuthAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }


    // This is For Spring Security default login page
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // sab kuch login ke baad hi chalega
                )
                .formLogin(Customizer.withDefaults()) // Spring ka default login page use hoga
                .logout(Customizer.withDefaults());

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/login", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")                     // custom login page
                        .loginProcessingUrl("/login")            // POST ke liye bhi yahi use hoga
                        .defaultSuccessUrl("/welcome", true) // login ke baad redirect
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")                     // same login page for Google
                        .successHandler(successHandler)     // after Google login success
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                    // POST /logout
                        .logoutSuccessUrl("/home")       // logout ke baad redirect
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());               // demo/test ke liye disable

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("test")
                .password("{noop}1234") // {noop} matlab password plain text hai
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
