package vn.graybee.controllers.admin.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.enums.InventoryStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.products.InventoryResponse;
import vn.graybee.services.products.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/inventories")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    public AdminInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<InventoryResponse>>> fetchAll() {
        return ResponseEntity.ok(inventoryService.fetchAll());
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<InventoryResponse>> updateQuantity(@RequestParam("id") int id, @RequestParam("quantity") int quantity, @RequestParam("status") InventoryStatus status) {
        return ResponseEntity.ok(inventoryService.updateQuantity(id, quantity, status));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(inventoryService.delete(id));
    }


}
