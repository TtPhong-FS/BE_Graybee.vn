package vn.graybee.inventory.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.inventory.dto.request.InventoryRequest;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;

public interface InventoryService {

    BasicMessageResponse<InventoryResponse> create(InventoryRequest request);

    BasicMessageResponse<List<InventoryResponse>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<InventoryResponse> update(int id, InventoryRequest request);

}
