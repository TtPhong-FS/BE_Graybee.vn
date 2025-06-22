package vn.graybee.modules.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.dashboard.dto.ProductRevenue;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
                SELECT new vn.graybee.modules.dashboard.dto.ProductRevenue(
                    p.id,
                    p.name,
                    p.thumbnail,
                    SUM(o.totalAmount)
                )
                FROM Product p
                JOIN OrderDetail od ON od.productId = p.id
                JOIN Order o ON o.id = od.orderId
                WHERE o.status = 'COMPLETED'
                GROUP BY p.id, p.name, p.thumbnail
                ORDER BY SUM(o.totalAmount) DESC
            """)
    List<ProductRevenue> getTenProductBestSeller(Pageable pageable);


    @Query("""
            SELECT new vn.graybee.modules.product.dto.response.ProductResponse(p, ca.name, bra.name, null)
            FROM Product p
            JOIN Category ca on p.categoryId = ca.id
            JOIN Category bra on p.brandId = bra.id
            """)
    List<ProductResponse> getAllProductResponse();

    @Query("Select COALESCE(p.finalPrice, 0) from Product p where p.id = :id")
    Optional<Double> findFinalPriceById(@Param("id") long id);

    @Query("""
            SELECT new vn.graybee.modules.product.dto.response.ProductUpdateDto(p, ca.name, bra.name, COALESCE(i.quantity, 0), pd.description)
                        FROM Product p
                        JOIN Category ca on ca.id = p.categoryId
                        LEFT JOIN Category bra on p.brandId = bra.id
                        LEFT JOIN Inventory i on p.id = i.productId
                        LEFT JOIN ProductDescription pd on p.id = pd.productId
                        where p.id = :id
            """
    )
    Optional<ProductUpdateDto> findProductUpdateDtoById(@Param("id") long id);

    @Query("Select exists (Select 1 from Product p where p.id = :id)")
    boolean checkExistsById(@Param("id") long id);

    @Query("Select p.status from Product p where p.id = :id")
    ProductStatus findStatusById(long id);

    @Query("Select exists (Select 1 from Product p where LOWER(p.name) = LOWER(:name))")
    boolean existsByName(String name);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE LOWER(p.name) = LOWER(:name) AND p.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") long id);

    @Query("Select exists (Select 1 from Product p where LOWER(p.slug) = LOWER(:slug))")
    boolean existsBySlug(String slug);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE LOWER(p.slug) = LOWER(:slug) AND p.id <> :id)")
    boolean existsBySlugAndNotId(@Param("slug") String slug, @Param("id") long id);

    @Transactional
    @Modifying
    @Query("Update Product p set p.status = :status where p.id = :id ")
    void updateStatusById(@Param("id") long id, @Param("status") ProductStatus status);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id = :id ")
    void deleteById(@Param("id") long id);

    @Query("Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail) from Product p where p.status = 'PUBLISHED' ")
    List<ProductBasicResponse> getProductPublishedToLoadIntoElastic();

    //    Public

    @Query("Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail) from Product p where p.id = :id and p.status = 'PUBLISHED' ")
    Optional<ProductBasicResponse> findBasicInfoForCart(@Param("id") long id);

    @Query("Select new vn.graybee.response.publics.products.ProductPriceResponse(p.id, p.finalPrice) from Product p where p.id = :id and p.status = 'PUBLISHED'")
    Optional<ProductPriceResponse> getPriceById(@Param("id") long id);

    @Query("Select new vn.graybee.modules.account.dto.response.FavoriteProductResponse(p.id, p.slug, p.price, p.finalPrice, p.name, p.thumbnail) from Product p where p.id = :productId and p.status = 'PUBLISHED'")
    Optional<FavoriteProductResponse> findProductFavouriteById(Long productId);

    @Query("Select new vn.graybee.modules.product.dto.response.InventoryProductDto(p.id, p.name, p.status) from Product p where p.id = :productId")
    Optional<InventoryProductDto> findProductBasicDtoById(long productId);

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductDetailDto(p, pd.description)
            from Product p
            left join ProductDescription pd on p.id = pd.productId
            where p.slug = :slug
            """)
    Optional<ProductDetailDto> findProductDetailDtoBySlug(String slug);


    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from Product p
            join Category c on p.categoryId = c.id
            where p.status = 'PUBLISHED'
            """)
    List<ProductBasicResponse> findAllProductPublished();

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from Product p
            join Category c on c.id = p.categoryId
            where p.status in ('PUBLISHED', 'COMING_SOON') and c.slug = :categorySlug
            """)
    List<ProductBasicResponse> findProductByCategorySlug(@Param("categorySlug") String categorySlug);

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from Product p
            join Category c on c.id = p.brandId
            where p.status in ('PUBLISHED', 'COMING_SOON') and c.slug = :categorySlug
            """)
    List<ProductBasicResponse> findProductByBrandSlug(@Param("categorySlug") String categorySlug);

    @Query("Select p.categoryId from Product p where p.id = :id")
    Optional<Long> getCategoryIdById(long id);

    @Query("Select p.id from Product p where p.slug = :productSlug")
    Optional<Long> findIdBySlug(String productSlug);

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from Product p
            join Category c on c.id = p.categoryId
            where c.name LIKE %:category% and p.status = 'PUBLISHED'
            """)
    List<ProductBasicResponse> findByCategoryName(String category);

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductDetailDto(p, pd.description)
            from Product p
            left join ProductDescription pd on p.id = pd.productId
            where p.id = :id
            """)
    Optional<ProductDetailDto> findProductDetailDtoById(long id);


}
