package vn.graybee.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.security.SignatureException;

@Component
public class CustomAuthenticationEndpoint implements AuthenticationEntryPoint {

    private static String getMessage(Exception ex) {
        String message = "Bad Credentials.";

        if (ex instanceof ExpiredJwtException) {
            message = "JWT already expired. Please try again.";

        }
        if (ex instanceof SignatureException) {
            message = "JWT invalid signature. Please check your token.";

        } else if (ex instanceof MalformedJwtException) {
            message = "JWT invalid. Please check your token.";

        }
        return message;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception ex = (Exception) request.getAttribute("exception");

        String message = getMessage(ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                message
        );

        problem.setTitle("Unauthorized");
        problem.setDetail(message);

        problem.setInstance(URI.create(request.getRequestURI()));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), problem);
    }

}
