package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.CategoryManufacturer;
import vn.graybee.taxonomy.category.dto.response.CategoryIdManufacturerIdDto;
import vn.graybee.taxonomy.category.dto.response.CategoryManufacturerBasicDto;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto;

import java.util.List;
import java.util.Optional;

public interface CategoryManufacturerRepository extends JpaRepository<CategoryManufacturer, Integer> {

    @Query("SELECT new vn.graybee.taxonomy.category.dto.response.CategoryManufacturerBasicDto(cm.categoryId, m.id,  m.name) " +
            "FROM CategoryManufacturer cm " +
            "INNER JOIN Manufacturer m ON cm.manufacturerId = m.id " +
            "WHERE cm.categoryId IN :categoryIds ORDER BY cm.categoryId")
    List<CategoryManufacturerBasicDto> findManufacturersByCategoryId_ADMIN(@Param("categoryIds") List<Integer> categoryIds);

    @Query("Select new vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto(m.id, m.name) from CategoryManufacturer cm join Manufacturer m on cm.manufacturerId = m.id where cm.categoryId = :categoryId order by m.id asc")
    List<ManufacturerBasicDto> findByCategoryId(@Param("categoryId") int categoryId);

    @Transactional
    @Modifying
    @Query("delete from CategoryManufacturer cm where cm.categoryId = :categoryId and cm.manufacturerId = :manufacturerId")
    void deleteManufacturerByIdAndCategoryById(@Param("categoryId") int categoryId, @Param("manufacturerId") int manufacturerId);

    @Modifying
    @Query("DELETE FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId NOT IN :manufacturerIds")
    void deleteByCategoryIdAndManufacturerIdNotIn(@Param("categoryId") int categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId")
    List<Integer> findManufacturerIdsByCategoryId(@Param("categoryId") int categoryId);

    @Query("Select new vn.graybee.taxonomy.category.dto.response.CategoryIdManufacturerIdDto(cm.categoryId, cm.manufacturerId) from CategoryManufacturer cm where cm.categoryId = :categoryId and cm.manufacturerId = :manufacturerId")
    Optional<CategoryIdManufacturerIdDto> findManufacturerIdWithCategoryId(@Param("categoryId") int categoryId, @Param("manufacturerId") int manufacturerId);

    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId IN :manufacturerIds")
    List<Integer> findExistingManufacturerIds(@Param("categoryId") Integer categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

}
