package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.CategorySubCategory;
import vn.graybee.response.categories.SubCategorySummaryResponse;

import java.util.List;

public interface CategorySubCategoryRepository extends JpaRepository<CategorySubCategory, Integer> {

    @Query("SELECT new vn.graybee.response.categories.SubCategorySummaryResponse(cs.subCategory.id, cs.subCategory.name) " +
            "FROM CategorySubCategory cs WHERE cs.category.id = :categoryId")
    List<SubCategorySummaryResponse> findByCategoryId(@Param("categoryId") int categoryId);

}
