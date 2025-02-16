package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.products.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "Select p.name from Product p where p.name = :name")
    Optional<String> validateNameExists(@Param("name") String name);

    @Query(value = "Select p.model from Product p where p.model = :model")
    Optional<String> validateModelExists(@Param("model") String model);

}
