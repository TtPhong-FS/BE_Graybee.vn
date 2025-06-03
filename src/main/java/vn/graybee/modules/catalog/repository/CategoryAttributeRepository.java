package vn.graybee.modules.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.modules.catalog.model.CategoryAttribute;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long> {

}
