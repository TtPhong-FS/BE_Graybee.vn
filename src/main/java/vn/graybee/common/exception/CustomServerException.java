package vn.graybee.common.exception;

import lombok.Getter;

@Getter
public class CustomServerException extends RuntimeException {

    private final String field;

    public CustomServerException(String field, String message) {
        super(message);
        this.field = field;
    }

}
