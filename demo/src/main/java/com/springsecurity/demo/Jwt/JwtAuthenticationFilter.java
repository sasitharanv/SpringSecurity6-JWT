package com.springsecurity.demo.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from HTTP request
        String token = getTokenFromRequest(request);

        // Validate the token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){

            // Get the username from the token
            String username = jwtTokenProvider.getUsername(token);

            // Load the user associated with the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create an authentication token for the user
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Typically, the password is not needed for token-based authentication
                    userDetails.getAuthorities()
            );

            // Set the authentication details
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        // Continue processing the request by passing it to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){

        // Get the token from the "Authorization" header
        String bearerToken = request.getHeader("Authorization");

        // Check if the token is present and starts with "Bearer "
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            // Extract and return the token part (excluding "Bearer ")
            return bearerToken.substring(7);
        }

        // If no token is found, return null
        return null;
    }
}
