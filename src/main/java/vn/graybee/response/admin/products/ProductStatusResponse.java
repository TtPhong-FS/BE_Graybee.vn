package vn.graybee.response.admin.products;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ProductStatusResponse {

    private long id;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ProductStatusResponse(long id, String status, LocalDateTime updatedAt) {
        this.id = id;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
