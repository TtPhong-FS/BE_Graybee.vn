package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.CpuDetail;

public interface CpuRepository extends JpaRepository<CpuDetail, Long> {

}
