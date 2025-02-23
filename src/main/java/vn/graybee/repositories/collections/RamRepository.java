package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.RamDetail;

public interface RamRepository extends JpaRepository<RamDetail, Long> {

}
