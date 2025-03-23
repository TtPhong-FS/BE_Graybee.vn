package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.services.categories.CategorySubCategoryServices;

@Service
public class CategorySubCategoryServicesImpl implements CategorySubCategoryServices {

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    public CategorySubCategoryServicesImpl(CategorySubCategoryRepository categorySubCategoryRepository) {
        this.categorySubCategoryRepository = categorySubCategoryRepository;
    }

    @Override
    public BasicMessageResponse<CategorySubcategoryIdResponse> deleteSubcategoryByCategoryId(int categoryId, int subcategoryId) {
        CategorySubcategoryIdResponse response = categorySubCategoryRepository.findSubcategoryIdWithCategoryId(categoryId, subcategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.RELATIONSHIP_DOES_NOT_EXIST));

        categorySubCategoryRepository.deleteSubcategoryByCategoryById(categoryId, subcategoryId);

        return new BasicMessageResponse<>(200, "Xoá quan hệ thành công!", response);
    }

}
