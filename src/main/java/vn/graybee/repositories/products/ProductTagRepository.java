package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.products.ProductTag;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductTagDto;

import java.util.List;
import java.util.Optional;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {

    @Query("SELECT new vn.graybee.response.admin.products.ProductTagDto(pt.productId, t.id,  t.name) " +
            "FROM ProductTag pt " +
            "INNER JOIN Tag t ON pt.tagId = t.id " +
            "WHERE pt.productId IN :productIds ORDER BY pt.productId")
    List<ProductTagDto> findTagsByProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT new vn.graybee.response.admin.directories.tag.TagResponse(t.id, t.name) " +
            "FROM ProductTag pt " +
            "INNER JOIN Tag t ON pt.tagId = t.id " +
            "WHERE pt.productId = :productId ")
    List<TagResponse> getTagsByProductId(@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("Delete from ProductTag pt where pt.productId = :productId and pt.tagId NOT IN :tagIds")
    void deleteByProductIdAndTagIdNotIn(@Param("productId") long productId, @Param("tagIds") List<Integer> tagIds);

    @Transactional
    @Modifying
    @Query("delete from ProductTag pt where pt.productId = :productId and pt.tagId = :tagId")
    void deleteByProductIdAndTagId(@Param("productId") long productId, @Param("tagId") int tagId);

    @Query("SELECT pt.tagId FROM ProductTag pt WHERE pt.productId = :productId")
    List<Integer> findTagIdsByProductId(@Param("productId") long productId);

    @Query("Select new vn.graybee.response.admin.products.ProductIdAndTagIdResponse(pt.productId, pt.tagId) from ProductTag pt where pt.productId = :productId and pt.tagId = :tagId")
    Optional<ProductIdAndTagIdResponse> findRelationByProductAndTagId(@Param("productId") long productId, @Param("tagId") int tagId);

}
