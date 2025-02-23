package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.SubCategory;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

  Optional<SubCategory> findByName(@Param("name") String name);
}
