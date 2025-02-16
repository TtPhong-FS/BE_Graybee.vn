package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.MouseDetail;

public interface MouseRepository extends JpaRepository<MouseDetail, Long> {

}
