package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.KeyboardDetail;
import vn.graybee.projections.collections.KeyboardDetailProjection;

import java.util.List;

public interface KeyboardRepository extends JpaRepository<KeyboardDetail, Long> {

    @Query("SELECT k FROM KeyboardDetail k")
    List<KeyboardDetailProjection> fetchAll();

}
