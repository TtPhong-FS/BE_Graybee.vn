package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.product.model.ProductDescription;

import java.util.Optional;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Integer> {

    @Query("Select pd from ProductDescription pd where pd.productId = :productId ")
    Optional<ProductDescription> findByProductId(@Param("productId") long productId);

    @Query("Select pd.description from ProductDescription pd where pd.productId = :productId ")
    String getDescriptionByProductId(@Param("productId") long productId);

    @Query("Select pd.description from ProductDescription pd where pd.productId = :productId ")
    Optional<String> findDescriptionByProductId(@Param("productId") Long productId);

}
