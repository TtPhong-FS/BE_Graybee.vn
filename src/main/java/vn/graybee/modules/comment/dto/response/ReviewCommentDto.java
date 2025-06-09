package vn.graybee.modules.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCommentDto {

    private String poster;

    private float rating;

    private String comment;

    private LocalDateTime publishedAt;


}
