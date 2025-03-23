package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.Product;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.publics.ProductBasicResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new vn.graybee.response.admin.products.ProductResponse(p.createdAt, p.updatedAt, p , c.categoryName, m.manufacturerName, null, COALESCE(i.quantity, 0)) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.productCode = i.productCode "
    )
    List<ProductResponse> findProductsWithoutTags();

    @Query("SELECT new vn.graybee.response.admin.products.ProductDto(p.createdAt, p.updatedAt, p , c.categoryName, m.manufacturerName, null, COALESCE(i.quantity, 0), pd.description) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.productCode = i.productCode " +
            "LEFT JOIN ProductDescription pd on p.id = pd.productId where p.id = :id"
    )
    Optional<ProductDto> findById_ADMIN(@Param("id") long id);

    @Query("Select i.quantity from Product p join Inventory i on p.productCode = i.productCode where p.id = :id ")
    Optional<Integer> findById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id = :id ")
    void delete(@Param("id") long id);

//    @Query("Select p from Product p where p.id = :id ")
//    Optional<Product> getStatusById(@Param("id") long id);

    @Query(value = "Select p.productName from Product p where p.productName = :productName")
    Optional<String> validateNameExists(@Param("productName") String productName);

//    @Query("Select new vn.graybee.rsponse.publics.ProductBasicResponse(p.id, p.name, p.price, p.thumbnail) from Product p where p.category.name = :categoryName ")
//    Optional<ProductBasicResponse> findByCategoryName_PUBLIC(@Param("categoryName") String categoryName);

    @Query("Select new vn.graybee.response.publics.ProductBasicResponse(p.id, p.productName, p.price, p.finalPrice, p.thumbnail) from Product p join Category c on p.categoryId = c.id where c.categoryName = :categoryName ")
    List<ProductBasicResponse> fetchByCategoryName(@Param("categoryName") String categoryName);

}
