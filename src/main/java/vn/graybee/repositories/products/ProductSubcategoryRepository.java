package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.ProductSubcategory;
import vn.graybee.response.admin.directories.subcategory.SubcategoryDto;
import vn.graybee.response.admin.products.ProductSubcategoryDto;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;

import java.util.List;
import java.util.Optional;

public interface ProductSubcategoryRepository extends JpaRepository<ProductSubcategory, Integer> {

    @Query("SELECT new vn.graybee.response.admin.products.ProductSubcategoryDto(ps.productId, sc.id,  sc.name) " +
            "FROM ProductSubcategory ps " +
            " inner join SubCategory sc ON ps.subcategoryId = sc.id " +
            " where ps.productId IN :productIds ")
    List<ProductSubcategoryDto> findSubcategoriesByProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT new vn.graybee.response.admin.directories.subcategory.SubcategoryDto(sc.id, sc.name) " +
            "FROM ProductSubcategory ps " +
            " inner join SubCategory sc ON ps.subcategoryId = sc.id " +
            " where ps.productId = :productId ")
    List<SubcategoryDto> findSubcategoriesByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("delete from ProductSubcategory ps where ps.productId = :productId and ps.subcategoryId = :subcategoryId")
    void deleteByProductIdAndSubcategoryId(@Param("productId") long productId, @Param("subcategoryId") int subcategoryId);

    @Transactional
    @Modifying
    @Query("Delete from ProductSubcategory ps where ps.productId = :productId and ps.subcategoryId NOT IN :subcategoryIds")
    void deleteByProductIdAndSubcategoryIdNotIn(@Param("productId") long productId, @Param("subcategoryIds") List<Integer> subcategoryIds);

    @Query("Select new vn.graybee.response.admin.products.ProductSubcategoryIDResponse(ps.productId, ps.subcategoryId) " +
            "from ProductSubcategory ps where ps.productId = :productId and ps.subcategoryId = :subcategoryId")
    Optional<ProductSubcategoryIDResponse> findRelationByProductAndSubcategoryId(@Param("productId") long productId, @Param("subcategoryId") int subcategoryId);

    @Query("SELECT ps.subcategoryId FROM ProductSubcategory ps WHERE ps.productId = :productId")
    List<Integer> findSubcategoryIdsByProductId(@Param("productId") long productId);

}
