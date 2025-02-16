package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.HandheldDetail;

public interface HandheldRepository extends JpaRepository<HandheldDetail, Long> {

}
