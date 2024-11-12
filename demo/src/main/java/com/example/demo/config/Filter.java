package com.example.demo.config;

import com.example.demo.Entity.Account;
import com.example.demo.Service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Qualifier(value = "handlerExceptionResolver")
    @Autowired
    HandlerExceptionResolver resolver;
    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register",
            "/api/service",
            "/api/category",
            "/api/dashboard"
    );

    public boolean checkIsPublicAPI(String uri) {
        // uri: /api/register
        // nếu gặp những cái api trong list ở trên => cho phép truy cập lun => true
        AntPathMatcher patchMatch = new AntPathMatcher();
        // check token => false
        return AUTH_PERMISSION.stream().anyMatch(pattern -> patchMatch.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // xac thuc nguoi dung
        boolean isPublicAPI = checkIsPublicAPI(request.getRequestURI());

        if (isPublicAPI) {
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);
            if (token == null) {
                //Bao loi epmty token
                resolver.resolveException(request, response, null, new Exception("Empty token!"));
            }
            Account account;
            try {
                account = tokenService.getAccountByToken(token);
            } catch (ExpiredJwtException e) {
                resolver.resolveException(request, response, null, new Exception("Expired token!"));
                return;
            } catch (MalformedJwtException malformedJwtException){
                resolver.resolveException(request,response,null, new Exception("Invalid token!"));
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    account,
                    token,
                    account.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //token ok, cho vao
            filterChain.doFilter(request,response);
        }
        //TH1: public api

        //TH2: api phan quyen

    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }
}
