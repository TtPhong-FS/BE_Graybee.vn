package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.projections.category.SubCategoryProjection;
import vn.graybee.projections.category.SubCategorySummaryProject;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("SELECT s FROM SubCategory s")
    List<SubCategoryProjection> fetchAll();

    @Query("Select s from SubCategory s where s.id = :id ")
    Optional<SubCategoryProjection> getById(@Param("id") int id);

    @Query("Select s.subcategoryName from SubCategory s where s.subcategoryName = :subcategoryName ")
    Optional<String> checkExistsByName(@Param("subcategoryName") String subcategoryName);

    @Query("Select s.id from SubCategory s where s.subcategoryName IN :subcategoryNames ")
    List<Integer> getIdByName(@Param("subcategoryNames") List<String> subcategoryNames);

    @Query(value = "SELECT cs.subcategory_id as Id,  s.subcategoryName as subcategoryName " +
            "FROM categories_subcategories cs join subcategories s on cs.subcategory_id = s.id WHERE cs.category_id = :categoryId", nativeQuery = true)
    List<SubCategorySummaryProject> findByCategoryId(@Param("categoryId") int categoryId);

}
