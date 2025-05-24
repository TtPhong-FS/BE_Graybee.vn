package vn.graybee.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.product.model.RegexPattern;

import java.util.Optional;

public interface RegexPatternRepository extends JpaRepository<RegexPattern, Integer> {

    Optional<RegexPattern> findByName(String name);

}
