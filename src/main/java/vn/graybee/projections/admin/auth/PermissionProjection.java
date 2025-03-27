package vn.graybee.projections.admin.auth;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface PermissionProjection {


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();

    int getId();

    String getName();

    int getUserCount();

    String getDescription();

    String getStatus();


}
