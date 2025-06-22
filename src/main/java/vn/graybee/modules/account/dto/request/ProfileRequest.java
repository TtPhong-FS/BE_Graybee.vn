package vn.graybee.modules.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ProfileRequest {

    @NotBlank(message = "{auth.validation.fullname.not.blank}")
    @Size(max = 100, message = "{auth.validation.fullname.size}")
    private String fullName;

    @Size(max = 12, message = "{auth.validation.phone.size}")
    private String phone;

    @NotNull(message = "Vui lòng nhập ngày/tháng/năm sinh")
    private Date birthday;

    @NotBlank(message = "{auth.validation.gender.not.blank}")
    private String gender;

}
