package vn.graybee.projections.admin.category;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface SubCategoryProjection {

    int getId();

    String getSubcategoryName();

    String getStatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
