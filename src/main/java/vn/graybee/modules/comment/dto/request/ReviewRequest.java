package vn.graybee.modules.comment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {

    private String comment;

    private int rating;

}
