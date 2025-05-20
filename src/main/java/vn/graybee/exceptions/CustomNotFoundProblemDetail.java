package vn.graybee.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ProblemDetail;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomNotFoundProblemDetail extends ProblemDetail {

    public CustomNotFoundProblemDetail(int status, String title, String resource) {
        this.setStatus(status);
        this.setTitle(title);
        this.setProperty("resource", resource);
    }

}
