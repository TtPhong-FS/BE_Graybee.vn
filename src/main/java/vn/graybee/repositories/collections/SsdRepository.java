package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.SsdDetail;

public interface SsdRepository extends JpaRepository<SsdDetail, Long> {

}
