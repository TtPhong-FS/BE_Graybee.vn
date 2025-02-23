package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.MouseDetail;

public interface MouseRepository extends JpaRepository<MouseDetail, Long> {

}
