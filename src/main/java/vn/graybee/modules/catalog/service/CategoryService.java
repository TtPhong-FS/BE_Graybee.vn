package vn.graybee.modules.catalog.service;

import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategoryIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryRequest request);

    Category updateCategory(Long id, CategoryRequest request);

    Long deleteById(Long id);

    List<CategoryDto> getAllCategoryDtoForDashboard();

    void checkExistsById(Long id);

    List<SidebarDto> getSidebar();

    CategoryUpdateDto getCategoryUpdateDtoById(Long id);

    void checkExistsBySlug(String categorySlug);

    List<CategorySummaryDto> findCategorySummaryDtoByNames(List<String> names);

    Long findCategoryIdByName(String categoryName);

    List<CategoryIdNameDto> getCategoryIdNameDtos(List<String> categoryNames);

    CategoryIdActiveResponse toggleActiveById(long id);

}
