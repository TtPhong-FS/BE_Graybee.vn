package vn.graybee.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import vn.graybee.requests.collections.CpuDetailCreateRequest;
import vn.graybee.requests.collections.HddDetailCreateRequest;
import vn.graybee.requests.collections.KeyboardDetailCreateRequest;
import vn.graybee.requests.collections.LaptopDetailCreateRequest;
import vn.graybee.requests.collections.MonitorDetailCreateRequest;
import vn.graybee.requests.collections.MotherboardDetailCreateRequest;
import vn.graybee.requests.collections.MouseDetailCreateRequest;
import vn.graybee.requests.collections.PcDetailCreateRequest;
import vn.graybee.requests.collections.SsdDetailCreateRequest;
import vn.graybee.requests.collections.VgaDetailCreateRequest;
import vn.graybee.response.collections.RamDetailDtoResponse;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "detailType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RamDetailDtoResponse.class, name = "ram"),
        @JsonSubTypes.Type(value = SsdDetailCreateRequest.class, name = "ssd"),
        @JsonSubTypes.Type(value = HddDetailCreateRequest.class, name = "hdd"),
        @JsonSubTypes.Type(value = PcDetailCreateRequest.class, name = "pc"),
        @JsonSubTypes.Type(value = LaptopDetailCreateRequest.class, name = "laptop"),
        @JsonSubTypes.Type(value = MotherboardDetailCreateRequest.class, name = "motherboard"),
        @JsonSubTypes.Type(value = CpuDetailCreateRequest.class, name = "cpu"),
        @JsonSubTypes.Type(value = VgaDetailCreateRequest.class, name = "vga"),
        @JsonSubTypes.Type(value = MouseDetailCreateRequest.class, name = "mouse"),
        @JsonSubTypes.Type(value = KeyboardDetailCreateRequest.class, name = "keyboard"),
        @JsonSubTypes.Type(value = MonitorDetailCreateRequest.class, name = "monitor"),
})
public abstract class DetailDtoResponse {

}

