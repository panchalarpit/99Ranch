package com.tawasupermarket.customermicroservice.security;

import com.tawasupermarket.customermicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.customermicroservice.model.UserModel;
import com.tawasupermarket.customermicroservice.security.service.AuthServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.tawasupermarket.customermicroservice.CustomerMicroserviceApplication.LOG;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    AuthServiceImpl authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        UserModel userModel = null;
        String jwt;

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                userModel = authService.validateUser(jwt);
            } catch (ExpiredJwtException e) {
                LOG.error(e.getMessage());
            } catch (ResourceNotFoundException e) {
                LOG.error(e.getMessage());
                throw new ResourceNotFoundException(e.getMessage());
            }
        }

        if (Objects.nonNull(userModel) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userModel, null, ((UserDetails) userModel).getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            HttpSession httpSession= request.getSession();
            httpSession.setAttribute("userId",userModel.getUserId());
            LOG.info("UserId "+userModel.getUserId()+" set in session");
        }
        filterChain.doFilter(request, response);
    }
}
