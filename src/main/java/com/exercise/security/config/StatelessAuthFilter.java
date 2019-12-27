package com.exercise.security.config;

import com.exercise.security.config.services.TokenAuthService;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthFilter extends OncePerRequestFilter {
    private final TokenAuthService tokenAuthService;

    public StatelessAuthFilter(@NonNull TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(tokenAuthService.getAuthentication(httpServletRequest).orElse(null));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
