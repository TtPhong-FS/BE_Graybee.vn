package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.modules.product.model.ProductSequence;

import java.util.Optional;

public interface ProductSequenceRepository extends JpaRepository<ProductSequence, Integer> {

    Optional<ProductSequence> findByCategoryCodeAndYearMonth(String categoryCode, String yearMonth);

}
