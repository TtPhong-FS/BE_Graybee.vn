package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategoryProductCountDto;
import vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.enums.CategoryStatus;
import vn.graybee.modules.catalog.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional
    @Modifying
    @Query("Update Category c set c.status = :status where c.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") CategoryStatus status);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name AND c.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name)")
    boolean existsByName(@Param("name") String name);

    @Query("Select exists (Select 1 from Category c where c.parentId = :parentId and c.id = :childrenId)")
    boolean checkExistsRelationByParentIdAndChildrenId(@Param("parentId") Long parentId, @Param("childrenId") Long childrenId);

    @Transactional
    @Modifying
    @Query("Update Category c set c.parentId = null where c.parentId = :parentId and c.id = :childrenId")
    void removeChildrenByParentIdAndChildrenId(@Param("parentId") Long parentId, @Param("childrenId") Long childrenId);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    Optional<String> findNameById(@Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySummaryDto(c.id, c.name, c.status) from Category c where c.id = :id")
    Optional<CategorySummaryDto> findNameAndStatusById(@Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategoryDto(c) from Category c where c.id = :id ")
    Optional<CategoryDto> findCategoryDtoById(@Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategoryProductCountDto(c.id, c.name, COALESCE(c.productCount, 0)) from Category c where c.id = :id ")
    Optional<CategoryProductCountDto> getProductCountById(@Param("id") Long id);

    @Query("Select c.name from Category c where c.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("Update Category c set c.productCount = c.productCount + 1 where c.id = :id")
    void increaseProductCountById(Long id);

    @Transactional
    @Modifying
    @Query("Update Category c set c.productCount = c.productCount - 1 where c.id = :id")
    void decreaseProductCountById(Long id);

    @Query("Select c.id from Category c where c.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);


    //    public

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId(c.id, c.slug, c.name, c.parentId) from Category c where c.status = 'ACTIVE' ")
    List<CategorySlugWithParentId> findCategoryTreeDto();

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategorySummaryDto(c.id, c.name, c.status) from Category c where c.name = :name")
    Optional<CategorySummaryDto> findCategorySummaryDtoByName(@Param("name") String name);


}
