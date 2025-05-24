package vn.graybee.taxonomy.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.taxonomy.category.dto.response.CategoryDto;
import vn.graybee.taxonomy.category.dto.response.CategoryProductCountDto;
import vn.graybee.taxonomy.category.dto.response.CategoryStatusDto;
import vn.graybee.taxonomy.category.dto.response.SidebarDto;
import vn.graybee.taxonomy.category.model.Category;
import vn.graybee.taxonomy.enums.TaxonomyStatus;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryDto(c) from Category c")
    List<CategoryDto> fetchAll();

    @Transactional
    @Modifying
    @Query("Update Category c set c.status = :status where c.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") TaxonomyStatus status);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name AND c.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name)")
    boolean checkExistsByName(@Param("name") String name);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id and c.status = :status")
    Optional<String> getNameById(@Param("id") Integer id, @Param("status") TaxonomyStatus status);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryStatusDto(c.id, c.name, c.status) from Category c where c.id = :id")
    Optional<CategoryStatusDto> findNameAndStatusById(@Param("id") int id);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryStatusDto(c.id, c.name, c.status) from Category c where c.name = :name")
    Optional<CategoryStatusDto> findNameAndStatusByName(@Param("name") String name);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryDto(c) from Category c where c.id = :id ")
    Optional<CategoryDto> getById(@Param("id") int id);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryProductCountDto(c.id, c.name, COALESCE(c.productCount, 0)) from Category c where c.id = :id ")
    Optional<CategoryProductCountDto> getProductCountById(@Param("id") int id);

    @Query("Select c.name from Category c where c.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("Update Category c set c.productCount = c.productCount + 1 where c.id = :id")
    void increaseProductCountById(int id);

    @Transactional
    @Modifying
    @Query("Update Category c set c.productCount = c.productCount - 1 where c.id = :id")
    void decreaseProductCountById(int id);

    //    public

    @Query("Select new vn.graybee.taxonomy.category.dto.response.SidebarDto(c.id, c.name) from Category c where c.status = :status ")
    List<SidebarDto> getSidebar(@Param("status") TaxonomyStatus status);

}
