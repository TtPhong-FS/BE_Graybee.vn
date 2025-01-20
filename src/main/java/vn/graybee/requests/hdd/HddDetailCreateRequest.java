package vn.graybee.requests.hdd;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import vn.graybee.requests.harddrive.HardDriveDetailCreateRequest;

public class HddDetailCreateRequest extends HardDriveDetailCreateRequest {

    @NotNull(message = "Noise level cannot be null")
    @PositiveOrZero(message = "Noise level cannot be negative")
    @JsonProperty("noise_level")
    private float noiseLevel;

    @NotNull(message = "Cache cannot be null")
    @PositiveOrZero(message = "Cache cannot be negative")
    private int cache;

    @NotNull(message = "Revolution per minutes cannot be null")
    @PositiveOrZero(message = "Revolution per minutes cannot be negative")
    @JsonProperty("revolution_per_minutes")
    private int revolutionPerMinutes;

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
