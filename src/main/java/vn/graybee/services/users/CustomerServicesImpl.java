package vn.graybee.services.users;

import org.springframework.stereotype.Service;
import vn.graybee.messages.MessageResponse;
import vn.graybee.repositories.orders.OrderRepository;
import vn.graybee.repositories.users.UserRepository;
import vn.graybee.response.admin.orders.AdminCustomerOrderResponse;
import vn.graybee.response.admin.users.AdminCustomerResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServicesImpl implements CustomerService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public CustomerServicesImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public MessageResponse<List<AdminCustomerResponse>> getAllCustomers() {

        List<AdminCustomerResponse> customers = userRepository.getAllCustomers();

        for (AdminCustomerResponse customer : customers) {
            AdminCustomerOrderResponse order = orderRepository.findCustomerOrderByUserUId(customer.getUid());
            LocalDateTime lastOrderDate = orderRepository.findLastOrderDateByUid(customer.getUid());

            customer.setTotalOrder(order.getTotalOrder());
            customer.setTotalSpent(order.getTotalSpent());
            customer.setLastOrderDate(lastOrderDate);
        }

        return new MessageResponse<>(200, null, customers, null, null);
    }

}
