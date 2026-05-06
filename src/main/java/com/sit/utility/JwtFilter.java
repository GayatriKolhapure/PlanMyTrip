package com.sit.utility;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sit.model.User;
import com.sit.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	
    	

        try {

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    User user = userRepository.findByEmail(email).orElse(null);
                    
                    System.out.println("USER ROLE: " + user.getRole());

                    if (user != null && jwtUtil.validateToken(token, user.getEmail())) {

                    	String role = jwtUtil.extractRole(token);

                    	var authorities = List.of(new SimpleGrantedAuthority(role));

                        var authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();

        return path.equals("/api/users/login") ||
               path.equals("/api/users/register");
    }
}