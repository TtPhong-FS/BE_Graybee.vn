package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerDto;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerStatusDto;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse(m) from Manufacturer m")
    List<ManufacturerResponse> getAll();

    @Transactional
    @Modifying
    @Query("Update Manufacturer m set m.status = :status where m.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") DirectoryStatus status);

    @Query("Select exists(Select 1 from Manufacturer m where m.id = :id )")
    Boolean checkExistsById(@Param("id") int id);

    @Query("Select exists(Select 1 from Manufacturer m where m.name = :name )")
    Boolean checkExistsByName(@Param("name") String name);

    @Query("SELECT EXISTS (SELECT 1 FROM Manufacturer m WHERE m.name = :name AND m.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerDto(m.id, m.name) from Manufacturer m where m.name In :names and m.status = :status ")
    List<ManufacturerDto> findByNamesAndStatus(@Param("names") List<String> names, @Param("status") DirectoryStatus status);

    @Query("Select m.name from Manufacturer m where m.id = :manufacturerId and m.status != 'DELETED'")
    Optional<String> getNameById(@Param("manufacturerId") int manufacturerId);

    @Query("Select COALESCE(m.productCount, 0) from Manufacturer m where m.id = :id ")
    Optional<Integer> getProductCountById(int id);

    @Transactional
    @Modifying
    @Query("Update Manufacturer m set m.productCount = m.productCount + 1 where m.id = :id")
    void increaseProductCountById(int id);

    @Transactional
    @Modifying
    @Query("Update Manufacturer m set m.productCount = m.productCount - 1 where m.id = :id")
    void decreaseProductCountById(int id);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerStatusDto(m.id, m.name, m.status) from Manufacturer m where m.id = :id")
    Optional<ManufacturerStatusDto> findNameAndStatusById(int id);

}
