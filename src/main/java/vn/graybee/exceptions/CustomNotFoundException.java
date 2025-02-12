package vn.graybee.exceptions;

public class CustomNotFoundException extends RuntimeException {

    private final String resource;

    public CustomNotFoundException(String resource, String message) {
        super(message);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

}
