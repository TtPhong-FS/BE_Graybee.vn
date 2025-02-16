package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.SubCategoryDetail;

import java.util.Optional;

public interface SubCategoryDetailRepository extends JpaRepository<SubCategoryDetail, Integer> {

    Optional<SubCategoryDetail> findByName(@Param("name") String name);

}
