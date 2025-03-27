package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Transactional
    @Modifying
    @Query("Delete from ProductImage pi join Product p on pi.productId = p.id where pi.productId = :productId and pi.imageUrl NOT IN :imageUrls")
    void deleteByProductIdAndImageUrlNotIn(@Param("productId") long productId, @Param("imageUrls") List<String> imageUrls);

    @Query("Select pi.imageUrl from ProductImage pi join Product p on pi.productId = p.id where pi.productId = :productId ORDER BY pi.id ASC")
    List<String> findImageUrlsByProductId(@Param("productId") long productId);


}
