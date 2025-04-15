package vn.graybee.repositories.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.enums.ProductStatus;
import vn.graybee.models.products.Product;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new vn.graybee.response.admin.products.ProductResponse(p,c.name, m.name, COALESCE(i.quantity, 0)) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.id = i.productId "
    )
    List<ProductResponse> fetchProducts();

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p where p.id = :id")
    Optional<ProductBasicResponse> findBasicById(@Param("id") long id);

    @Query("Select p.finalPrice from Product p where p.id = :id")
    BigDecimal findFinalPriceById(@Param("id") long id);

    @Query("SELECT new vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse(p.id, p.code,null,null) FROM Product p")
    List<ProductSubcategoryAndTagResponse> fetchProductsWithoutSubcategoryAndTag();

    @Query("SELECT new vn.graybee.response.admin.products.ProductDto(p ,c.id, m.id,null,null, COALESCE(i.quantity, 0), pd.description) " +
            "FROM Product p " +
            "INNER JOIN Category c ON p.categoryId = c.id " +
            "INNER JOIN Manufacturer m ON p.manufacturerId = m.id " +
            "LEFT JOIN Inventory i on p.id = i.productId " +
            "LEFT JOIN ProductDescription pd on p.id = pd.productId where p.id = :id"
    )
    Optional<ProductDto> findToUpdate(@Param("id") long id);

    @Query("Select p.id from Product p where p.id = :id")
    Optional<Long> checkExistsById(@Param("id") long id);

    @Query(value = "Select p.name from Product p where p.name = :name")
    Optional<String> validateName(@Param("name") String name);

    @Query(value = "Select p.code from Product p where p.id = :id")
    Optional<String> getProductCodeById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("Update Product p set p.status = :status where p.id = :id ")
    void updateStatusById(@Param("id") long id, @Param("status") ProductStatus status);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id = :id ")
    void deleteById(@Param("id") long id);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE p.name = :name AND p.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") long id);

    //    Public

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p where p.status = 'PUBLISHED' ")
    List<ProductBasicResponse> getProductToLoadIntoElastic();

    @Query("Select new vn.graybee.response.favourites.ProductFavourite(p.id, p.finalPrice,p.name, p.thumbnail) from Product p where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductFavourite> findToAddToFavourite(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductBasicResponse> findBasicToAddToCart(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductPriceResponse(p.id, p.finalPrice) from Product p where p.id = :id and p.status = 'PUBLISHED'")
    Optional<ProductPriceResponse> getPriceById(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductDetailResponse(p.id, p.name, m.name, p.warranty, p.conditions, p.weight,p.color, p.thumbnail, p.price, p.finalPrice, p.discountPercent) from Product p join Manufacturer m on p.manufacturerId = m.id where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductDetailResponse> getDetailByProductId(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p join Category c on p.categoryId = c.id where p.status = 'PUBLISHED' and c.name = :category ")
    Page<ProductBasicResponse> findProductsByCategoryName(@Param("category") String category, Pageable pageable);

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p join Category c on p.categoryId = c.id join Manufacturer m on p.manufacturerId = m.id where p.status = 'PUBLISHED' and c.name = :category and m.name = :manufacturer ")
    Page<ProductBasicResponse> findByCategoryAndManufacturer(@Param("category") String category, @Param("manufacturer") String manufacturer, Pageable pageable);

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p join Category c on p.categoryId = c.id where p.status = 'PUBLISHED' and c.name = :category and exists (Select 1 from ProductSubcategory ps join SubCategory s on ps.subcategoryId = s.id where ps.productId = p.id and s.name = :subcategory) and exists (Select 1 from ProductTag pt join Tag t on pt.tagId = t.id where pt.productId = p.id and t.name = :tag)")
    Page<ProductBasicResponse> findByCategoryAndSubcategoryAndTag(@Param("category") String category, @Param("subcategory") String subcategory, @Param("tag") String tag, Pageable pageable);

}
