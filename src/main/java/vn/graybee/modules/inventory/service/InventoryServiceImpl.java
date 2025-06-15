package vn.graybee.modules.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.inventory.dto.request.InventoryRequest;
import vn.graybee.modules.inventory.dto.response.InventoryIdQuantity;
import vn.graybee.modules.inventory.model.Inventory;
import vn.graybee.modules.inventory.repository.InventoryRepository;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.service.AdminProductService;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final AdminProductService adminProductService;

    private final MessageSourceUtil messageSourceUtil;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, AdminProductService adminProductService, MessageSourceUtil messageSourceUtil) {
        this.inventoryRepository = inventoryRepository;
        this.adminProductService = adminProductService;
        this.messageSourceUtil = messageSourceUtil;
    }

    public InventoryResponse getInventoryResponse(Inventory inventory, InventoryProductDto product) {
        return new InventoryResponse(
                product.getProductId(),
                product.getProductName(),
                inventory.getQuantity(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InventoryResponse createInventory(InventoryRequest request) {

        InventoryProductDto product = adminProductService.getProductBasicDtoById(request.getProductId());

        Inventory inventory = new Inventory();
        inventory.setProductId(product.getProductId());
        inventory.setQuantity(request.getQuantity());

        inventory = inventoryRepository.save(inventory);

        return getInventoryResponse(inventory, product);
    }

    @Override
    public List<InventoryResponse> getInventoryListForDashboard() {
        return inventoryRepository.getAllInventoryResponse();

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteInventoryByProductId(long productId) {

        InventoryQuantityResponse inventory = inventoryRepository.checkExistsById(productId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("common.not-found", new Object[]{messageSourceUtil.get("inventory.name")})));

        if (inventory.getQuantity() > 0) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("inventory.cannot-delete-when-has-quantity"));
        }

        inventoryRepository.deleteById(inventory.getProductId());

        return productId;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InventoryIdQuantity updateQuantityByProductId(long productId, int quantity) {

        checkExistsByProductId(productId);

        inventoryRepository.updateQuantityByProductId(productId, quantity);

        return new InventoryIdQuantity(productId, quantity, LocalDateTime.now());
    }

    @Override
    public void validateQuantityAvailable(long productId, int requestedQuantity) {
        checkExistsByProductId(productId);
        Integer availableQuantity = inventoryRepository.getAvailableQuantityByProductId(productId);

        if (availableQuantity < requestedQuantity) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("inventory.not-enough-Quantity"));
        }
    }

    @Override
    public int getAvailableQuantity(long productId) {
        checkExistsByProductId(productId);
        return inventoryRepository.getAvailableQuantityByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void decreaseQuantity(long productId, int quantity) {
        checkExistsByProductId(productId);
        int updated = inventoryRepository.decreaseQuantity(productId, quantity);

        if (updated == 0) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("inventory.not-enough-Quantity"));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void increaseQuantity(long productId, int quantity) {
        checkExistsByProductId(productId);
        inventoryRepository.increaseQuantity(productId, quantity);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveInventoryByProductId(long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(Inventory::new);

        boolean isInventoryExists = inventory.getProductId() == null;
        if (isInventoryExists) {
            inventory.setProductId(productId);
        }

        inventory.setQuantity(quantity);

        inventoryRepository.save(inventory);
    }

    private void checkExistsByProductId(long productId) {
        if (!inventoryRepository.existsByProductId(productId)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("inventory.not.found"));
        }

    }

}
