package vn.graybee.repositories.products.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.products.detail.PcDetail;
import vn.graybee.response.publics.products.detail.PcDetailResponse;

public interface IPcDetailRepository extends JpaRepository<PcDetail, Long> {

    @Query("Select new vn.graybee.response.publics.products.detail.PcDetailResponse(pc) from PcDetail pc where pc.productId = :productId")
    PcDetailResponse findByProductId(@Param("productId") Long productId);

}
