package vn.graybee.projections;

import java.time.LocalDateTime;

public interface CategoryProjection {

    Long getId();

    String getCategoryName();

    boolean isDeleted();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}
