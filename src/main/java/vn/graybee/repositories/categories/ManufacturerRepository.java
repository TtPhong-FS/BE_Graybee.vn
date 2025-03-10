package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.category.ManufacturerProjection;
import vn.graybee.response.categories.ManufacturerStatusResponse;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Manufacturer m where m.id = :id")
    void deleteById(@Param("id") int id);

    @Query("Select m.productCount from Manufacturer m where m.id = :id ")
    Optional<Integer> getCountProductById(@Param("id") int id);

    @Query("Select m from Manufacturer m")
    List<ManufacturerProjection> fetchAll();

    Optional<Manufacturer> findByName(@Param("name") String name);

    @Query("Select m.id from Manufacturer m where m.name In :name ")
    List<Integer> getIdByName(@Param("name") List<String> name);


    @Query("Select new vn.graybee.response.categories.ManufacturerStatusResponse(m.id, m.status) from Manufacturer m where m.name = :name ")
    Optional<ManufacturerStatusResponse> getIdAndStatusByName(@Param("name") String name);


    @Query("Select m.name from Manufacturer m where m.name = :name ")
    Optional<String> validateNameExists(@Param("name") String name);

}
