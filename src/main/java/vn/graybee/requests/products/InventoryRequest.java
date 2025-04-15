package vn.graybee.requests.products;

import jakarta.validation.constraints.NotBlank;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.InventoryStatus;
import vn.graybee.exceptions.BusinessCustomException;

public class InventoryRequest {

    private long productId;

    @NotBlank(message = "Trạng thái không thể trống")
    private String status;

    private int quantity;

    public InventoryStatus getStatusEnum() {
        try {
            return InventoryStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.status_invalid + status);
        }
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
