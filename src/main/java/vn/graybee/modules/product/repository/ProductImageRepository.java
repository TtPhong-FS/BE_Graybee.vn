package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.product.model.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Transactional
    @Modifying
    @Query("Delete from ProductImage pi join Product p on pi.productId = p.id where pi.productId = :productId and pi.imageUrl IN :imageUrls")
    void deleteByProductIdAndImageUrlIn(@Param("productId") long productId, @Param("imageUrls") List<String> imageUrls);

    @Transactional
    @Modifying
    @Query("Delete from ProductImage pi where pi.productId = :productId")
    void deleteByProductId(@Param("productId") long productId);
    
    @Query("Select pi.imageUrl from ProductImage pi where pi.productId = :productId")
    List<String> findAllImageUrlsByProductId(@Param("productId") Long productId);

    @Query("Select pi from ProductImage pi where pi.productId = :productId")
    List<ProductImage> findAllProductImageByProductId(Long productId);

}
