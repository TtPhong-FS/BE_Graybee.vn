package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.products.ProductTag;
import vn.graybee.response.products.ProductTagDto;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {

    @Query("SELECT new vn.graybee.response.products.ProductTagDto(pt.productId, t.tagName) " +
            "FROM ProductTag pt " +
            "INNER JOIN Tag t ON pt.tagId = t.id " +
            "WHERE pt.productId IN :productIds and pt.status = 'ACTIVE' ")
    List<ProductTagDto> findTagsByProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT new vn.graybee.response.products.ProductTagDto(pt.productId, t.tagName) " +
            "FROM ProductTag pt " +
            "INNER JOIN Tag t ON pt.tagId = t.id " +
            "WHERE pt.productId = :productId and pt.status = 'ACTIVE' ")
    List<ProductTagDto> findTagsByProductId(@Param("productId") long productId);

}
