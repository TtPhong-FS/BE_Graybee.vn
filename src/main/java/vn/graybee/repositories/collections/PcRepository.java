package vn.graybee.repositories.collections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.models.collections.PcDetail;
import vn.graybee.response.publics.collections.PcResponse;

import java.util.Optional;

public interface PcRepository extends JpaRepository<PcDetail, Long> {

    @Query("Select new vn.graybee.response.publics.collections.PcResponse(pd.cpu, pd.motherboard, pd.ram, pd.vga, pd.ssd) from PcDetail pd where pd.id = :productId ")
    Optional<PcResponse> findByProductId(long productId);

}
