package vn.graybee.requests.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class HddDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("communication_standard")
    private String communicationStandard;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int capacity;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @JsonProperty("hours_to_failure")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int hoursToFailure;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @JsonProperty("reading_speed")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int readingSpeed;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @JsonProperty("writing_speed")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int writingSpeed;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("noise_level")
    private float noiseLevel;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int cache;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
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
