package vn.graybee.modules.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerInfoRequest {

    @NotBlank(message = "Vui lòng nhập tên người nhận")
    private String recipientName;

    @NotBlank(message = "Vui lòng nhập số điện thoại người nhận")
    private String recipientPhone;

    @Size(max = 200, message = "Địa chỉ email quá dài, hãy dưới 200 ký tự")
    private String email;

    @Size(max = 200, message = "Ghi chú quá dài, hãy dưới 200 ký tự")
    private String note;

}
