package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.CategoryManufacturer;
import vn.graybee.projections.category.CategoryManufacturerProjection;

import java.util.List;

public interface CategoryManufacturerRepository extends JpaRepository<CategoryManufacturer, Long> {

    @Query("SELECT cm FROM CategoryManufacturer cm")
    List<CategoryManufacturerProjection> fetchAll();


    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId IN :manufacturerIds")
    List<Integer> findExistingManufacturerIds(@Param("categoryId") Integer categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

}
