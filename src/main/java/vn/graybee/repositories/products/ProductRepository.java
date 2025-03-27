package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.Product;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.publics.ProductBasicResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new vn.graybee.response.admin.products.ProductResponse(p,c.name, m.name, COALESCE(i.quantity, 0)) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.code = i.productCode "
    )
    List<ProductResponse> fetchProducts();

    @Query("SELECT new vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse(p.id, p.code,null,null) FROM Product p")
    List<ProductSubcategoryAndTagResponse> fetchProductsWithoutSubcategoryAndTag();

    @Query("SELECT new vn.graybee.response.admin.products.ProductDto(p,c.id, m.id,null,null, COALESCE(i.quantity, 0), pd.description) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.code = i.productCode " +
            "LEFT JOIN ProductDescription pd on p.id = pd.productId where p.id = :id"
    )
    Optional<ProductDto> getById(@Param("id") long id);

    @Query("Select p.id from Product p where p.id = :id")
    Optional<Long> checkExistsById(@Param("id") long id);

    @Query("Select i.quantity from Product p join Inventory i on p.code = i.productCode where p.id = :id ")
    int findQuantityFromInventory(@Param("id") long id);


    @Query(value = "Select p.name from Product p where p.name = :name")
    Optional<String> validateName(@Param("name") String name);

    @Query("Select new vn.graybee.response.publics.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p join Category c on p.categoryId = c.id where c.name = :categoryName ")
    List<ProductBasicResponse> fetchByCategoryName(@Param("categoryName") String categoryName);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id = :id ")
    void delete(@Param("id") long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE p.name = :name AND p.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") long id);


}
