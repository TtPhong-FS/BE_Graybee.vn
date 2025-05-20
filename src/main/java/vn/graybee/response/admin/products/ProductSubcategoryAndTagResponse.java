package vn.graybee.response.admin.products;

import vn.graybee.response.admin.directories.subcategory.SubcategoryDto;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public class ProductSubcategoryAndTagResponse {

    private long id;

    private String productName;

    private String productCode;

    private List<SubcategoryDto> subcategories;

    private List<TagResponse> tags;

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


    public List<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

}
