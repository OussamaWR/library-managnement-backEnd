package com.inn.store.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomtomerUsersDetailsService service;

    private String userName=null;
    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpRequest.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup")){
            filterChain.doFilter(httpRequest,httpResponse);
        }else {
                String authorizationHeader= httpRequest.getHeader(SecurityConstants.HEADER_STRING);
                String token = null;

                if(authorizationHeader !=null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)){
                    token = authorizationHeader.substring(7);
                    userName = jwtUtil.extractUsername(token);
                    claims = jwtUtil.extractAllClaims(token);
                }

                if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = service.loadUserByUsername(userName);
                    if(jwtUtil.validatedToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails ,null,userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(httpRequest)
                        );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken );
                    }
                }
            filterChain.doFilter(httpRequest,httpResponse);
        }

    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }
}
