package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.products.Product;
import vn.graybee.response.publics.ProductBasicResponse;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    boolean existsByCategoryId(int categoryId);

    @Query(value = "Select p.name from Product p where p.name = :name")
    Optional<String> validateNameExists(@Param("name") String name);

    @Query(value = "Select p.model from Product p where p.model = :model")
    Optional<String> validateModelExists(@Param("model") String model);

    @Query("Select new vn.graybee.response.publics.ProductBasicResponse(p.id, p.name, p.price, p.thumbnail) from Product p where p.category.name = :categoryName ")
    Optional<ProductBasicResponse> findByCategoryName_PUBLIC(@Param("categoryName") String categoryName);

}
