package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.KeyboardDetail;

public interface KeyboardRepository extends JpaRepository<KeyboardDetail, Long> {

}
