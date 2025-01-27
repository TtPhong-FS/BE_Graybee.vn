package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.PsuDetail;

public interface PsuRepository extends JpaRepository<PsuDetail, Long> {

}
