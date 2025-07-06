package vn.graybee.auth.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import vn.graybee.auth.config.JwtProperties;
import vn.graybee.auth.service.JwtService;
import vn.graybee.auth.service.RedisAuthService;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtProperties jwtProperties;

    private final RedisAuthService redisAuthService;

    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String authHeader = request.getHeader(jwtProperties.getHeader());

        if (authHeader == null || !authHeader.startsWith(jwtProperties.getPrefix() + " ")) {
            return;
        }

        String token = authHeader.substring(7);

        String uid = jwtService.extractUsername(token);

        if (redisAuthService.isLogout(uid) || !redisAuthService.isExists(uid, token)) {
            return;
        }

        redisAuthService.updateStatusToken(uid);
    }

}
