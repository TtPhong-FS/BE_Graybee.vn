package vn.graybee.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import vn.graybee.requests.cpu.CpuDetailCreateRequest;
import vn.graybee.requests.handheld.HandheldDetailCreateRequest;
import vn.graybee.requests.hdd.HddDetailCreateRequest;
import vn.graybee.requests.heatDissipation.HeatDissipationDetailCreateRequest;
import vn.graybee.requests.keyboard.KeyboardDetailCreateRequest;
import vn.graybee.requests.laptop.LaptopDetailCreateRequest;
import vn.graybee.requests.monitor.MonitorDetailCreateRequest;
import vn.graybee.requests.motherboard.MotherboardDetailCreateRequest;
import vn.graybee.requests.mouse.MouseDetailCreateRequest;
import vn.graybee.requests.pc.PcDetailCreateRequest;
import vn.graybee.requests.psu.PsuDetailCreateRequest;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.requests.ssd.SsdDetailCreateRequest;
import vn.graybee.requests.vga.VgaDetailCreateRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "detailType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RamDetailCreateRequest.class, name = "ram"),
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
        @JsonSubTypes.Type(value = PsuDetailCreateRequest.class, name = "psu"),
        @JsonSubTypes.Type(value = HeatDissipationDetailCreateRequest.class, name = "heat dissipation"),
        @JsonSubTypes.Type(value = HandheldDetailCreateRequest.class, name = "handheld"),
})
public abstract class DetailDtoRequest {


}

