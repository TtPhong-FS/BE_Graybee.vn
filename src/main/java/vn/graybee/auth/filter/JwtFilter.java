package vn.graybee.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.graybee.account.security.UserDetailService;
import vn.graybee.auth.config.JwtProperties;
import vn.graybee.auth.service.JwtService;
import vn.graybee.auth.service.RedisAuthService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;

    private final RedisAuthService redisAuthService;

    private final JwtProperties jwtProperties;

    private final UserDetailService userDetailService;

    public JwtFilter(JwtService jwtService, RedisAuthService redisAuthService, JwtProperties jwtProperties, UserDetailService userDetailService) {
        this.jwtService = jwtService;
        this.redisAuthService = redisAuthService;
        this.jwtProperties = jwtProperties;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(jwtProperties.getHeader());

        if (authHeader == null || !authHeader.startsWith(jwtProperties.getPrefix() + " ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);

            String uid = jwtService.extractUsername(token);

            if (uid != null && !uid.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (redisAuthService.isValidToken(uid, token)) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(uid);

                    if (jwtService.isValid(token, userDetails)) {

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }

            }

        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            throw ex;

        } //

        filterChain.doFilter(request, response);
    }


}
