package vn.graybee.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final ObjectMapper objectMapper;

    private final UserService userService;

    private final JwtServices jwtServices;

    private final RedisAuthServices redisAuthServices;

    private final JwtConfig jwtConfig;

    private final UserDetailServiceImpl userDetailService;

    public JwtFilter(ObjectMapper objectMapper, UserService userService, JwtServices jwtServices, RedisAuthServices redisAuthServices, JwtConfig jwtConfig, UserDetailServiceImpl userDetailService) {
        this.objectMapper = objectMapper;
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

//        try {
        String token = authHeader.substring(7);

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
        filterChain.doFilter(request, response);

//        } catch (RedisConnectionFailureException ex) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            response.getWriter().write("Redis server is not ready. Please try again later.");
//        } catch (ExpiredJwtException | SignatureException | MalformedJwtException ex) {
//
//            String message;
//
//            if (ex instanceof ExpiredJwtException) {
//                message = "Jwt token is expired. Please login again.";
//            } else if (ex instanceof SignatureException) {
//                message = "Jwt token is invalid. Please check your token.";
//            } else {
//                message = "Jwt token is malformed. Please check your token.";
//            }
//            logger.error("JWT error: {}", message);
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//
//            ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, message);
//            problem.setTitle("Unauthorized");
//            problem.setInstance(URI.create(request.getRequestURI()));
//
//            objectMapper.writeValue(response.getWriter(), problem);
//
//        }

    }


}
