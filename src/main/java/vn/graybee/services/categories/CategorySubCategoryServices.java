package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;

public interface CategorySubCategoryServices {

    BasicMessageResponse<CategorySubcategoryIdResponse> deleteSubcategoryByCategoryId(int categoryId, int subcategoryId);

}
