package vn.graybee.modules.inventory.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.inventory.dto.request.InventoryRequest;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

public interface InventoryService {

    BasicMessageResponse<InventoryResponse> createInventory(InventoryRequest request);

    BasicMessageResponse<List<InventoryResponse>> getInventoryListForDashboard();

    BasicMessageResponse<Integer> deleteInventory(int id);

    BasicMessageResponse<InventoryResponse> updateInventory(int id, InventoryRequest request);

    void validateStockAvailable(Long productId, int requestedQuantity);

    int getAvailableStock(Long productId);

    void decreaseStock(Long productId, int quantity);

    void increaseStock(Long productId, int quantity);

    void saveInventoryByProductId(Long productId, boolean stock, Integer quantity);

    void updateInventoryByProductId(Long productId, boolean stock, Integer quantity);

}
