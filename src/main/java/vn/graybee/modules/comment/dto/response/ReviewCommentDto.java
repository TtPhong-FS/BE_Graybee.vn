package vn.graybee.modules.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private int id;

    private long productId;

    private String uid;

    private String poster;

    private int rating;

    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishedAt;

}
