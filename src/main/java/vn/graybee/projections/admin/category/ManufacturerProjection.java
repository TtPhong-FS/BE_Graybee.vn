package vn.graybee.projections.admin.category;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface ManufacturerProjection {

    int getId();

    String getManufacturerName();

    String getStatus();

    int getProductCount();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

}
