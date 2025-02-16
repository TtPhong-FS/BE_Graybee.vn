package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.collections.PcDetail;
import vn.graybee.projections.publics.PcSummaryProjection;

import java.util.Optional;

public interface PcRepository extends JpaRepository<PcDetail, Long> {

    Optional<PcSummaryProjection> findByProductId(long productId);

}
