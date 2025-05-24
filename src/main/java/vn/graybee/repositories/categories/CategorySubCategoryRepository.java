package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.CategorySubCategory;
import vn.graybee.taxonomy.category.dto.response.CategoryIdSubcategoryIdDto;
import vn.graybee.taxonomy.category.dto.response.CategorySubcategoryBasicDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;

import java.util.List;
import java.util.Optional;

public interface CategorySubCategoryRepository extends JpaRepository<CategorySubCategory, Integer> {

    @Query("SELECT new vn.graybee.taxonomy.category.dto.response.CategorySubcategoryBasicDto(cs.categoryId, s.id,  s.name) " +
            "FROM CategorySubCategory cs " +
            "INNER JOIN Subcategory s ON cs.subCategoryId = s.id " +
            "WHERE cs.categoryId IN :categoryIds ORDER BY cs.categoryId")
    List<CategorySubcategoryBasicDto> findSubcategoriesByCategoryId_ADMIN(@Param("categoryIds") List<Integer> categoryIds);

    @Transactional
    @Modifying
    @Query("delete from CategorySubCategory cs where cs.categoryId = :categoryId and cs.subCategoryId = :subcategoryId")
    void deleteSubcategoryByCategoryById(@Param("categoryId") int categoryId, @Param("subcategoryId") int subcategoryId);

    @Modifying
    @Query("DELETE FROM CategorySubCategory cs WHERE cs.categoryId = :categoryId AND cs.subCategoryId NOT IN :subcategoryIds")
    void deleteByCategoryIdAndSubCategoryIdNotIn(@Param("categoryId") int categoryId, @Param("subcategoryIds") List<Integer> subcategoryIds);

    @Query("SELECT cs.subCategoryId FROM CategorySubCategory cs WHERE cs.categoryId = :categoryId")
    List<Integer> findSubcategoryIdsByCategoryId(@Param("categoryId") int categoryId);

    @Query("Select new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto(s.id, s.name) from CategorySubCategory cs join Subcategory s on cs.subCategoryId = s.id where cs.categoryId = :categoryId ORDER BY s.id ASC")
    List<SubcategoryBasicDto> findByCategoryId(@Param("categoryId") int categoryId);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryIdSubcategoryIdDto(cs.categoryId, cs.subCategoryId) from CategorySubCategory cs where cs.categoryId =:categoryId and cs.subCategoryId = :subCategoryId ")
    Optional<CategoryIdSubcategoryIdDto> findSubcategoryIdWithCategoryId(@Param("categoryId") int categoryId, @Param("subCategoryId") int subCategoryId);

    @Query("SELECT cs.subCategoryId FROM CategorySubCategory cs WHERE cs.categoryId = :categoryId AND cs.subCategoryId IN :subCategoryIds")
    List<Integer> findExistingSubCategoryIds(@Param("categoryId") Integer categoryId, @Param("subCategoryIds") List<Integer> subCategoryIds);


}
