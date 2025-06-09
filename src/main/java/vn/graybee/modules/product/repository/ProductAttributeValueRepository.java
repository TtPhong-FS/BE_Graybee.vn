package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.product.model.ProductAttributeValue;

import java.util.List;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {

    @Query("Select p from ProductAttributeValue p where p.productId = :productId")
    List<ProductAttributeValue> findAllByProductId(long productId);

    @Query("""
            Select new vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto(a.label, pav.value)
            from ProductAttributeValue pav
            join Attribute a on pav.attributeId = a.id
            where pav.productId = :productId
            """)
    List<AttributeDisplayDto> findAttributeDisplayDtosByProductId(long productId);

}
