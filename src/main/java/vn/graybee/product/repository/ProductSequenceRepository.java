package vn.graybee.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.product.model.ProductSequence;

import java.util.Optional;

public interface ProductSequenceRepository extends JpaRepository<ProductSequence, Integer> {

    Optional<ProductSequence> findByCategoryCodeAndYearMonth(String categoryCode, String yearMonth);

}
