package vn.graybee.modules.inventory.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.inventory.dto.request.InventoryRequest;
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
    public ResponseEntity<BasicMessageResponse<InventoryResponse>> create(@RequestBody @Valid InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.createInventory(request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<InventoryResponse>>> fetchAll() {
        return ResponseEntity.ok(inventoryService.getInventoryListForDashboard());
    }

    @PutMapping
    public ResponseEntity<BasicMessageResponse<InventoryResponse>> update(@RequestParam("id") int id, @RequestBody @Valid InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, request));
    }

    @DeleteMapping
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(inventoryService.deleteInventory(id));
    }


}
