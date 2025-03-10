package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.CategorySubCategory;
import vn.graybee.projections.category.CategorySubcategoryProjection;

import java.util.List;

public interface CategorySubCategoryRepository extends JpaRepository<CategorySubCategory, Integer> {

    @Query("SELECT cs FROM CategorySubCategory cs")
    List<CategorySubcategoryProjection> fetchAll();


    @Query("SELECT cs.subCategoryId FROM CategorySubCategory cs WHERE cs.categoryId = :categoryId AND cs.subCategoryId IN :subCategoryIds")
    List<Integer> findExistingSubCategoryIds(@Param("categoryId") Integer categoryId, @Param("subCategoryIds") List<Integer> subCategoryIds);


}
