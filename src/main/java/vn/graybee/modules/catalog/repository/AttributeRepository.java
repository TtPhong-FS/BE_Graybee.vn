package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.model.Attribute;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("Select exists (Select 1 from Attribute a where a.name = :name and a.categoryId = :categoryId)")
    boolean existsByNameAndCategoryId(@Param("name") String name, @Param("categoryId") long categoryId);

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeDto(a, c.name) from Attribute a join Category c on a.categoryId = c.id where a.id = :id")
    Optional<AttributeDto> findAttributeDtoById(@Param("id") Long id);

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeDto(a, c.name) from Attribute a join Category c on a.categoryId = c.id")
    List<AttributeDto> findAllAttributeDto();

    @Query("Select a.name from Attribute a where a.categoryId = :categoryId")
    Set<String> findAllNamesByCategoryId(@Param("categoryId") long categoryId);

    @Query("Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto(a.id, a.name, a.required, a.inputType, a.options) from Attribute a where a.categoryId = :categoryId")
    List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(Long categoryId);

}
