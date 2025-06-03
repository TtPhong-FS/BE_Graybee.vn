package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.modules.catalog.model.Attribute;

import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findByName(String name);

}
