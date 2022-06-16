package fmi.java.web.tourFinder.filter;

import fmi.java.web.tourFinder.exception.ForbiddenException;
import fmi.java.web.tourFinder.exception.UnauthorizedException;
import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.util.CommonUtils;
import fmi.java.web.tourFinder.util.Routes;
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

    private static final Set<Pair<HttpMethod, String>> anonymousRoutes = new HashSet<>(){{
        add(Pair.of(HttpMethod.POST, Routes.LOGIN));
        add(Pair.of(HttpMethod.POST, Routes.USERS));
        add(Pair.of(HttpMethod.GET, Routes.TOURS));
        add(Pair.of(HttpMethod.GET, Routes.TOURS_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> basicUserRoutes = new HashSet<>(){{
        add(Pair.of(HttpMethod.GET, Routes.USERS));
        add(Pair.of(HttpMethod.GET, Routes.USERS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.USERS_ID));
        add(Pair.of(HttpMethod.PUT, Routes.USERS_ID));
        add(Pair.of(HttpMethod.GET, Routes.TOURS));
        add(Pair.of(HttpMethod.GET, Routes.TOURS_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> agencyRoutes = new HashSet<>(){{
        addAll(basicUserRoutes);
        add(Pair.of(HttpMethod.POST, Routes.TOURS));
        add(Pair.of(HttpMethod.PUT, Routes.TOURS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.TOURS_ID));
    }};

    private static final Set<Pair<HttpMethod, String>> adminRoutes = new HashSet<>(){{
        addAll(agencyRoutes);
        add(Pair.of(HttpMethod.POST, Routes.USERS));
        add(Pair.of(HttpMethod.DELETE, Routes.USERS_ID));

        add(Pair.of(HttpMethod.POST, Routes.TOURS));
        add(Pair.of(HttpMethod.PUT, Routes.TOURS_ID));
        add(Pair.of(HttpMethod.DELETE, Routes.TOURS_ID));
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        User user = CommonUtils.getLoggedUser(httpServletRequest);

        if (user == null) {
            if (!isRequestValid(httpServletRequest, anonymousRoutes)) {
                throw UnauthorizedException.create();
            }
        } else if (Role.USER.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, basicUserRoutes)) {
                throw ForbiddenException.create();
            }
        } else if (Role.AGENCY.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, agencyRoutes)) {
                throw ForbiddenException.create();
            }
        } else if (Role.ADMIN.equals(user.getRole())) {
            if (!isRequestValid(httpServletRequest, adminRoutes)) {
                throw ForbiddenException.create();
            }
        } else {
            throw ForbiddenException.create();
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
