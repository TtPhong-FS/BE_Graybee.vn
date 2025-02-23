package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.HddDetail;

public interface HddRepository extends JpaRepository<HddDetail, Long> {

}
