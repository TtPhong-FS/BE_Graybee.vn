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
@Table(name = "ssd_details")
public class SsdDetail {

    @Id
    @Column(name = "hard_drive_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "hard_drive_id")
    private HardDriveDetail hardDrive;

    @Column(name = "storage_temperature", length = 50)
    private String storageTemperature;

    @Column(name = "operating_temperature", length = 50)
    private String operatingTemperature;

    @Column(name = "random_reading_speed", nullable = false)
    private int randomReadingSpeed;

    @Column(name = "random_writing_speed", nullable = false)
    private int randomWritingSpeed;

    @Column(name = "software", length = 100)
    private String software;

    public SsdDetail() {
    }

    public SsdDetail(Long id, String storageTemperature, String operatingTemperature, int randomReadingSpeed, int randomWritingSpeed, String software) {
        this.id = id;
        this.storageTemperature = storageTemperature;
        this.operatingTemperature = operatingTemperature;
        this.randomReadingSpeed = randomReadingSpeed;
        this.randomWritingSpeed = randomWritingSpeed;
        this.software = software;
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
