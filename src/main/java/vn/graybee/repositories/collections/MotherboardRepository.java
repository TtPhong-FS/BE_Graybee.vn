package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.MotherBoardDetail;

public interface MotherboardRepository extends JpaRepository<MotherBoardDetail, Long> {

}
