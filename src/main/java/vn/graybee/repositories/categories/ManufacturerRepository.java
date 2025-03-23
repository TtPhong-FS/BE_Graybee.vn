package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Manufacturer m where m.id = :id")
    void deleteById(@Param("id") int id);

    @Query("Select m.productCount from Manufacturer m where m.id = :id ")
    Optional<Integer> getCountProductById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse(m) from Manufacturer m where m.id = :id ")
    Optional<ManufacturerResponse> getById(@Param("id") int id);

    @Query("Select m from Manufacturer m")
    List<ManufacturerProjection> fetchAll();

    @Query("Select m.id from Manufacturer m where m.manufacturerName In :manufacturerName ")
    List<Integer> getIdByName(@Param("manufacturerName") List<String> manufacturerName);

    @Query("Select new vn.graybee.response.admin.directories.manufacturer.ManuDto(m.id, m.manufacturerName) from Manufacturer m where m.id In :manuIds ")
    List<ManuDto> findByIds(@Param("manuIds") List<Integer> manuIds);

    @Query("Select m.id from Manufacturer m where m.id In :manuIds ")
    List<Integer> findAllByIds(@Param("manuIds") List<Integer> manuIds);

    @Query("Select m.id from Manufacturer m where m.manufacturerName = :manufacturerName and m.status != 'DELETED' ")
    Optional<Integer> getIdByName(@Param("manufacturerName") String manufacturerName);

    @Query("Select m.manufacturerName from Manufacturer m where m.manufacturerName = :manufacturerName ")
    Optional<String> validateNameExists(@Param("manufacturerName") String manufacturerName);

}
