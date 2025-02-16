package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.categories.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

}
