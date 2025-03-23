package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.categories.SubCategoryTag;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;
import java.util.Optional;

public interface SubCategoryTagRepository extends JpaRepository<SubCategoryTag, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SubCategoryTag st WHERE st.subcategoryId = :subcategoryId AND st.tagId NOT IN :tagIds")
    void deleteBySubCategoryIdAndTagIdNotIn(@Param("subcategoryId") int subcategoryId, @Param("tagIds") List<Integer> tagIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM SubCategoryTag st WHERE st.subcategoryId = :subcategoryId AND st.tagId = :tagId")
    void deleteRelationBySubcategoryIdAndTagId(@Param("subcategoryId") int subcategoryId, @Param("tagId") int tagId);

    @Query("SELECT st.tagId FROM SubCategoryTag st WHERE st.subcategoryId = :subcategoryId")
    List<Integer> findTagIdsBySubcategoryId(@Param("subcategoryId") int subcategoryId);

    @Query("Select new vn.graybee.response.admin.directories.tag.TagResponse(t) from SubCategoryTag st join Tag t on st.tagId = t.id where st.subcategoryId = :subcategoryId ORDER BY t.id ASC")
    List<TagResponse> findBySubCategoryId(@Param("subcategoryId") int subcategoryId);

    @Query("Select new vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse(st.subcategoryId, st.tagId) from SubCategoryTag st where st.subcategoryId = :subcategoryId and st.tagId = :tagId ")
    Optional<SubcategoryTagIdResponse> findRelationsBySubcategoryIdAndTagId(@Param("subcategoryId") int subcategoryId, @Param("tagId") int tagId);

}
