package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.RamDetail;
import vn.graybee.projections.collections.RamDetailProjection;

import java.util.List;

public interface RamRepository extends JpaRepository<RamDetail, Long> {

    @Query("SELECT r FROM RamDetail r")
    List<RamDetailProjection> fetchAll();

}
