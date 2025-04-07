package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.ProductStatistic;
import vn.graybee.repositories.products.ProductStatisticRepository;
import vn.graybee.response.admin.products.ProductStatisticResponse;
import vn.graybee.services.products.ProductStatisticService;

import java.util.List;

@Service
public class ProductStatisticServiceImpl implements ProductStatisticService {

    private final ProductStatisticRepository productStatisticRepository;

    public ProductStatisticServiceImpl(ProductStatisticRepository productStatisticRepository) {
        this.productStatisticRepository = productStatisticRepository;
    }

    @Override
    public BasicMessageResponse<List<ProductStatisticResponse>> fetchAll() {
        List<ProductStatisticResponse> statistic = productStatisticRepository.fetchAll();

        if (statistic.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, statistic);
        }
        return new BasicMessageResponse<>(200, ConstantGeneral.success_fetch_all, statistic);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateViewCount(long productId) {
        ProductStatistic productStatistic = productStatisticRepository.findByProductId(productId);

        productStatistic.decreaseViewCount();

        productStatisticRepository.save(productStatistic);
    }

}
