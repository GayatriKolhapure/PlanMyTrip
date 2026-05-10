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

            // ✅ Check if header exists and starts with Bearer
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                // ✅ Extract email from token
                String email = jwtUtil.extractEmail(token);

                // ✅ Check if already authenticated
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    User user = userRepository.findByEmail(email).orElse(null);

                    // ✅ Validate token
                    if (user != null && jwtUtil.validateToken(token, user.getEmail())) {

                        // ✅ Extract role from token
                        String role = jwtUtil.extractRole(token);

                        // ✅ Set authority (VERY IMPORTANT)
                        var authorities = List.of(new SimpleGrantedAuthority(role));

                        // ✅ Create authentication object
                        var authToken = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                authorities
                        );

                        // ✅ Set authentication in context
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getMessage());
        }

        // ✅ Continue filter chain
        filterChain.doFilter(request, response);
    }

    // ✅ Skip JWT filter for public APIs
    		@Override
    		protected boolean shouldNotFilter(HttpServletRequest request) {

    		    String path = request.getServletPath();

    		    System.out.println("PATH: " + path);

    		    return path.startsWith("/api/users/login") ||
    		           path.startsWith("/api/users/register") ||
    		           path.startsWith("/api/users/forgot-password") ||
    		           path.startsWith("/api/users/verify-answer") ||
    		           path.startsWith("/api/users/reset-password");
    		}
    		

}