package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
