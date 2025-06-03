package vn.graybee.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ProblemDetail;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomNotFoundProblemDetail extends ProblemDetail {

    public CustomNotFoundProblemDetail(int status, String title) {
        this.setStatus(status);
        this.setTitle(title);
    }

}
