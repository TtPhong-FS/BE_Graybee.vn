package vn.graybee.modules.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForUpdateResponse {

    private long productId;

    private int quantity;

}
