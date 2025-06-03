package vn.graybee.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.product.dto.response.detail.PcDetailResponse;
import vn.graybee.modules.product.model.detail.PcDetail;

public interface PcDetailRepository extends JpaRepository<PcDetail, Long> {

    @Query("Select new vn.graybee.modules.product.dto.response.detail.PcDetailResponse(pc) from PcDetail pc where pc.productId = :productId")
    PcDetailResponse findByProductId(@Param("productId") Long productId);

}
