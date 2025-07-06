package vn.graybee.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.graybee.auth.config.JwtProperties;
import vn.graybee.auth.service.JwtService;
import vn.graybee.auth.service.RedisAuthService;
import vn.graybee.common.utils.ProblemDetailUtils;
import vn.graybee.modules.account.security.UserDetailService;

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

                if (!redisAuthService.isExists(uid, token)) {
                    ProblemDetailUtils.writeProblemDetail(request, response, HttpStatus.UNAUTHORIZED, "Token không hợp lệ. Vui lòng đăng nhập lại!");
                    return;
                }

                if (redisAuthService.isLogout(uid)) {
                    ProblemDetailUtils.writeProblemDetail(request, response, HttpStatus.UNAUTHORIZED, "Token đã đăng xuất. Không thể sử dụng lại!");
                    return;
                }

                UserDetails userDetails = userDetailService.loadUserByUsername(uid);

                if (jwtService.isValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
            }


        } catch (ExpiredJwtException ex) {
            ProblemDetailUtils.writeProblemDetail(request, response, HttpStatus.UNAUTHORIZED, "Token đã hết hạn. Vui lòng đăng nhập lại để tiếp tục!");
        } catch (JwtException ex) {
            ProblemDetailUtils.writeProblemDetail(request, response, HttpStatus.UNAUTHORIZED, "Token không hợp lệ. Vui lòng kiểm tra hoặc đăng nhập lại!");
        }
    }

}
