package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;

public interface SubcategoryTagServices {

    BasicMessageResponse<SubcategoryTagIdResponse> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId);

}
