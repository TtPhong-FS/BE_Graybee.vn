package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.catalog.dto.response.CategoryBasicDto;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.model.ProductCategory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from Product p
            join ProductCategory pc on p.id = pc.productId
            join Category c on c.id = pc.tagId
            where p.status in ('PUBLISHED', 'COMING_SOON') and c.slug = :categorySlug
            """)
    List<ProductBasicResponse> findProductByTagSlug(@Param("categorySlug") String categorySlug);

    @Query("Select pc from ProductCategory pc where pc.productId = :productId")
    List<ProductCategory> findAllByProductId(@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("Delete from ProductCategory pc where pc.productId = :productId and pc.tagId in :categoryIds")
    void deleteByProductIdAndCategoryIdIn(Long productId, Set<Long> categoryIds);

    @Query("Select new vn.graybee.modules.catalog.dto.response.CategoryBasicDto(c.id, c.name) from Product p join Category c on p.categoryId = c.id where p.id = :productId")
    Optional<CategoryBasicDto> findCategoryBasicDtoByProductId(@Param("productId") long productId);

}
