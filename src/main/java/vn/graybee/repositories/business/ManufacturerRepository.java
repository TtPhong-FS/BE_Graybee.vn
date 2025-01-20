package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
