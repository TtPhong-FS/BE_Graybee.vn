package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.product.model.ProductClassifyView;

import java.util.List;
import java.util.Optional;

public interface ProductClassifyViewRepository extends JpaRepository<ProductClassifyView, Long> {


    Optional<ProductClassifyView> findByProductId(long productId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductClassifyView p SET p.categoryName = null WHERE p.categoryName = :categoryName")
    void nullifyCategory(@Param("categoryName") String categoryName);

    @Transactional
    @Modifying
    @Query("UPDATE ProductClassifyView p SET p.brandName = null WHERE p.brandName = :brandName")
    void nullifyBrand(@Param("brandName") String brandName);

    @Query(value = """
                    SELECT * FROM product_classify_view
                    WHERE tag_names @> cast(:jsonTag as jsonb)
            """, nativeQuery = true)
    List<ProductClassifyView> findAllByTagName(@Param("jsonTag") String jsonTag);

}
