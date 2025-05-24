package vn.graybee.common.exception;

public class BusinessCustomException extends RuntimeException {

    private final String field;

    public BusinessCustomException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
