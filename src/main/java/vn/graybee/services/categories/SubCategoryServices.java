package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;

public interface SubCategoryServices {

    BasicMessageResponse<SubCategory> create(SubCategoryCreateRequest request);

    BasicMessageResponse<SubCategory> update(int subCategoryId, SubCategoryUpdateRequest request);

}
