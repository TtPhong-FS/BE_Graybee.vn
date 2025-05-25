package vn.graybee.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantInventory;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.inventory.dto.request.InventoryRequest;
import vn.graybee.inventory.dto.response.InventoryProductResponse;
import vn.graybee.inventory.model.Inventory;
import vn.graybee.inventory.repository.InventoryRepository;
import vn.graybee.product.service.ProductService;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final ProductService productService;

    private final MessageSourceUtil messageSourceUtil;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductService productService, MessageSourceUtil messageSourceUtil) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
        this.messageSourceUtil = messageSourceUtil;
    }

    public InventoryResponse getInventoryResponse(Inventory inventory, InventoryProductResponse product) {
        return new InventoryResponse(
                inventory.getId(),
                product.getProductId(),
                product.getProductName(),
                product.getProductCode(),
                inventory.getStock(),
                inventory.getAvailableQuantity(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<InventoryResponse> createInventory(InventoryRequest request) {

        InventoryProductResponse product = productService.getInventoryProductResponseById(request.getProductId());

        Inventory inventory = new Inventory();
        inventory.setProductId(product.getProductId());
        inventory.setAvailableQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        inventory.setStock(request.getQuantity() != null && request.getQuantity() > 0);

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = getInventoryResponse(inventory, product);

        return new BasicMessageResponse<>(200, ConstantInventory.success_create, response);
    }

    @Override
    public BasicMessageResponse<List<InventoryResponse>> getInventoryListForDashboard() {
        List<InventoryResponse> response = inventoryRepository.fetchAll();

        final String message = response.isEmpty() ? messageSourceUtil.get("inventory.empty") : messageSourceUtil.get("inventory.success-get-all");

        return new BasicMessageResponse<>(200, message, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> deleteInventory(int id) {

        InventoryQuantityResponse inventory = inventoryRepository.checkExistsById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("common.not-found", new Object[]{messageSourceUtil.get("inventory.name")})));

        if (inventory.getQuantity() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("inventory.cannot-delete-when-has-quantity"));
        }

        inventoryRepository.deleteById(inventory.getId());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("inventory.success-delete"), inventory.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<InventoryResponse> updateInventory(int id, InventoryRequest request) {


        InventoryProductResponse product = productService.getInventoryProductResponseById(request.getProductId());

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.not-found", new Object[]{messageSourceUtil.get("inventory.name")})));

        inventory.setAvailableQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        inventory.setStock(request.getQuantity() != null && request.getQuantity() > 0);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = getInventoryResponse(inventory, product);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("inventory.success-update"), response);
    }


    @Override
    public void validateStockAvailable(Long productId, int requestedQuantity) {
        checkExistsByProductId(productId);
        Integer availableQuantity = inventoryRepository.getAvailableQuantityByProductId(productId);

        if (availableQuantity < requestedQuantity) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("inventory.not-enough-stock"));
        }
    }

    @Override
    public int getAvailableStock(Long productId) {
        checkExistsByProductId(productId);
        return inventoryRepository.getAvailableQuantityByProductId(productId);
    }

    @Override
    public void decreaseStock(Long productId, int quantity) {
        checkExistsByProductId(productId);
        int updated = inventoryRepository.decreaseStock(productId, quantity);

        if (updated == 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("inventory.not-enough-stock"));
        }
    }

    @Override
    public void increaseStock(Long productId, int quantity) {
        checkExistsByProductId(productId);
        inventoryRepository.increaseStock(productId, quantity);

    }

    private void checkExistsByProductId(Long productId) {
        if (!inventoryRepository.existsByProductId(productId)) {
            throw new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("inventory.not.found"));
        }

    }

}
