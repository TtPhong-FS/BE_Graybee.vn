package vn.graybee.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.product.model.ProductClassification;

import java.util.List;

public interface ProductClassificationRepository extends JpaRepository<ProductClassification, Long> {

    List<ProductClassification> findByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("Delete from ProductClassification pc where pc.productId = :productId and pc.id IN :ids")
    void deleteByProductIdAndIds(@Param("productId") Long productId, @Param("ids") List<Long> ids);

    boolean hasTag(@Param("productId") Long productId, @Param("tagId") Integer tagId);

    boolean hasSubcategory(@Param("productId") Long productId, @Param("subcategoryId") Integer subcategoryId);

    @Transactional
    @Modifying
    @Query("Update ProductClassification pc set pc.tagId = null where pc.productId = :productId and pc.tagId = :tagId")
    void unsetTagByProductIdAndTagId(@Param("productId") Long productId, @Param("tagId") Integer tagId);

    @Transactional
    @Modifying
    @Query("Update ProductClassification pc set pc.subcategoryId = null where pc.productId = :productId and pc.subcategoryId = :subcategoryId")
    void unsetSubcategoryByProductIdAndSubcategoryId(@Param("productId") Long productId, @Param("subcategoryId") Integer subcategoryId);

}
