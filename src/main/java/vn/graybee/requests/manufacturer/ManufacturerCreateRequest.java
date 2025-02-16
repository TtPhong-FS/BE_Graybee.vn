package vn.graybee.requests.manufacturer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ManufacturerCreateRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
