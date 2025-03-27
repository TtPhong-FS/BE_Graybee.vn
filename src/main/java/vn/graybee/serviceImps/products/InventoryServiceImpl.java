package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantInventory;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.enums.GeneralStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Inventory;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;
import vn.graybee.services.products.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public BasicMessageResponse<List<InventoryResponse>> fetchAll() {
        List<InventoryResponse> response = inventoryRepository.fetchAll();

        if (response.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, response);
        }

        return new BasicMessageResponse<>(200, ConstantInventory.success_fetch_inventories, response);
    }

    @Override
    public BasicMessageResponse<Integer> delete(int id) {
        InventoryQuantityResponse inventory = inventoryRepository.checkExistsById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists_in_inventory));

        if (inventory.getQuantity() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantProduct.still_inventory);
        }

        inventoryRepository.deleteById(inventory.getId());

        return new BasicMessageResponse<>(200, ConstantInventory.success_delete, inventory.getId());
    }

    @Override
    public BasicMessageResponse<InventoryResponse> updateQuantity(int id, int quantity) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists_in_inventory));

        inventory.setQuantity(quantity);
        inventory.setStatus(quantity > 0 ? GeneralStatus.ACTIVE.name() : GeneralStatus.OUT_OF_STOCK.name());

        inventory.setUpdatedAt(LocalDateTime.now());

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = new InventoryResponse(inventory);

        return new BasicMessageResponse<>(200, ConstantInventory.success_update, response);
    }

}
