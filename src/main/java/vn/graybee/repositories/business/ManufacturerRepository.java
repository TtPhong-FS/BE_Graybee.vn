package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.ManufacturerProjection;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    
    Optional<Manufacturer> findByName(@Param("name") String name);

    @Query("Select m from Manufacturer m")
    List<ManufacturerProjection> findAllManufacturers();


}
