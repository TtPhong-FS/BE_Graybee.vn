package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.CpuDetail;
import vn.graybee.projections.collections.CpuDetailProjection;

import java.util.List;

public interface CpuRepository extends JpaRepository<CpuDetail, Long> {

    @Query("SELECT c FROM CpuDetail c")
    List<CpuDetailProjection> fetchAll();

}
