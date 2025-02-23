package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.categories.SubCategorySummaryResponse;

import java.util.List;

public interface CategorySubCategoryServices {

    BasicMessageResponse<List<SubCategorySummaryResponse>> findByCategoryId(int categoryId);

}
