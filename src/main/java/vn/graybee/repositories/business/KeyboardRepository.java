package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.KeyboardDetail;

public interface KeyboardRepository extends JpaRepository<KeyboardDetail, Long> {

}
