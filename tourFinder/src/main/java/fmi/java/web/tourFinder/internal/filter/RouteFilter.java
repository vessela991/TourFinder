package fmi.java.web.tourFinder.internal.filter;

import fmi.java.web.tourFinder.businessLogic.exception.ForbiddenException;
import fmi.java.web.tourFinder.businessLogic.exception.UnauthorizedException;
import fmi.java.web.tourFinder.internal.util.Constants;
import fmi.java.web.tourFinder.internal.util.Routes;
import fmi.java.web.tourFinder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(3)
public class RouteFilter extends OncePerRequestFilter {

    @Autowired
    private AntPathMatcher antPathMatcher;

    private static final Set<Pair<HttpMethod, String>> basicTourRoutes = new HashSet<>(){{
        add(Pair.of(HttpMethod.GET, Routes.TOURS));
        add(Pair.of(HttpMethod.GET, Routes.TOURS_ID));
        add(Pair.of(HttpMethod.GET, Routes.TOURS_ID_PICTURES));
    }};

    private static final Set<Pair<HttpMethod, String>> anonymousRoutes = new HashSet<>(){{
        addAll(basicTourRoutes);
        add(Pair.of(HttpMethod.POST, Routes.LOGIN));
        add(Pair.of(HttpMethod.POST, Routes.USERS));
    }};

    private static final Set<Pair<HttpMethod, String>> userRoutes = new HashSet<>(){{
        addAll(basicTourRoutes);
        add(Pair.of(HttpMethod.GET, Routes.USERS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.USERS_ID));
        add(Pair.of(HttpMethod.PUT, Routes.USERS_ID));
        add(Pair.of(HttpMethod.POST, Routes.BOOKING_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> agencyRoutes = new HashSet<>(){{
        addAll(basicTourRoutes);
        add(Pair.of(HttpMethod.POST, Routes.TOURS));
        add(Pair.of(HttpMethod.PUT, Routes.TOURS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.TOURS_ID));
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        User user = (User) httpServletRequest.getAttribute(Constants.LOGGED_USER);

        if (user == null && !isRequestValid(httpServletRequest, anonymousRoutes)) {
            httpServletResponse.sendError(UnauthorizedException.instance().getStatusCode().value(), UnauthorizedException.instance().getMessage());
            return;
        }

        if (user == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (user.isUser() && !isRequestValid(httpServletRequest, userRoutes)) {
            httpServletResponse.sendError(ForbiddenException.instance().getStatusCode().value(), ForbiddenException.instance().getMessage());
            return;
        }

        if (user.isAgency() && !isRequestValid(httpServletRequest, agencyRoutes)) {
            httpServletResponse.sendError(ForbiddenException.instance().getStatusCode().value(), ForbiddenException.instance().getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isRequestValid(HttpServletRequest httpServletRequest, Set<Pair<HttpMethod, String>> routes) {
        for (Pair<HttpMethod, String> route: routes) {
            if (antPathMatcher.match(route.getSecond(), getRequestUri(httpServletRequest))
                    && antPathMatcher.match(String.valueOf(route.getFirst()), httpServletRequest.getMethod())) {
                return true;
            }
        }

        return false;
    }

    private String getRequestUri(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        if(url.endsWith("/"))
        {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }
}
