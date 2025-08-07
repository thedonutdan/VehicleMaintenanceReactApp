package io.thedonutdan.vehiclemaintenance.security;

import java.io.IOError;
import java.io.IOException;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import io.thedonutdan.vehiclemaintenance.DAO.UserDAO;
import io.thedonutdan.vehiclemaintenance.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDAO userDAO;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDAO userDAO) {
        this.jwtUtil = jwtUtil;
        this.userDAO = userDAO;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt".equals(c.getName())) {
                    try {
                        UUID userId = jwtUtil.parseToken(c.getValue());
                        User user = userDAO.findById(userId);
                        if (user != null) {
                            var auth = new UsernamePasswordAuthenticationToken(user.getUserId(), null);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    } catch (JwtException | IllegalArgumentException e) {

                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
