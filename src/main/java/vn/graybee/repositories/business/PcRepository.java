package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.PcDetail;

public interface PcRepository extends JpaRepository<PcDetail, Long> {

}
