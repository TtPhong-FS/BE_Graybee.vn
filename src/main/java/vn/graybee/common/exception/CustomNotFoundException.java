package vn.graybee.common.exception;

public class CustomNotFoundException extends RuntimeException {

    private final String field;


    public CustomNotFoundException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
