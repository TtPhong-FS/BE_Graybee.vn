package vn.graybee.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class MultipleException extends RuntimeException {

    private final Map<String, String> fieldErrors;

    public MultipleException(Map<String, String> fieldErrors) {
        super("Multiple exception");
        this.fieldErrors = fieldErrors;
    }


}
