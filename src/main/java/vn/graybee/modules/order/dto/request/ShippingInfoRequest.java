package vn.graybee.modules.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingInfoRequest {

    @NotBlank(message = "Vui lòng chọn thành phố/tỉnh")
    @Size(max = 100, message = "Tên thành phố quá dài")
    private String city;

    @NotBlank(message = "Vui lòng chọn huyện/quận")
    @Size(max = 100, message = "Tên huyện quá dài")
    private String district;

    @NotBlank(message = "Vui lòng chọn xã/phường")
    @Size(max = 50, message = "Tên xã quá dài")
    private String commune;

    @NotBlank(message = "Vui lòng nhập địa chỉ cụ thể")
    @Size(max = 255, message = "Địa chỉ quá dài")
    private String streetAddress;

    @NotBlank(message = "Vui lòng chọn hình thức nhận hàng")
    private String deliveryType;

    @NotBlank(message = "Vui lòng chọn phương thức giao hàng")
    private String deliveryMethod;

}
