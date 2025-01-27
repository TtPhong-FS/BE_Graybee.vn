package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.CpuDetail;

public interface CpuRepository extends JpaRepository<CpuDetail, Long> {

}
