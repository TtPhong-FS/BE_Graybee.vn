package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryWithManufacturersResponse;

import java.util.List;

public interface CategoryManufacturerService {

    BasicMessageResponse<List<CategoryWithManufacturersResponse>> fetchAll();

    BasicMessageResponse<CategoryManufacturerIdResponse> deleteManufacturerByIdAndCategoryById(int manufacturerId, int categoryId);

}
