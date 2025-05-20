package vn.graybee.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.graybee.utils.MessageSourceUtil;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomAuthenticationEndpoint implements AuthenticationEntryPoint {

    private final MessageSourceUtil messageSourceUtil;

    public CustomAuthenticationEndpoint(MessageSourceUtil messageSourceUtil) {
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        System.out.println(">> CustomAuthEntryPoint triggered: " + authException.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, authException.getMessage());
        problem.setTitle(messageSourceUtil.get("error.401.title"));
        problem.setDetail(messageSourceUtil.get("error.401.detail"));
        problem.setInstance(URI.create(request.getRequestURI()));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), problem);
    }

}
