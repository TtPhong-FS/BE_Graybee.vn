package vn.graybee.requests.collections;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class HddDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String communicationStandard;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int capacity;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int lifeSpan;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int readingSpeed;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int writingSpeed;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    private float noiseLevel;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int cache;

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

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
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


}
