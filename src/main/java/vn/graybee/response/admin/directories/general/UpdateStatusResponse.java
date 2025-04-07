package vn.graybee.response.admin.directories.general;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UpdateStatusResponse {

    private int id;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public UpdateStatusResponse() {
    }

    public UpdateStatusResponse(int id, String status, LocalDateTime updatedAt) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
