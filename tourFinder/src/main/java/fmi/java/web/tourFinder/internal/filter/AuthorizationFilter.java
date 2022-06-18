package fmi.java.web.tourFinder.internal.filter;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.UnauthorizedException;
import fmi.java.web.tourFinder.businessLogic.service.JwtService;
import fmi.java.web.tourFinder.internal.util.Constants;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = extractTokenFromHeader(httpServletRequest);

        if (jwt != null) {
            Result<String> userId = jwtService.getUserId(jwt);

            if (userId.hasNoValue()) {
                throw new ResponseStatusException(userId.getException().getStatusCode(), userId.getException().getMessage());
            }

            Optional<User> user = userRepository.findById(userId.getValue());

            if (user.isEmpty()) {
                UnauthorizedException unauthorizedException = UnauthorizedException.instance();
                throw new ResponseStatusException(unauthorizedException.getStatusCode(), unauthorizedException.getMessage());
            }

            httpServletRequest.setAttribute(Constants.LOGGED_USER, user.get());
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
