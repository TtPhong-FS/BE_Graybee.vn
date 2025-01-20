package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.business.Product;
import vn.graybee.response.ProductIdAndTypeResponse;
import vn.graybee.response.ProductResponseByCategoryId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select new vn.graybee.response.ProductResponseByCategoryId(p.id, p.name, p.price, p.thumbnail) from Product p join Category c on p.category.id = c.id where c.id = :id")
    List<ProductResponseByCategoryId> findProductByCategory_Id(@Param("id") long id);

    @Query(value = "select new vn.graybee.response.ProductIdAndTypeResponse(p.id, p.productType) from Product p where p.id = :id")
    Optional<ProductIdAndTypeResponse> findByIdAndProductType(@Param("id") long id);

}
