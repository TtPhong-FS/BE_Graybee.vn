package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.CategoryManufacturer;
import vn.graybee.projections.category.CategoryManufacturerProjection;

import java.util.List;

public interface CategoryManufacturerRepository extends JpaRepository<CategoryManufacturer, Integer> {

    @Query("SELECT cm FROM CategoryManufacturer cm")
    List<CategoryManufacturerProjection> fetchAll();

    @Transactional
    @Modifying
    @Query("delete from CategoryManufacturer cm where cm.manufacturerId = :manufacturerId and cm.categoryId = :categoryId")
    void deleteManufacturerByIdAndCategoryById(@Param("manufacturerId") int manufacturerId, @Param("categoryId") int categoryId);


    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId IN :manufacturerIds")
    List<Integer> findExistingManufacturerIds(@Param("categoryId") Integer categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

    @Query("""
                SELECT c.id, c.categoryName, m.id, m.manufacturerName
                FROM CategoryManufacturer cm
                JOIN Category c ON cm.categoryId = c.id
                JOIN Manufacturer m ON cm.manufacturerId = m.id
            """)
    List<Object[]> fetchCategoryWithManufacturersRaw();


}
