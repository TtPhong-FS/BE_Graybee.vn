package vn.graybee.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import vn.graybee.requests.ram.RamDetailCreateRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "detailType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RamDetailCreateRequest.class, name = "ram")
})
public abstract class DetailDtoRequest {

    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

}
