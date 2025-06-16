package vn.graybee.modules.inventory.service;

import vn.graybee.modules.inventory.dto.request.InventoryRequest;
import vn.graybee.modules.inventory.dto.response.InventoryForUpdateResponse;
import vn.graybee.modules.inventory.dto.response.InventoryIdQuantity;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    List<InventoryResponse> getInventoryListForDashboard();

    Long deleteInventoryByProductId(long productId);

    InventoryIdQuantity updateQuantityByProductId(long productId, int quantity);

    void validateQuantityAvailable(long productId, int requestedQuantity);

    int getAvailableQuantity(long productId);

    void decreaseQuantity(long productId, int quantity);

    void increaseQuantity(long productId, int quantity);

    void saveInventoryByProductId(long productId, int quantity);

    InventoryForUpdateResponse getInventoryForUpdate(long productId);

}
