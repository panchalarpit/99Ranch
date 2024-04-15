package com.tawasupermarket.purchasemicroservice.security;

import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.model.UserModel;
import com.tawasupermarket.purchasemicroservice.security.service.AuthServiceImpl;
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

import static com.tawasupermarket.purchasemicroservice.PurchaseMicroserviceApplication.LOG;


public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthServiceImpl authService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        UserModel userModel = null;
        String jwt ;

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
            LOG.info("userId "+userModel.getUserId()+" is set in session");
        }
        filterChain.doFilter(request, response);
    }
}
