/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.GroupProject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Studio20-10
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        String inputUserType = request.getParameter("userType");
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String actualRole = user.getRole().toUpperCase();

            if ("doctor".equalsIgnoreCase(inputUserType) && "PATIENT".equals(actualRole)) {
                request.getSession().invalidate();
                response.sendRedirect("/login?error=unauthorizedRole");
                return;
            }

            if ("admin".equalsIgnoreCase(inputUserType) && !"ADMIN".equals(actualRole)) {
                request.getSession().invalidate();
                response.sendRedirect("/login?error=unauthorizedRole");
                return;
            }

            if ("admin".equalsIgnoreCase(inputUserType)) {
                response.sendRedirect("/h2-console");
            } else if ("doctor".equalsIgnoreCase(inputUserType)) {
                response.sendRedirect("/doctorsite");
            } else {
                response.sendRedirect("/website");
            }
        } else {
            response.sendRedirect("/login?error=userNotFound");
        }
    }
}
