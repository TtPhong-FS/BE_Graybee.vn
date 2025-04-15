package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.Inventory;
import vn.graybee.response.admin.products.InventoryQuantityResponse;
import vn.graybee.response.admin.products.InventoryResponse;
import vn.graybee.response.admin.products.ProductQuantityResponse;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query("Select i from Inventory i join Product p on i.productId = p.id where p.id = :productId ")
    Optional<Inventory> findByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.ProductQuantityResponse(p.id, COALESCE(i.quantity, 0)) from Product p left join Inventory i on i.productId = p.id where p.id = :productId ")
    Optional<ProductQuantityResponse> findQuantityByProductId(@Param("productId") long productId);

    @Query("Select p.code from Product p join Inventory i on p.id = i.productId where i.id = :id ")
    Optional<String> getProductCodeById(@Param("id") int id);

    @Query("Select p.code from Product p join Inventory i on p.id = i.productId where i.id = :id ")
    Optional<String> getProductCodeByProductId(@Param("id") int id);


    @Query("Select i.quantity from Inventory i join Product p on i.productId = p.id where p.id = :productId")
    Integer findStockByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.InventoryResponse(i, p.code) from Inventory i left join Product p on i.productId = p.id")
    List<InventoryResponse> fetchAll();

    @Transactional
    @Modifying
    @Query("Delete from Inventory i where i.id = :id ")
    void deleteById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.products.InventoryQuantityResponse(i.id, i.quantity) from Inventory i where i.id = :id ")
    Optional<InventoryQuantityResponse> checkExistsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(" Update Inventory i SET i.quantity = i.quantity - :totalQuantity where i.productId = :productId and i.quantity >= :totalQuantity")
    void updateQuantityAfterSuccessOrder(@Param("totalQuantity") int totalQuantity, @Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("Update Inventory i set i.status = vn.graybee.enums.InventoryStatus.OUT_OF_STOCK where i.productId = :productId and i.quantity = 0 ")
    void updateStatusIfQuantityZero(@Param("productId") long productId);

}
