package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
