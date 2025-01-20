package vn.graybee.models.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "keyboard_details")
public class KeyboardDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "key_material", length = 30)
    private String keyMaterial;

    private int design;

    @Column(length = 50)
    private String connect;

    @Column(name = "key_cap", length = 50)
    private String keyCap;

    @Column(name = "switch_type", length = 30)
    private String switchType;

    @Column(length = 30)
    private String compatible;

    @Column(length = 100)
    private String feature;

    @Column(length = 50)
    private String support;

    @Column(length = 30)
    private String led;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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

    public KeyboardDetail(Long id, String keyMaterial, int design, String connect, String keyCap, String switchType, String compatible, String feature, String support, String led) {
        this.id = id;
        this.keyMaterial = keyMaterial;
        this.design = design;
        this.connect = connect;
        this.keyCap = keyCap;
        this.switchType = switchType;
        this.compatible = compatible;
        this.feature = feature;
        this.support = support;
        this.led = led;
    }

    public KeyboardDetail() {
    }

}
