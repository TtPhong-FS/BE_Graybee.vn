package vn.graybee.requests.manufacturer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ManufacturerCreateRequest {

    @NotEmpty(message = "Manufacturer's name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
