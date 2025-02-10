package vn.graybee.enums;

public enum Condition {
    NEW("NEW"),
    OLD("OLD");

    private final String description;

    Condition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
