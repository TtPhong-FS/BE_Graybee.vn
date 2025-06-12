package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryIdName;
import vn.graybee.modules.catalog.model.CategoryAttribute;

import java.util.List;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long> {

    @Query("Select ca from CategoryAttribute ca where ca.attributeId = :attributeId")
    List<CategoryAttribute> findAllByAttributeId(long attributeId);

    @Transactional
    @Modifying
    @Query("Delete from CategoryAttribute ca where ca.attributeId = :attributeId")
    void deleteByAttributeId(long attributeId);

    @Transactional
    @Modifying
    @Query("Delete from CategoryAttribute ca where ca.attributeId = :attributeId and ca.categoryId = :categoryId")
    void deleteByCategoryIdAndAttributeId(long categoryId, long attributeId);

    @Query("""
            Select new vn.graybee.modules.catalog.dto.response.CategoryIdNameDto(c.id, c.name) 
            from CategoryAttribute ca 
            join Category c on ca.categoryId = c.id 
            where ca.attributeId = attributeId
            """)
    List<CategoryIdNameDto> getAllCategoryIdNameByAttributeId(long attributeId);

    @Query("""
            Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryIdName(ca.attributeId, c.id, c.name) 
            from CategoryAttribute ca 
            join Category c on ca.categoryId = c.id 
            where ca.attributeId in :attributeIds
            """)
    List<AttributeIdCategoryIdName> findAllAttributeIdMapCategoryIdNameByAttributeIds(List<Long> attributeIds);

}
