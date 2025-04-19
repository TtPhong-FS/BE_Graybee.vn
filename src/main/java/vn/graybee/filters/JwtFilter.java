package vn.graybee.filters;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.graybee.config.JwtConfig;
import vn.graybee.serviceImps.auth.JwtServices;
import vn.graybee.serviceImps.auth.RedisAuthServices;
import vn.graybee.serviceImps.users.UserDetailServiceImpl;
import vn.graybee.services.users.UserService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtServices jwtServices;

    private final RedisAuthServices redisAuthServices;

    private final JwtConfig jwtConfig;

    private final UserDetailServiceImpl userDetailService;

    public JwtFilter(UserService userService, JwtServices jwtServices, RedisAuthServices redisAuthServices, JwtConfig jwtConfig, UserDetailServiceImpl userDetailService) {
        this.userService = userService;
        this.jwtServices = jwtServices;
        this.redisAuthServices = redisAuthServices;
        this.jwtConfig = jwtConfig;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(jwtConfig.getHeader());

        if (authHeader == null || !authHeader.startsWith(jwtConfig.getPrefix() + " ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtServices.extractUsername(token);
            Integer uid = userService.getUidByUsername(username);

            if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (redisAuthServices.isValidToken(uid, token)) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    if (jwtServices.isValid(token, userDetails)) {

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }

            }
        } catch (JwtException ex) {
            request.setAttribute("exception", ex);
        }
        filterChain.doFilter(request, response);
    }


}
