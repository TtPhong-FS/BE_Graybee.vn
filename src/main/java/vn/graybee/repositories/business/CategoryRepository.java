package vn.graybee.repositories.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "Select c.categoryName from Category c where c.categoryName = :categoryName")
    Optional<String> checkCategoryNameExists(@Param("categoryName") String categoryName);

    @Query(value = "select p.id from products p join categories c on p.category_id = c.id where c.id = :categoryId and c.is_delete = 'false' limit 1 ", nativeQuery = true)
    Optional<Long> checkProductIdExists(@Param("categoryId") Long categoryId);

    @Query("Select c from Category c where c.categoryName = :categoryName")
    Optional<Category> findToCreateProduct(@Param("categoryName") String categoryName);

    @Query("Select c from Category c")
    Page<CategoryProjection> findAllCategories(Pageable pageable);


    //    public
    @Query("Select c from Category c")
    List<CategoryBasicInfoProjection> findAllCategories_public();

}
