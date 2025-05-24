package vn.graybee.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantInventory;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.inventory.dto.request.InventoryRequest;
import vn.graybee.inventory.model.Inventory;
import vn.graybee.inventory.repository.InventoryRepository;
import vn.graybee.product.repository.ProductRepository;
import vn.graybee.response.admin.inventories.AdminInventoryProductResponse;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public InventoryResponse getResponse(Inventory inventory, AdminInventoryProductResponse product) {
        return new InventoryResponse(
                inventory.getId(),
                product.getId(),
                product.getThumbnail(),
                product.getProductName(),
                product.getProductCode(),
                inventory.getStock(),
                inventory.getQuantity(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<InventoryResponse> create(InventoryRequest request) {

        AdminInventoryProductResponse product = productRepository.findAdminInventoryProductById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        Inventory inventory = new Inventory();
        inventory.setProductId(product.getId());
        inventory.setQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        inventory.setStock(request.getQuantity() != null && request.getQuantity() > 0);

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = getResponse(inventory, product);

        return new BasicMessageResponse<>(200, ConstantInventory.success_create, response);
    }

    @Override
    public BasicMessageResponse<List<InventoryResponse>> fetchAll() {
        List<InventoryResponse> response = inventoryRepository.fetchAll();

        final String message = response.isEmpty() ? ConstantGeneral.empty_list : ConstantInventory.success_fetch_inventories;

        return new BasicMessageResponse<>(200, message, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
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
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<InventoryResponse> update(int id, InventoryRequest request) {

        AdminInventoryProductResponse product = productRepository.findAdminInventoryProductById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists_in_inventory));

        inventory.setQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        inventory.setStock(request.getQuantity() != null && request.getQuantity() > 0);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = getResponse(inventory, product);

        return new BasicMessageResponse<>(200, ConstantInventory.success_update, response);
    }

}
