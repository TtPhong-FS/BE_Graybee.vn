package vn.graybee.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.inventory.model.Inventory;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;
import vn.graybee.response.admin.products.ProductQuantityResponse;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query("Select i from Inventory i join Product p on i.productId = p.id where p.id = :productId ")
    Optional<Inventory> findByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.ProductQuantityResponse(p.id, COALESCE(i.quantity, 0)) from Product p left join Inventory i on i.productId = p.id where p.id = :productId ")
    ProductQuantityResponse findQuantityByProductId(@Param("productId") long productId);

    @Query("Select COALESCE(i.quantity, 0) from Inventory i left join Product p on i.productId = p.id where p.id = :productId ")
    Integer findStockByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.InventoryResponse(i.id, p.id, p.thumbnail, p.name, p.code, i.isStock, i.quantity, i.createdAt, i.updatedAt) from Inventory i left join Product p on i.productId = p.id")
    List<InventoryResponse> fetchAll();

    @Query("Select new vn.graybee.response.admin.products.InventoryQuantityResponse(i.id, i.quantity) from Inventory i where i.id = :inventoryId ")
    Optional<InventoryQuantityResponse> checkExistsById(@Param("inventoryId") Integer inventoryId);

    @Transactional
    @Modifying
    @Query(" Update Inventory i SET i.quantity = i.quantity - :totalQuantity where i.productId = :productId and i.quantity >= :totalQuantity")
    void updateQuantityAfterSuccessOrder(@Param("totalQuantity") int totalQuantity, @Param("productId") long productId);

    @Query("Select COALESCE(i.quantity, 0) from Inventory i where i.productId = :productId")
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
