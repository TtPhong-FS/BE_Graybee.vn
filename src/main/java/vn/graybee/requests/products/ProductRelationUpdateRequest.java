package vn.graybee.requests.products;

import java.util.Collections;
import java.util.List;

public class ProductRelationUpdateRequest {

    List<Integer> subcategories;

    List<Integer> tags;

    public List<Integer> getSubcategories() {
        return subcategories != null ? subcategories : Collections.emptyList();
    }

    public void setSubcategories(List<Integer> subcategories) {
        this.subcategories = subcategories;
    }

    public List<Integer> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

}
