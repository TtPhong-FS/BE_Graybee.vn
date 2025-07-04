package vn.graybee.modules.order.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.order.dto.response.OrderDetailDto;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.modules.order.dto.response.admin.OrderStatusResponse;
import vn.graybee.modules.order.service.AdminOrderService;

import java.util.List;

@RestController
@RequestMapping("${api.admin.orders}")
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

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<OrderStatusResponse>> updateStatusById(@PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminOrderService.updateStatusOrderById(id, status),
                        "Cập nhật trạng thái đơn hàng thành công"
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
    public ResponseEntity<MessageResponse<List<AdminOrderResponse>>> getOrderListForDashboard() {

        List<AdminOrderResponse> orderResponses = adminOrderService.getOrderListForDashboard();
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


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminOrderService.deleteOrderById(id),
                        "Xoá đơn hàng thành công"
                )
        );
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BasicMessageResponse<OrderDetailDto>> getOrderDetailByCode(
            @PathVariable long id
    ) {
        OrderDetailDto orderDetailDto = adminOrderService.getOrderDetailById(id);

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderDetailDto,
                        null
                )
        );
    }


}
