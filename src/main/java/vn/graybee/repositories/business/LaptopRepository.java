package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.LaptopDetail;

public interface LaptopRepository extends JpaRepository<LaptopDetail, Long> {

}
