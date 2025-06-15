package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional
    @Modifying
    @Query("Delete from Category c where c.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name AND c.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name)")
    boolean existsByName(@Param("name") String name);

    @Query("Select c.id from Category c where c.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategoryUpdateDto(c.id, c.name, (Select p.name from Category p where p.id = c.parentId), c.slug, c.categoryType, c.isActive) from Category c where c.id = :id")
    Optional<CategoryUpdateDto> findCategoryUpdateDtoById(Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySummaryDto(c.id, c.name, c.categoryType) from Category c where c.name = :name or c.id = :id")
    Optional<CategorySummaryDto> findCategorySummaryDtoByNameOrId(@Param("name") String name, @Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId(c.id, c.slug, c.name, c.parentId) from Category c where c.isActive = true ")
    List<CategorySlugWithParentId> findCategoryTreeDto();

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.slug = :slug)")
    boolean existsBySlug(@Param("slug") String slug);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySummaryDto(c.id, c.name, c.categoryType) from Category c where c.name in :names")
    List<CategorySummaryDto> findCategorySummaryDtoByNames(@Param("names") List<String> names);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategoryIdNameDto(c.id, c.name) from Category c where c.name in :categoryNames")
    List<CategoryIdNameDto> findCategoryIdNameDtoByNames(List<String> categoryNames);

    @Query("Select c.isActive from Category c where c.id = :id")
    Optional<Boolean> getActiveById(long id);

    @Transactional
    @Modifying
    @Query("Update Category c set c.isActive = not c.isActive where c.id = :id")
    void toggleActiveById(@Param("id") Long id);

    @Query("Select c.name from Category c where c.id = :id")
    Optional<String> findNameById(long id);

}
