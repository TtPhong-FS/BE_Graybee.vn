package vn.graybee.taxonomy.manufacturer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerDto;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerStatusDto;
import vn.graybee.taxonomy.manufacturer.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Query("Select new vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerDto(m) from Manufacturer m")
    List<ManufacturerDto> getAll();

    @Transactional
    @Modifying
    @Query("Update Manufacturer m set m.status = :status where m.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") TaxonomyStatus status);

    @Query("Select exists(Select 1 from Manufacturer m where m.id = :id )")
    Boolean checkExistsById(@Param("id") int id);

    @Query("Select exists(Select 1 from Manufacturer m where m.name = :name )")
    Boolean checkExistsByName(@Param("name") String name);

    @Query("SELECT EXISTS (SELECT 1 FROM Manufacturer m WHERE m.name = :name AND m.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select new vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto(m.id, m.name) from Manufacturer m where m.name In :names and m.status = :status ")
    List<ManufacturerBasicDto> findByNamesAndStatus(@Param("names") List<String> names, @Param("status") TaxonomyStatus status);

    @Query("Select m.name from Manufacturer m where m.id = :id and m.status = :status")
    Optional<String> getNameById(@Param("id") int id, @Param("status") TaxonomyStatus status);

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

    @Query("Select new vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerStatusDto(m.id, m.name, m.status) from Manufacturer m where m.id = :id")
    Optional<ManufacturerStatusDto> findNameAndStatusById(int id);

}
