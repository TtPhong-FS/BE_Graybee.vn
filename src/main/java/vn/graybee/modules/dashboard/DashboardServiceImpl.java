package vn.graybee.modules.dashboard;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.graybee.modules.dashboard.dto.OrderTotalResponse;
import vn.graybee.modules.dashboard.dto.ProductRevenue;
import vn.graybee.modules.order.repository.OrderRepository;
import vn.graybee.modules.product.repository.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;


    @Override
    public List<ProductRevenue> getTenProductBestSeller() {
        PageRequest pageRequest = PageRequest.of(0, 15);
        return productRepository.getTenProductBestSeller(pageRequest);
    }

    @Override
    public OrderTotalResponse getTotalOrders() {
        return orderRepository.countTotalOrders();
    }

}
