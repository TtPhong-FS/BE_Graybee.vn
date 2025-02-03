package vn.graybee.requests.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.ProductCreateRequest;

public class KeyboardDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @JsonProperty("key_material")
    private String keyMaterial = "unknown";

    @PositiveOrZero(message = "Cannot be a negative number")
    @Positive(message = "Must be a positive number")
    private int design = 100;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String connect;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("key_cap")
    private String keyCap;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("switch_type")
    private String switchType;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String compatible;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String feature = "unknown";

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String support = "unknown";

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String led = "unknown";

    public String getKeyMaterial() {
        return keyMaterial;
    }

    public void setKeyMaterial(String keyMaterial) {
        this.keyMaterial = keyMaterial;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        this.design = design;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getKeyCap() {
        return keyCap;
    }

    public void setKeyCap(String keyCap) {
        this.keyCap = keyCap;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getCompatible() {
        return compatible;
    }

    public void setCompatible(String compatible) {
        this.compatible = compatible;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
