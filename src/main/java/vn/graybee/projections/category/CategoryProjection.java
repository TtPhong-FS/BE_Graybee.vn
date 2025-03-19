package vn.graybee.projections.category;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface CategoryProjection {

    int getId();

    String getCategoryName();

    String getStatus();

    int getProductCount();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
