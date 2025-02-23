package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.LaptopDetail;

public interface LaptopRepository extends JpaRepository<LaptopDetail, Long> {

}
