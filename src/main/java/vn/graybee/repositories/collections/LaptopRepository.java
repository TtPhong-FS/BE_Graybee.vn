package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.LaptopDetail;
import vn.graybee.projections.collections.LaptopDetailProjection;

import java.util.List;

public interface LaptopRepository extends JpaRepository<LaptopDetail, Long> {

    @Query("SELECT l FROM LaptopDetail l")
    List<LaptopDetailProjection> fetchAll();

}
