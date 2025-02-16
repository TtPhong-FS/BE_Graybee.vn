package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.RamDetail;

public interface RamRepository extends JpaRepository<RamDetail, Long> {

}
