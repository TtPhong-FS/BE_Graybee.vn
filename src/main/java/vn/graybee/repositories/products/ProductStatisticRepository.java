package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.products.ProductStatistic;
import vn.graybee.response.admin.products.ProductStatisticResponse;

import java.util.List;

public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Integer> {

    @Query("Select ps from ProductStatistic ps where ps.productId = :productId")
    ProductStatistic findByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.ProductStatisticResponse(ps.id, p.code, ps.viewCount, ps.purchaseCount, ps.hasPromotion) ps from ProductStatistic ps join Product p on ps.productId = p.id")
    List<ProductStatisticResponse> fetchAll();
    
}
