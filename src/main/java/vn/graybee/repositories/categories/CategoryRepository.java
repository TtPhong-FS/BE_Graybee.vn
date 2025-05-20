package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.models.directories.Category;
import vn.graybee.response.admin.directories.category.CategoryProductCountResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategoryStatusDto;
import vn.graybee.response.publics.sidebar.SidebarDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryResponse(c) from Category c")
    List<CategoryResponse> fetchAll();

    @Transactional
    @Modifying
    @Query("Update Category c set c.status = :status where c.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") DirectoryStatus status);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name AND c.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("SELECT EXISTS (SELECT 1 FROM Category c WHERE c.name = :name)")
    boolean checkExistsByName(@Param("name") String name);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    Optional<String> getNameById(@Param("id") Integer id);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryStatusDto(c.id, c.name, c.status) from Category c where c.id = :id")
    Optional<CategoryStatusDto> findNameAndStatusById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryStatusDto(c.id, c.name, c.status) from Category c where c.name = :name")
    Optional<CategoryStatusDto> findNameAndStatusByName(@Param("name") String name);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryResponse(c) from Category c where c.id = :id ")
    Optional<CategoryResponse> getById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryProductCountResponse(c.id, c.name, COALESCE(c.productCount, 0)) from Category c where c.id = :id ")
    Optional<CategoryProductCountResponse> getProductCountById(@Param("id") int id);

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

    @Query("Select new vn.graybee.response.publics.sidebar.SidebarDto(c.id, c.name) from Category c where c.status = 'ACTIVE' ")
    List<SidebarDto> getSidebar();

}
