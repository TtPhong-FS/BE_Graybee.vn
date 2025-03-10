package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.MonitorDetail;
import vn.graybee.projections.collections.MonitorDetailProjection;

import java.util.List;

public interface MonitorRepository extends JpaRepository<MonitorDetail, Long> {

    @Query("SELECT m FROM MonitorDetail m")
    List<MonitorDetailProjection> fetchAll();

}
