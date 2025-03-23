package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {


}
