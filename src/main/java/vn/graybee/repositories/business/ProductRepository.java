package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Product;
import vn.graybee.response.ProductResponseByCategoryName;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select new vn.graybee.response.ProductResponseByCategoryName(p.id, p.name, p.price, p.thumbnail) from Product p join Category c on p.category.id = c.id where c.name = :name and c.isDelete = 'false' and p.isDelete = 'false' ")
    List<ProductResponseByCategoryName> findProductByCategory_Name(@Param("name") String name);

    @Query(value = "Select p.name from Product p where p.name = :name")
    Optional<String> ensureProductNameBeforeCreate(@Param("name") String name);

    @Query(value = "Select p.model from Product p where p.model = :model")
    Optional<String> checkProductModelExists(@Param("model") String model);

}
