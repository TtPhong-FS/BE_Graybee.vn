package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.HandheldDetail;

public interface HandheldRepository extends JpaRepository<HandheldDetail, Long> {

}
