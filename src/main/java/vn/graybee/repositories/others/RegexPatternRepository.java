package vn.graybee.repositories.others;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.others.RegexPattern;

import java.util.Optional;

public interface RegexPatternRepository extends JpaRepository<RegexPattern, Integer> {

    Optional<RegexPattern> findByTypeName(String typeName);

}
