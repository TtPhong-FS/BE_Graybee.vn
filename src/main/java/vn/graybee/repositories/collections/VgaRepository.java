package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.VgaDetail;
import vn.graybee.projections.collections.VgaDetailProjection;

import java.util.List;

public interface VgaRepository extends JpaRepository<VgaDetail, Long> {

    @Query("SELECT v FROM VgaDetail v")
    List<VgaDetailProjection> fetchAll();

}
