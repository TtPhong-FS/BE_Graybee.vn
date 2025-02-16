package vn.graybee.repositories.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("Select c from Category c where c.name = :name")
    Optional<Category> findToCreateProduct(@Param("name") String name);

    @Query("Select c from Category c")
    Page<CategoryProjection> findAllCategories(Pageable pageable);


    //    public
    @Query("Select c from Category c")
    List<CategoryBasicInfoProjection> findAllCategories_public();

}
