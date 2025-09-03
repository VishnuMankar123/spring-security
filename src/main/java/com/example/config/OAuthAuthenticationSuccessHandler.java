package com.example.config;

import com.example.entity.UserEntity;
import com.example.repo.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuthAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // OAuth2 user info nikalna
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Example: Google OAuth2 ke attributes
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // DB check karo - agar user already hai to skip, warna save
        if (!userRepository.existsByEmail(email)) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(email);
            newUser.setName(name);
            newUser.setPassword("{noop}1234");
            newUser.setRole("ROLE_USER");
            newUser.setEmail(email);
            System.out.println("User saved: " + newUser);
            userRepository.save(newUser);
        }

        // Redirect after success
        response.sendRedirect("/welcome");
    }
}

