package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.modules.product.model.RegexPattern;

import java.util.Optional;

public interface RegexPatternRepository extends JpaRepository<RegexPattern, Integer> {

    Optional<RegexPattern> findByName(String name);

}
