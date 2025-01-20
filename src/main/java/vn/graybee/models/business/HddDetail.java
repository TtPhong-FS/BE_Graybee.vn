package vn.graybee.models.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hdd_details")
public class HddDetail {

    @Id
    @Column(name = "hard_drive_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "hard_drive_id")
    private HardDriveDetail hardDrive;

    @Column(name = "noise_level", nullable = false)
    private float noiseLevel;

    private int cache;

    @Column(name = "revolution_per_minutes")
    private int revolutionPerMinutes;

    public HddDetail() {
    }

    public float getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public HddDetail(HardDriveDetail hardDrive, float noiseLevel, int cache, int revolutionPerMinutes) {
        this.hardDrive = hardDrive;
        this.noiseLevel = noiseLevel;
        this.cache = cache;
        this.revolutionPerMinutes = revolutionPerMinutes;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardDriveDetail getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(HardDriveDetail hardDrive) {
        this.hardDrive = hardDrive;
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
