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

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("Select i from Inventory i where i.productId = :productId ")
    Optional<Inventory> findByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.InventoryResponse(i.productId, p.name, i.quantity, i.createdAt, i.updatedAt) from Inventory i join Product p on i.productId = p.id")
    List<InventoryResponse> getAllInventoryResponse();

    @Query("Select new vn.graybee.response.admin.products.InventoryQuantityResponse(i.productId, i.quantity) from Inventory i where i.productId = :productId ")
    Optional<InventoryQuantityResponse> checkExistsById(@Param("productId") long productId);

    @Query("Select COALESCE(i.quantity, 0) from Inventory i where i.productId = :productId")
    Integer getAvailableQuantityByProductId(long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity - :quantity WHERE i.productId = :productId AND i.quantity >= :quantity")
    int decreaseQuantity(@Param("productId") long productId, @Param("quantity") int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :quantity WHERE i.productId = :productId")
    void increaseQuantity(@Param("productId") long productId, @Param("quantity") int quantity);

    @Query("Select exists (Select 1 from Inventory i where i.productId = :productId)")
    boolean existsByProductId(@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = :quantity WHERE i.productId = :productId")
    void updateQuantityByProductId(long productId, int quantity);

}
