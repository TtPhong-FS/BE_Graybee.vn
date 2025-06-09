package vn.graybee.modules.catalog.service;

import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;

import java.util.List;

public interface CategoryService {

    CategorySimpleDto createCategory(CategoryRequest request);

    CategorySimpleDto updateCategory(Long id, CategoryRequest request);

    Long deleteById(Long id);

    List<CategoryDto> getAllCategoryDtoForDashboard();

    UpdateStatusDto updateStatusById(Long id, String status);

    CategoryDto restoreById(Long id, UserDetail userDetail);

    void checkExistsById(Long id);

    CategorySummaryDto getCategorySummaryByNameOrId(String name, Long id);

    List<SidebarDto> getSidebar();

    CategoryUpdateDto getCategoryUpdateDtoById(Long id);

    void checkExistsBySlug(String categorySlug);

    List<CategorySummaryDto> findCategorySummaryDtoByNames(List<String> names);

    Long findCategoryIdByName(String categoryName);

}
