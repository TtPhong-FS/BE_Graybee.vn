package vn.graybee.modules.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductFavourite;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new vn.graybee.modules.product.dto.response.ProductResponse(p) " +
            "FROM Product p " +
            "Where (:status IS NULL or p.status = :status)"
    )
    Page<ProductResponse> fetchProducts(
            @Param("status") ProductStatus status,
            Pageable pageable);

    @Query("Select COALESCE(p.finalPrice, 0) from Product p where p.id = :id")
    Optional<BigDecimal> findFinalPriceById(@Param("id") long id);

    @Query("SELECT new vn.graybee.modules.product.dto.response.ProductUpdateDto(p, pd.description, COALESCE(i.availableQuantity, 0), COALESCE(i.isStock, false)) " +
            "FROM Product p " +
            "LEFT JOIN Inventory i on p.id = i.productId " +
            "LEFT JOIN ProductDescription pd on p.id = pd.productId where p.id = :id"
    )
    Optional<ProductUpdateDto> findProductUpdateDtoById(@Param("id") long id);

    @Query("Select exists (Select 1 from Product p where p.id = :id)")
    boolean checkExistsById(@Param("id") long id);

    @Query("Select p.status from Product p where p.id = :id")
    ProductStatus findStatusById(long id);

    @Query("Select exists (Select 1 from Product p where p.name = :name)")
    boolean existsByName(String name);

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

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p where p.status = 'PUBLISHED' ")
    List<ProductBasicResponse> getProductPublishedToLoadIntoElastic();

    //    Public

    @Query("Select new vn.graybee.modules.product.dto.response.ProductFavourite(p.id, p.price, p.finalPrice,p.name, p.thumbnail) from Product p where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductFavourite> findToAddToFavourite(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductBasicResponse(p.id, p.name, p.price, p.finalPrice, p.thumbnail) from Product p where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductBasicResponse> findBasicInfoForCart(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductPriceResponse(p.id, p.finalPrice) from Product p where p.id = :id and p.status = 'PUBLISHED'")
    Optional<ProductPriceResponse> getPriceById(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductDetailResponse(p.id, p.name, p.warranty, p.conditions, p.weight,p.color, p.thumbnail, p.price, p.finalPrice, p.discountPercent) from Product p ")
    Optional<ProductDetailResponse> getDetailByProductId(@Param("id") long id);

    @Query("Select new vn.graybee.modules.product.dto.response.FavoriteProductResponse(p.id, p.price, p.finalPrice, p.name, p.thumbnail) from Product p where p.id = :productId and p.status = 'PUBLISHED'")
    Optional<FavoriteProductResponse> findProductFavouriteById(Long productId);

    @Query("Select new vn.graybee.modules.product.dto.response.InventoryProductDto(p.id, p.name, p.status) from Product p where p.id = :productId")
    Optional<InventoryProductDto> findProductBasicDtoById(long productId);


}
