package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.MotherBoardDetail;
import vn.graybee.projections.collections.MotherboardDetailProjection;

import java.util.List;

public interface MotherboardRepository extends JpaRepository<MotherBoardDetail, Long> {

    @Query("SELECT m FROM MotherBoardDetail m")
    List<MotherboardDetailProjection> fetchAll();

}
