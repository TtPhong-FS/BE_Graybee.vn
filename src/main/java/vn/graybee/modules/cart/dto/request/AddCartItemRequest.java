package vn.graybee.modules.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCartItemRequest {

    @NotNull(message = "Vui lòng chọn sản phẩm")
    private long productId;

    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    @NotNull(message = "Vui lòng chọn số lượng")
    private int quantity;


}
