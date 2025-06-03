package vn.graybee.modules.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.inventory.model.Inventory;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query("Select i from Inventory i join Product p on i.productId = p.id where p.id = :productId ")
    Optional<Inventory> findByProductId(@Param("productId") long productId);


    @Query("Select new vn.graybee.response.admin.products.InventoryResponse(i.id, p.id, p.name, i.isStock, i.availableQuantity, i.createdAt, i.updatedAt) from Inventory i left join Product p on i.productId = p.id")
    List<InventoryResponse> fetchAll();

    @Query("Select new vn.graybee.response.admin.products.InventoryQuantityResponse(i.id, i.availableQuantity) from Inventory i where i.id = :inventoryId ")
    Optional<InventoryQuantityResponse> checkExistsById(@Param("inventoryId") Integer inventoryId);

    @Query("Select COALESCE(i.availableQuantity, 0) from Inventory i where i.productId = :productId")
    Integer getAvailableQuantityByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.availableQuantity = i.availableQuantity - :quantity WHERE i.productId = :productId AND i.availableQuantity >= :quantity")
    int decreaseStock(@Param("productId") Long productId, @Param("quantity") int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.availableQuantity = i.availableQuantity + :quantity WHERE i.productId = :productId")
    void increaseStock(@Param("productId") Long productId, @Param("quantity") int quantity);

    @Query("Select exists (Select 1 from Inventory i where i.productId = :productId)")
    boolean existsByProductId(@Param("productId") Long productId);

}
