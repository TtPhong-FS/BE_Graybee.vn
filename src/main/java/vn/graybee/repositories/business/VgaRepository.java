package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.VgaDetail;

public interface VgaRepository extends JpaRepository<VgaDetail, Long> {

}
