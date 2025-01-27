package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.SsdDetail;

public interface SsdRepository extends JpaRepository<SsdDetail, Long> {

}
