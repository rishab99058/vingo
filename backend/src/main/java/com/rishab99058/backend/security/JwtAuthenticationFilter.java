package com.rishab99058.backend.security;

import com.rishab99058.backend.entity.UserEntity;
import com.rishab99058.backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

         final String jwt = request.getHeader("Authorization");
        if(jwt == null || !jwt.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = jwt.replace("Bearer ", "");
        String email = jwtService.getEmailFromJwtToken(jwtToken);
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserEntity userEntity  = userRepository.findByEmailAndDeletedAtNull(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEntity,null, null);
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);


    }
}

