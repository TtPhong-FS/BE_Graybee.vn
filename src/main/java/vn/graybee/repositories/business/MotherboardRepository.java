package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.MotherBoardDetail;

public interface MotherboardRepository extends JpaRepository<MotherBoardDetail, Long> {

}
