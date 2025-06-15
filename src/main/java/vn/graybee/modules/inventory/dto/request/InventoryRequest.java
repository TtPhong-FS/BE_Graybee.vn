package vn.graybee.modules.inventory.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryRequest {

    @NotNull
    private long productId;

    @NotNull
    private int quantity;
    
}
