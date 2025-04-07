package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.CategoryManufacturer;
import vn.graybee.response.admin.directories.category.CategoryManuDto;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;

import java.util.List;
import java.util.Optional;

public interface CategoryManufacturerRepository extends JpaRepository<CategoryManufacturer, Integer> {

    @Query("SELECT new vn.graybee.response.admin.directories.category.CategoryManuDto(cm.categoryId, m.id,  m.name) " +
            "FROM CategoryManufacturer cm " +
            "INNER JOIN Manufacturer m ON cm.manufacturerId = m.id " +
            "WHERE cm.categoryId IN :categoryIds ORDER BY cm.categoryId")
    List<CategoryManuDto> findManufacturersByCategoryId_ADMIN(@Param("categoryIds") List<Integer> categoryIds);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManuDto(m.id, m.name) from CategoryManufacturer cm join Manufacturer m on cm.manufacturerId = m.id where cm.categoryId = :categoryId order by m.id asc")
    List<ManuDto> findByCategoryId(@Param("categoryId") int categoryId);

    @Transactional
    @Modifying
    @Query("delete from CategoryManufacturer cm where cm.categoryId = :categoryId and cm.manufacturerId = :manufacturerId")
    void deleteManufacturerByIdAndCategoryById(@Param("categoryId") int categoryId, @Param("manufacturerId") int manufacturerId);

    @Modifying
    @Query("DELETE FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId NOT IN :manufacturerIds")
    void deleteByCategoryIdAndManufacturerIdNotIn(@Param("categoryId") int categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId")
    List<Integer> findManufacturerIdsByCategoryId(@Param("categoryId") int categoryId);

    @Query("Select new vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse(cm.categoryId, cm.manufacturerId) from CategoryManufacturer cm where cm.categoryId = :categoryId and cm.manufacturerId = :manufacturerId")
    Optional<CategoryManufacturerIdResponse> findManufacturerIdWithCategoryId(@Param("categoryId") int categoryId, @Param("manufacturerId") int manufacturerId);

    @Query("SELECT cm.manufacturerId FROM CategoryManufacturer cm WHERE cm.categoryId = :categoryId AND cm.manufacturerId IN :manufacturerIds")
    List<Integer> findExistingManufacturerIds(@Param("categoryId") Integer categoryId, @Param("manufacturerIds") List<Integer> manufacturerIds);

}
