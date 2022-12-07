package com.workshop.brianSec.config.jwt;

import com.workshop.brianSec.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Brian Barasa
 */

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    UserRepo userRepo;
    @Autowired
    JWTUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;

         // Parsing the JWT
        if (authHeader == null || authHeader.startsWith("Bearer")) {
            log.info("parsing JWT: ---->{}", authHeader);
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userRepo.findUserByEmail(userEmail);
            log.info("UserDetails: ----->{}", userDetails.toString());
            final Boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);

            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null
                );
            }
        }

    }
}
