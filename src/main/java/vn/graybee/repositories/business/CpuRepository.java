package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.CpuDetail;

public interface CpuRepository extends JpaRepository<CpuDetail, Long> {

}
