package vn.graybee.taxonomy.subcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto;
import vn.graybee.taxonomy.subcategory.model.Subcategory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    @Query("SELECT new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto(s) from Subcategory s")
    List<SubcategoryDto> fetchAll();

    @Transactional
    @Modifying
    @Query("delete from Subcategory s where s.id = :id ")
    void delete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("Update Subcategory s set s.status = :status where s.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") TaxonomyStatus status);

    @Query("SELECT EXISTS (SELECT 1 FROM Subcategory s WHERE s.name = :name AND s.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select s.id from Subcategory s where s.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Query("Select new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto(s) from Subcategory s where s.id = :id ")
    Optional<SubcategoryDto> getById(@Param("id") int id);

    @Query("Select s.name from Subcategory s where s.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Query("Select s.id from Subcategory s where s.id IN :ids and s.status != 'DELETED' ")
    Set<Integer> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto(s.id, s.name) " +
            "FROM Subcategory s " +
            "WHERE s.name IN :names AND s.status = :status")
    List<SubcategoryBasicDto> findByNamesAndStatus(@Param("names") List<String> names, @Param("status") TaxonomyStatus status);


    //    Public
    @Query("SELECT new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto(s.id, s.name) from Subcategory s")
    List<SubcategoryTagsDto> getAllForSidebar();

}
