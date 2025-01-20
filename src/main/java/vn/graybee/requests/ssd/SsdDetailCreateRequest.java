package vn.graybee.requests.ssd;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.harddrive.HardDriveDetailCreateRequest;

public class SsdDetailCreateRequest extends HardDriveDetailCreateRequest {

    @JsonProperty("storage_temperature")
    @NotBlank(message = "Storage Temperature cannot be blank")
    @Size(min = 1, max = 50, message = "Storage Temperature must be between 1 and 50 characters")
    private String storageTemperature = "unknown";

    @JsonProperty("operating_temperature")
    @NotBlank(message = "Operating Temperature cannot be blank")
    @Size(min = 1, max = 50, message = "Operating Temperature must be between 1 and 50 characters")
    private String operatingTemperature = "unknown";

    @JsonProperty("random_reading_speed")
    private int randomReadingSpeed;

    @JsonProperty("random_writing_speed")
    private int randomWritingSpeed;


    @JsonProperty("software")
    @Size(min = 1, max = 100, message = "Software must be between 1 and 100 characters")
    private String software = "unknown";

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
