package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
