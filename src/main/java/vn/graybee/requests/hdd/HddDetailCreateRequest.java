package vn.graybee.requests.hdd;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.ProductCreateRequest;

public class HddDetailCreateRequest extends ProductCreateRequest {

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

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("noise_level")
    private float noiseLevel;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int cache;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("revolution_per_minutes")
    private int revolutionPerMinutes;

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

    public float getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public int getRevolutionPerMinutes() {
        return revolutionPerMinutes;
    }

    public void setRevolutionPerMinutes(int revolutionPerMinutes) {
        this.revolutionPerMinutes = revolutionPerMinutes;
    }

}
