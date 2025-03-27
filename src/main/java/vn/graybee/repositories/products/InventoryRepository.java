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

    @Query("Select i from Inventory i join Product p on i.productCode = p.code where p.id = :productId ")
    Optional<Inventory> findByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.ProductQuantityResponse(p.id, COALESCE(i.quantity, 0)) from Product p left join Inventory i on i.productCode = p.code where p.id = :productId ")
    Optional<ProductQuantityResponse> findQuantityByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.InventoryResponse(i) from Inventory i")
    List<InventoryResponse> fetchAll();

    @Transactional
    @Modifying
    @Query("Delete from Inventory i where i.id = :id ")
    void deleteById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.products.InventoryQuantityResponse(i.id, i.quantity) from Inventory i where i.id = :id ")
    Optional<InventoryQuantityResponse> checkExistsById(@Param("id") int id);

}
