package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

public interface InventoryService {

    BasicMessageResponse<List<InventoryResponse>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<InventoryResponse> updateQuantity(int id, int quantity);

}
