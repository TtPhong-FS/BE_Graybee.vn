package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantInventory;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.enums.InventoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Inventory;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.products.InventoryRequest;
import vn.graybee.response.admin.inventories.AdminInventoryProductResponse;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;
import vn.graybee.services.products.InventoryService;

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
                inventory.getQuantity(),
                inventory.getStatus(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<InventoryResponse> create(InventoryRequest request) {

        AdminInventoryProductResponse product = productRepository.findAdminInventoryProductById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        InventoryStatus status = request.getStatusEnum();

        Inventory inventory = new Inventory();
        inventory.setProductId(product.getId());
        inventory.setQuantity(request.getQuantity());
        inventory.setStatus(request.getQuantity() > 0 ? status : InventoryStatus.OUT_OF_STOCK);

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

        InventoryStatus status = request.getStatusEnum();

        AdminInventoryProductResponse product = productRepository.findAdminInventoryProductById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists_in_inventory));

        inventory.setQuantity(request.getQuantity());
        inventory.setStatus(request.getQuantity() > 0 ? status : InventoryStatus.OUT_OF_STOCK);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = getResponse(inventory, product);

        return new BasicMessageResponse<>(200, ConstantInventory.success_update, response);
    }

}
