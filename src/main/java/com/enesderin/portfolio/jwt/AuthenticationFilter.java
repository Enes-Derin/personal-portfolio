package com.enesderin.portfolio.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("Gelen header: " + header);

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("Token bulunamadı veya Bearer ile başlamıyor");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username = jwtService.getUsernameToken(token);
        System.out.println("Çözümlenen kullanıcı adı: " + username);

        try {
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                boolean valid = jwtService.validateToken(token, userDetails);
                System.out.println("Token geçerli mi? " + valid);
                if (userDetails != null && valid) {
                    System.out.println("Yetkiler: " + userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("AuthenticationFilter çalışıyor");
                }
            }
        } catch (ExpiredJwtException e) {
            throw new ServletException("Expired JWT token");
        } catch (Exception e) {
            throw new ServletException(e);
        }

        filterChain.doFilter(request, response);
    }

}
