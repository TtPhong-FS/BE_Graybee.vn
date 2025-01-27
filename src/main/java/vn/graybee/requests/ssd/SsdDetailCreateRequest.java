package vn.graybee.requests.ssd;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.product.ProductCreateRequest;

public class SsdDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("communication_standard")
    private String communicationStandard;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int capacity;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("hours_to_failure")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int hoursToFailure = 0;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("reading_speed")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int readingSpeed;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("writing_speed")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int writingSpeed;

    @JsonProperty("storage_temperature")
    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String storageTemperature = "unknown";

    @JsonProperty("operating_temperature")
    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String operatingTemperature = "unknown";

    @PositiveOrZero(message = "Cannot be a negative number")
    @Positive(message = "Must be a positive number")
    @JsonProperty("random_reading_speed")
    private int randomReadingSpeed;

    @PositiveOrZero(message = "Cannot be a negative number")
    @Positive(message = "Must be a positive number")
    @JsonProperty("random_writing_speed")
    private int randomWritingSpeed;

    @JsonProperty("software")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String software = "unknown";

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

    public String getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(String storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    public String getOperatingTemperature() {
        return operatingTemperature;
    }

    public void setOperatingTemperature(String operatingTemperature) {
        this.operatingTemperature = operatingTemperature;
    }

    public int getRandomReadingSpeed() {
        return randomReadingSpeed;
    }

    public void setRandomReadingSpeed(int randomReadingSpeed) {
        this.randomReadingSpeed = randomReadingSpeed;
    }

    public int getRandomWritingSpeed() {
        return randomWritingSpeed;
    }

    public void setRandomWritingSpeed(int randomWritingSpeed) {
        this.randomWritingSpeed = randomWritingSpeed;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

}
