package vn.graybee.modules.order.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.modules.order.service.AdminOrderService;

import java.util.List;

@RestController
@RequestMapping("${api.adminApi.orders}")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<BasicMessageResponse<ConfirmOrderResponse>> confirmOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminOrderService.confirmOrderById(id),
                        "Xác nhận đơn hàng thành công!"
                )
        );
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminOrderService.cancelOrderById(id),
                        "Huỷ đơn hàng thành công!"
                )
        );
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<AdminOrderResponse>>> getOrderListForDashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {

        List<AdminOrderResponse> orderResponses = adminOrderService.getOrderListForDashboard(page, size, sortBy, order);
        final String message = orderResponses.isEmpty() ? "Hiện tại không có đơn hàng nào trong hệ thống" : "Lấy tất cả đơn hàng thành công";
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderResponses
                        ,
                        message
                        ,
                        null, null
                )
        );
    }

}
