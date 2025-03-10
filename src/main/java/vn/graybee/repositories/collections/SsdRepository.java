package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.SsdDetail;
import vn.graybee.projections.collections.SsdDetailProjection;

import java.util.List;

public interface SsdRepository extends JpaRepository<SsdDetail, Long> {

    @Query("SELECT s FROM SsdDetail s")
    List<SsdDetailProjection> fetchAll();

}
