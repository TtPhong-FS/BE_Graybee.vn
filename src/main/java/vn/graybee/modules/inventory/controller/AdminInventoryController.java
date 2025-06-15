package vn.graybee.modules.inventory.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.inventory.dto.request.InventoryRequest;
import vn.graybee.modules.inventory.dto.response.InventoryIdQuantity;
import vn.graybee.modules.inventory.service.InventoryService;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

@RestController
@RequestMapping("${api.adminApi.inventories}")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    public AdminInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<InventoryResponse>> createInventory(@RequestBody @Valid InventoryRequest request) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        inventoryService.createInventory(request),
                        "Thêm sản phẩm vào kho thành công"
                )
        );
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<InventoryResponse>>> fetchAll() {

        List<InventoryResponse> inventoryResponses = inventoryService.getInventoryListForDashboard();
        final String message = inventoryResponses.isEmpty() ? "Kho hiện tại đang trống!" : "Kho hàng đã được lấy thành công";

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        inventoryResponses
                        , message

                )
        );
    }

    @PutMapping("/quantity/{id}/{quantity}")
    public ResponseEntity<BasicMessageResponse<InventoryIdQuantity>> updateQuantityByProductId(@PathVariable("id") long id, @PathVariable int quantity) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        inventoryService.updateQuantityByProductId(id, quantity),
                        "Cập nhật số lượng thành công"
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<BasicMessageResponse<Long>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        inventoryService.deleteInventoryByProductId(id),
                        "Xoá sản phẩm khỏi kho thành công"
                )
        );
    }

}
