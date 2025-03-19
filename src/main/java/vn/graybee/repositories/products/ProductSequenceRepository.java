package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.ProductSequence;

import java.util.Optional;

public interface ProductSequenceRepository extends JpaRepository<ProductSequence, Integer> {

    Optional<ProductSequence> findByCategoryCodeAndYearMonth(String categoryCode, String yearMonth);

}
