package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.model.Attribute;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("SELECT EXISTS (SELECT 1 FROM Attribute a WHERE LOWER(a.name) = LOWER(:name))")
    boolean existsByName(@Param("name") String name);

    @Query("Select exists (Select 1 from Attribute a where a.id = :id)")
    boolean existsById(@Param("id") long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeDto(a, null) from Attribute a where a.id = :id")
    Optional<AttributeDto> findAttributeDtoById(@Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeDto(a, null) from Attribute a")
    List<AttributeDto> findAllAttributeDto();

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto(a.id, a.name, a.label, a.isRequired, a.inputType) from Attribute a join CategoryAttribute ca on a.id = ca.attributeId where ca.categoryId = :categoryId")
    List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(@Param("categoryId") long categoryId);

    @Query("Select exists (Select 1 from Attribute a where LOWER(a.name) = LOWER(:name) and a.id <> :id)")
    boolean checkExistsByNameNotId(@Param("name") String name, @Param("id") long id);

    @Query("Select a.isActive from Attribute a where a.id = :id ")
    Optional<Boolean> getActiveById(long id);

    @Transactional
    @Modifying
    @Query("Update Attribute a set a.isActive = not a.isActive where a.id = :id")
    void toggleActiveById(long id);

    @Query("Select a.isRequired from Attribute a where a.id = :id ")
    Optional<Boolean> getRequiredById(long id);

    @Transactional
    @Modifying
    @Query("Update Attribute a set a.isRequired = not a.isRequired where a.id = :id")
    void toggleRequiredById(long id);

    @Query("""
            Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto(a.id, a.name, a.label, a.isRequired, a.inputType) 
            from Attribute a 
            join CategoryAttribute ca on a.id = ca.attributeId
            join Category c on c.id = ca.categoryId
            where c.name = :categoryName
            """)
    List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryName(String categoryName);

    @Transactional
    @Modifying
    @Query("Update Attribute a set a.inputType = :type where a.id = :id")
    void updateInputTypeByIdAndType(long id, String type);

}
