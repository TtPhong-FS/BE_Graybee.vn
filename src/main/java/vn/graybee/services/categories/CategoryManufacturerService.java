package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.categories.CategoryWithManufacturersResponse;

import java.util.List;

public interface CategoryManufacturerService {

    BasicMessageResponse<List<CategoryWithManufacturersResponse>> fetchAll();

    BasicMessageResponse<Integer> deleteManufacturerByIdAndCategoryById(int manufacturerId, int categoryId);

}
