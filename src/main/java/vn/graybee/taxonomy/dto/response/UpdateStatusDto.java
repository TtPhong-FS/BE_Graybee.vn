package vn.graybee.taxonomy.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.taxonomy.enums.TaxonomyStatus;

import java.time.LocalDateTime;

public class UpdateStatusDto {

    private int id;

    private TaxonomyStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public UpdateStatusDto() {
    }

    public UpdateStatusDto(int id, TaxonomyStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaxonomyStatus getStatus() {
        return status;
    }

    public void setStatus(TaxonomyStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
