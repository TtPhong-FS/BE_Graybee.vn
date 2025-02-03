package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "Select c.categoryName from Category c where c.categoryName = :categoryName")
    Optional<String> ensureCategoryNameBeforeCreate(@Param("categoryName") String categoryName);

    @Query(value = "select p.id from products p join categories c on p.category_id = c.id where c.id = :categoryId and c.is_delete = 'false' limit 1 ", nativeQuery = true)
    Optional<Long> checkProductIdExistsByCategoryId(@Param("categoryId") Long categoryId);

}
