package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.projections.admin.category.SubCategoryProjection;
import vn.graybee.projections.admin.category.SubCategorySummaryProject;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.subcate.SubcateDto;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("SELECT s FROM SubCategory s")
    List<SubCategoryProjection> fetchAll();

    @Transactional
    @Modifying
    @Query("delete from SubCategory s where s.id = :id ")
    void delete(@Param("id") int id);

    @Query("Select s.id from SubCategory s where s.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.subcate.SubCategoryResponse(s, null) from SubCategory s where s.id = :id ")
    Optional<SubCategoryResponse> getById(@Param("id") int id);

    @Query("Select s.subcategoryName from SubCategory s where s.subcategoryName = :subcategoryName ")
    Optional<String> checkExistsByName(@Param("subcategoryName") String subcategoryName);

    @Query("Select s.id from SubCategory s where s.id IN :ids and s.status != 'DELETED' ")
    List<Integer> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("Select new vn.graybee.response.admin.directories.subcate.SubcateDto(s.id, s.subcategoryName) from SubCategory s where s.id IN :ids and s.status != 'DELETED' ")
    List<SubcateDto> findByIds(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT cs.subcategory_id as Id,  s.subcategoryName as subcategoryName " +
            "FROM categories_subcategories cs join subcategories s on cs.subcategory_id = s.id WHERE cs.category_id = :categoryId", nativeQuery = true)
    List<SubCategorySummaryProject> findByCategoryId(@Param("categoryId") int categoryId);

}
