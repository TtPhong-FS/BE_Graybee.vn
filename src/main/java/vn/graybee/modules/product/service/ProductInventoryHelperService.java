package vn.graybee.modules.product.service;

import org.springframework.stereotype.Service;
import vn.graybee.modules.inventory.service.InventoryService;
import vn.graybee.modules.product.dto.response.InventoryProductDto;

@Service
public class ProductInventoryHelperService {

    private final AdminProductService adminProductService;

    private final InventoryService inventoryService;

    public ProductInventoryHelperService(AdminProductService adminProductService, InventoryService inventoryService) {
        this.adminProductService = adminProductService;
        this.inventoryService = inventoryService;
    }

    public InventoryProductDto getProductBasicDtoById(Long productId) {
        return adminProductService.getProductBasicDtoById(productId);
    }

    public void saveInventoryByProductId(Long productId, boolean stock, int quantity) {

        inventoryService.saveInventoryByProductId(productId, stock, quantity);
    }

    public void updateInventoryByProductId(Long productId, boolean stock, int quantity) {

        inventoryService.updateInventoryByProductId(productId, stock, quantity);
    }

    public int getAvailableStock(long productId) {
        return inventoryService.getAvailableStock(productId);
    }

}
