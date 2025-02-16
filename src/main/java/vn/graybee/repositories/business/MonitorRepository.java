package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.MonitorDetail;

public interface MonitorRepository extends JpaRepository<MonitorDetail, Long> {

}
