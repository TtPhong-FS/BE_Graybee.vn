package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.MouseDetail;
import vn.graybee.projections.collections.MouseDetailProjection;

import java.util.List;

public interface MouseRepository extends JpaRepository<MouseDetail, Long> {

    @Query("SELECT m FROM MouseDetail m")
    List<MouseDetailProjection> fetchAll();

}
