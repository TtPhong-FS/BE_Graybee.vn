package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.HeatDissipationDetail;

public interface HeatDissipationRepository extends JpaRepository<HeatDissipationDetail, Long> {

}
