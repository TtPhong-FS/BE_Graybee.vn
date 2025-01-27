package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.MonitorDetail;

public interface MonitorRepository extends JpaRepository<MonitorDetail, Long> {

}
