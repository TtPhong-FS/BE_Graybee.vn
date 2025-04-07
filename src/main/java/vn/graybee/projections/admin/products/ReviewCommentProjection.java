package vn.graybee.projections.admin.products;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface ReviewCommentProjection {

    int getId();

    long getProductId();

    int getUserId();

    float getRating();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getPublishedAt();

}
