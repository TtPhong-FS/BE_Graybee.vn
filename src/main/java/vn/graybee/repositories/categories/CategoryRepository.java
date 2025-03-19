package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.category.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.response.categories.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c")
    List<CategoryProjection> fetchAll();

//    @Query("Select p.id from Category c ")
//    int countProductExists(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("delete from Category c where c.id = :id")
    void deleteByCategoryId(@Param("id") int id);

    @Query("Select c.id from Category c where c.categoryName = :categoryName and c.status = 'ACTIVE' ")
    Optional<Integer> getIdByName(@Param("categoryName") String categoryName);

    @Query("Select new vn.graybee.response.categories.CategoryResponse(c.createdAt, c.updatedAt, c) from Category c where c.id = :id ")
    Optional<CategoryResponse> getById(@Param("id") int id);

    @Query("Select c.productCount from Category c where c.id = :id ")
    Optional<Integer> getCountProductById(@Param("id") int id);

    @Query("Select c.id from Category c where c.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Query("Select c.categoryName from Category c where c.categoryName = :categoryName ")
    Optional<String> validateNameExists(@Param("categoryName") String categoryName);

    //    public
    @Query("Select c from Category c")
    List<CategoryBasicInfoProjection> findAllCategories_public();

}
