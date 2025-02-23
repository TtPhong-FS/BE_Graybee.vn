package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
