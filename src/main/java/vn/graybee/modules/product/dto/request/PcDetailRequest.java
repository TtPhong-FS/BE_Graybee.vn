package vn.graybee.modules.product.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PcDetailRequest extends DetailTemplateRequest {

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String mainBoard;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String cpu;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String ram;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String vga;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự.")
    private String hdd;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String ssd;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String psu;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String caseName;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String cooling;

    private String ioPorts;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự.")
    private String os;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String connectivity;


}
