package vn.graybee.auth.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vn.graybee.common.utils.MessageSourceUtil;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomAccessDenied implements AccessDeniedHandler {

    private final MessageSourceUtil messageSourceUtil;

    public CustomAccessDenied(MessageSourceUtil messageSourceUtil) {
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                accessDeniedException.getMessage()
        );
        problem.setTitle(messageSourceUtil.get("common.error.403.title"));
        problem.setDetail(messageSourceUtil.get("common.error.403.detail"));
        problem.setInstance(URI.create(request.getRequestURI()));

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), problem);
    }

}
