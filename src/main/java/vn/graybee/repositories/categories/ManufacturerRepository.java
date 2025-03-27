package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerProductCountResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Manufacturer m where m.id = :id")
    void deleteById(@Param("id") int id);

    @Query("SELECT EXISTS (SELECT 1 FROM Manufacturer m WHERE m.name = :name AND m.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse(m) from Manufacturer m where m.id = :id ")
    Optional<ManufacturerResponse> getById(@Param("id") int id);

    @Query("Select m from Manufacturer m")
    List<ManufacturerProjection> fetchAll();

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManuDto(m.id, m.name) from Manufacturer m where m.id In :manuIds ")
    List<ManuDto> findByIds(@Param("manuIds") List<Integer> manuIds);

    @Query("Select m.id from Manufacturer m where m.id In :manuIds ")
    List<Integer> findAllByIds(@Param("manuIds") List<Integer> manuIds);

    @Query("Select m.name from Manufacturer m where m.id = :manufacturerId and m.status != 'DELETED'")
    Optional<String> getNameById(@Param("manufacturerId") int manufacturerId);

    @Query("Select m.name from Manufacturer m where m.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerProductCountResponse(m.id, m.productCount) from Manufacturer m where m.id = :id ")
    Optional<ManufacturerProductCountResponse> checkExistsByIdAndGetProductCount(int id);

}
