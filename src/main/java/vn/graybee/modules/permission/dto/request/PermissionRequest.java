package vn.graybee.modules.permission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionRequest {

    @NotBlank(message = "Tên quyền không thể trống")
    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String name;


    @Size(max = 150, message = "Độ dài tối đa không vượt quá 150 ký tự")
    private String description;

    private boolean isActive;


}
