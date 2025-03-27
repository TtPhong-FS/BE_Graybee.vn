package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.Category;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.response.admin.directories.category.CategoryProductCountResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryResponse(c, null, null) from Category c")
    List<CategoryResponse> fetchAll_ADMIN();

    @Transactional
    @Modifying
    @Query("delete from Category c where c.id = :id")
    void deleteById(@Param("id") int id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name AND c.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select c.name from Category c where c.id = :categoryId and c.status != 'DELETED'")
    Optional<String> getNameById(@Param("categoryId") int categoryId);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryResponse(c, null, null) from Category c where c.id = :id ")
    Optional<CategoryResponse> getById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryProductCountResponse(c.id, c.productCount) from Category c where c.id = :id ")
    Optional<CategoryProductCountResponse> checkExistsAndGetProductCountById(@Param("id") int id);

    @Query("Select c.name from Category c where c.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    //    public
    @Query("Select c from Category c")
    List<CategoryBasicInfoProjection> findAllCategories_public();

}
