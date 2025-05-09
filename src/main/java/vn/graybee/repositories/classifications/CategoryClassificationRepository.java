package vn.graybee.repositories.classifications;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.classifications.CategoryClassification;

public interface CategoryClassificationRepository extends JpaRepository<CategoryClassification, Integer> {

}
