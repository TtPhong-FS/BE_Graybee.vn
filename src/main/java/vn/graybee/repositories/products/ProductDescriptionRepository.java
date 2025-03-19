package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.ProductDescription;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Integer> {

}
