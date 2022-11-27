package pl.agh.bsi.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.agh.bsi.backend.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoggedUsersFilter extends OncePerRequestFilter {
    private static final List<String> FORBIDDEN_URL = Arrays.asList(
            "/resource",
            "/anotherUrl"
    );

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        boolean isEndpointProtected = false;
        for (String protectedUrl: FORBIDDEN_URL
             ) {
            if (requestURI.startsWith(protectedUrl)){
                isEndpointProtected = true;
                break;
            }
        }

        String sessionId = request.getHeader("SessionId");

        if(isEndpointProtected &&
                (sessionId == null || !userService.isUserLogged(sessionId))){
            response.getWriter().write("YOU ARE NOT ALLOWED TO GET THERE!");
            response.setStatus(403);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
