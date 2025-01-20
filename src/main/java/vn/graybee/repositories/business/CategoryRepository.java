package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
