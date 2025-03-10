package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.HddDetail;
import vn.graybee.projections.collections.HddDetailProjection;

import java.util.List;

public interface HddRepository extends JpaRepository<HddDetail, Long> {

    @Query("SELECT h FROM HddDetail h")
    List<HddDetailProjection> fetchAll();

}
