package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query(value = "Select m.manufacturerName from Manufacturer m where m.manufacturerName = :manufacturerName")
    Optional<String> ensureManufactureNameBeforeCreate(@Param("manufacturerName") String manufacturerName);

    @Query(value = "select p.id from products p join manufacturers m on p.manufacturer_id = m.id where m.id = :manufacturerId and m.is_delete = 'false' limit 1 ", nativeQuery = true)
    Optional<Long> checkProductIdExistsByManufacturerId(@Param("manufacturerId") long manufacturerId);


}
