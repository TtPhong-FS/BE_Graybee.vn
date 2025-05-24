package vn.graybee.response.admin.products;

import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;
import vn.graybee.taxonomy.tag.dto.response.TagDto;

import java.util.List;

public class ProductSubcategoryAndTagResponse {

    private long id;

    private String productName;

    private String productCode;

    private List<SubcategoryBasicDto> subcategories;

    private List<TagDto> tags;

    public ProductSubcategoryAndTagResponse() {
    }

    public ProductSubcategoryAndTagResponse(long id, String productName, String productCode) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<SubcategoryBasicDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryBasicDto> subcategories) {
        this.subcategories = subcategories;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

}
