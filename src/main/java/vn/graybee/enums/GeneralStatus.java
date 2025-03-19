package vn.graybee.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GeneralStatus {
    DRAFT,
    ACTIVE,
    INACTIVE,
    OUT_OF_STOCK,
    DISCONTINUED,
    ARCHIVED,
    DELETED,
    PENDING;

    @JsonCreator
    public static GeneralStatus fromString(String value) {
        return GeneralStatus.valueOf(value.toUpperCase());
    }
}
