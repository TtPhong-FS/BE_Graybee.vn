package vn.graybee.product.dto.request.detail;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "detailType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PcDetailRequest.class, name = "pc"),
//        @JsonSubTypes.Type(value = SsdDetailCreateRequest.class, name = "ssd"),
//        @JsonSubTypes.Type(value = HddDetailCreateRequest.class, name = "hdd"),
//        @JsonSubTypes.Type(value = PcDetailCreateRequest.class, name = "pc"),
//        @JsonSubTypes.Type(value = LaptopDetailCreateRequest.class, name = "laptop"),
//        @JsonSubTypes.Type(value = MotherboardDetailCreateRequest.class, name = "motherboard"),
//        @JsonSubTypes.Type(value = CpuDetailCreateRequest.class, name = "cpu"),
//        @JsonSubTypes.Type(value = VgaDetailCreateRequest.class, name = "vga"),
//        @JsonSubTypes.Type(value = MouseDetailCreateRequest.class, name = "mouse"),
//        @JsonSubTypes.Type(value = KeyboardDetailCreateRequest.class, name = "keyboard"),
//        @JsonSubTypes.Type(value = MonitorDetailCreateRequest.class, name = "monitor")
})
public abstract class DetailTemplateRequest {


}
