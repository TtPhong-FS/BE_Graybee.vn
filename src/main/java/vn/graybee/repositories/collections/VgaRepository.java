package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.VgaDetail;

public interface VgaRepository extends JpaRepository<VgaDetail, Long> {

}
