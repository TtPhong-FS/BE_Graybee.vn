package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    @Query("Select p.id from Category c ")
//    int countProductExists(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("delete from Category c where c.id = :id")
    void deleteByCategoryId(@Param("id") int id);


    @Query("Select c.id from Category c where c.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Query("Select c from Category c where c.name = :name")
    Optional<Category> findToCreateProduct(@Param("name") String name);

    @Query("SELECT c FROM Category c")
    List<CategoryProjection> fetchCategories();

    @Query("Select c.name from Category c where c.name = :name ")
    Optional<String> validateNameExists(@Param("name") String name);

    //    public
    @Query("Select c from Category c")
    List<CategoryBasicInfoProjection> findAllCategories_public();

}
