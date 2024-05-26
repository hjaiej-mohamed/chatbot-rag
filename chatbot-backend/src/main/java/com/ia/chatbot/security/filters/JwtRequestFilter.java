package com.ia.chatbot.security.filters;


import com.ia.chatbot.security.jwt.JwtUtils;
import com.ia.chatbot.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter{
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username =null;
        String token = null;
        try{
            if(authHeader != null && authHeader.startsWith("Bearer")){

                token = authHeader.substring(7);
                username = jwtUtils.extractUsername(token);
            }
            if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if( jwtUtils.isTokenValid(token,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
        } catch (JwtException ex) {
            handleJwtException(response, ex);
        }

    }

    private void handleJwtException(HttpServletResponse response, JwtException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (ex instanceof ExpiredJwtException) {
            response.getWriter().write("Token has expired");
        } else {
            response.getWriter().write("Invalid token");
        }
        log.error("JWT exception: {}", ex.getMessage());
    }
}
