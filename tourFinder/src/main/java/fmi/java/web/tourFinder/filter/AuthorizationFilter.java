package fmi.java.web.tourFinder.filter;

import fmi.java.web.tourFinder.exception.UnauthorizedException;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import fmi.java.web.tourFinder.service.JwtService;
import fmi.java.web.tourFinder.util.Constants;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final Integer TOKEN_SUBSTRING = 7;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractTokenFromHeader(httpServletRequest);

        if (jwt != null) {
            Claims claims = jwtService.getClaims(jwt);
            if (claims == null) {
                throw UnauthorizedException.create();
            }

            if (!jwtService.validateClaims(claims)) {
                throw UnauthorizedException.create();
            }

            String userId = jwtService.getUserId(claims);
            User user = userRepository.findById(userId).orElseThrow(UnauthorizedException::create);
            httpServletRequest.setAttribute(Constants.LOGGED_USER, user);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractTokenFromHeader(HttpServletRequest httpServletRequest) {
        final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            return authHeader.substring(TOKEN_SUBSTRING);
        }
        return null;
    }
}
