package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
