package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.KeyboardDetail;

public interface KeyboardRepository extends JpaRepository<KeyboardDetail, Long> {

}
