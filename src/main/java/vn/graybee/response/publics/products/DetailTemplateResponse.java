package vn.graybee.response.publics.products;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import vn.graybee.modules.product.dto.response.detail.PcDetailResponse;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "detailType", visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PcDetailResponse.class, name = "pc"),
})
public abstract class DetailTemplateResponse {


    public abstract String getDetailType();


}
