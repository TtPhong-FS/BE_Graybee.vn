package vn.graybee.response.admin.directories.general;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.enums.DirectoryStatus;

import java.time.LocalDateTime;

public class UpdateStatusResponse {

    private int id;

    private DirectoryStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public UpdateStatusResponse() {
    }

    public UpdateStatusResponse(int id, DirectoryStatus status, LocalDateTime updatedAt) {
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

    public DirectoryStatus getStatus() {
        return status;
    }

    public void setStatus(DirectoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
