package vn.graybee.requests.harddrive;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.product.ProductCreateRequest;

public class HardDriveDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 50, message = "Communication standard must be between 1 and 50 characters")
    @NotBlank(message = "Communication Standard cannot be blank")
    @JsonProperty("communication_standard")
    private String communicationStandard;

    @NotNull(message = "capacity cannot be null")
    @PositiveOrZero(message = "Capacity cannot be negative")
    private int capacity;

    @NotNull(message = "Hours to failure cannot be null")
    @JsonProperty("hours_to_failure")
    @PositiveOrZero(message = "Hours to failure cannot be negative")
    private int hoursToFailure = 0;

    @NotNull(message = "Reading speed cannot be null")
    @JsonProperty("reading_speed")
    @PositiveOrZero(message = "Reading speed cannot be negative")
    private int readingSpeed;

    @NotNull(message = "Writing speed cannot be null")
    @JsonProperty("writing_speed")
    @PositiveOrZero(message = "Writing speed cannot be negative")
    private int writingSpeed;

    public String getCommunicationStandard() {
        return communicationStandard;
    }

    public void setCommunicationStandard(String communicationStandard) {
        this.communicationStandard = communicationStandard;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHoursToFailure() {
        return hoursToFailure;
    }

    public void setHoursToFailure(int hoursToFailure) {
        this.hoursToFailure = hoursToFailure;
    }

    public int getReadingSpeed() {
        return readingSpeed;
    }

    public void setReadingSpeed(int readingSpeed) {
        this.readingSpeed = readingSpeed;
    }

    public int getWritingSpeed() {
        return writingSpeed;
    }

    public void setWritingSpeed(int writingSpeed) {
        this.writingSpeed = writingSpeed;
    }

}
