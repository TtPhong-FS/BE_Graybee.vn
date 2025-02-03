package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "Select p.productName from Product p where p.productName = :productName")
    Optional<String> ensureProductNameBeforeCreate(@Param("productName") String productName);

    @Query(value = "Select p.model from Product p where p.model = :model")
    Optional<String> checkProductModelExists(@Param("model") String model);

}
