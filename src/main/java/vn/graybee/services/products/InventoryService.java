package vn.graybee.services.products;

import vn.graybee.enums.InventoryStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.InventoryRequest;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

public interface InventoryService {

    BasicMessageResponse<InventoryResponse> create(InventoryRequest request);

    BasicMessageResponse<List<InventoryResponse>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<InventoryResponse> updateQuantity(int id, int quantity, InventoryStatus status);

}
