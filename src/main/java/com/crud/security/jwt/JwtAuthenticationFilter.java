package com.crud.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        System.out.println(">>>>>>>>>>>>OncePerRequestFilter>>>>>>>>>>>>>>");
        System.out.println("requestToken>>>" + requestToken);
        String userName = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);
            try {
                userName = jwtTokenHelper.getUsernameFromToken(token);
            } catch (Exception e) {
                e.printStackTrace();
//                throw new NotFountException("UserName not found from Token");
                System.out.println("UserName not found from Token");
            }
        } else {
//            throw new NotFountException("Token Not Found");
            System.out.println(">>>Token Not Found>>>");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
//                throw new NestedServletException("Invalid jwt Token");
                System.out.println("Invalid jwt Token");
            }
        } else {
//            throw new NotFountException("UserName Null or spring context null");
            System.out.println("UserName Null or spring context null");
        }
        filterChain.doFilter(request, response);
    }
}
