package vn.graybee.projections;

import java.time.LocalDateTime;

public interface ManufacturerProjection {

    Long getId();

    String getManufacturerName();

    boolean isDeleted();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}
