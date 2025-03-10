package vn.graybee.projections.category;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface CategorySubcategoryProjection {


    int getId();

    int getCategoryId();

    int getSubCategoryId();

    String getStatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
