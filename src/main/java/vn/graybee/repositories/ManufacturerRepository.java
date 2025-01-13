package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
