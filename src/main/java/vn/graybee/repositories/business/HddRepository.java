package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.HddDetail;

public interface HddRepository extends JpaRepository<HddDetail, Long> {

}
