package vn.graybee.modules.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.dto.response.admin.CustomerResponse;
import vn.graybee.modules.account.service.AdminCustomerService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/customers")
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CustomerResponse>>> getAllCustomer() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminCustomerService.getAllCustomer(),
                        "Danh sách khách hàng đã được làm mới"
                )
        );
    }

}
