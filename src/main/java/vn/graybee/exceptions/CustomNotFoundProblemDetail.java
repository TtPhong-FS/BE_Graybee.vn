package vn.graybee.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ProblemDetail;

import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomNotFoundProblemDetail extends ProblemDetail {

    public CustomNotFoundProblemDetail(int status, String title, String resource) {
        this.setType(URI.create("http://localhost:8080/errors/not-found"));
        this.setStatus(status);
        this.setTitle(title);
        this.setProperty("resource", resource);
    }

}
