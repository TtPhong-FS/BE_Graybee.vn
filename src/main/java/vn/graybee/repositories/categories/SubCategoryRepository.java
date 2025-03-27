package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.SubCategory;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.subcate.SubcateDto;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("SELECT new vn.graybee.response.admin.directories.subcate.SubCategoryResponse(s, null) from SubCategory s")
    List<SubCategoryResponse> fetchAll();

    @Transactional
    @Modifying
    @Query("delete from SubCategory s where s.id = :id ")
    void delete(@Param("id") int id);

    @Query("SELECT EXISTS (SELECT 1 FROM SubCategory s WHERE s.name = :name AND s.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select s.id from SubCategory s where s.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.subcate.SubCategoryResponse(s, null) from SubCategory s where s.id = :id ")
    Optional<SubCategoryResponse> getById(@Param("id") int id);

    @Query("Select s.name from SubCategory s where s.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Query("Select s.id from SubCategory s where s.id IN :ids and s.status != 'DELETED' ")
    List<Integer> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("Select new vn.graybee.response.admin.directories.subcate.SubcateDto(s.id, s.name) from SubCategory s where s.id IN :ids and s.status != 'DELETED' ")
    List<SubcateDto> findByIds(@Param("ids") List<Integer> ids);

}
