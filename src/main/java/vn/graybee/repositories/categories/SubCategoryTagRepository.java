package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.SubCategoryTag;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryIdTagIdDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagDto;
import vn.graybee.taxonomy.tag.dto.response.TagDto;

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

    @Query("Select new vn.graybee.taxonomy.tag.dto.response.TagDto(t.id, t.name) from SubCategoryTag st join Tag t on st.tagId = t.id where st.subcategoryId = :subcategoryId")
    List<TagDto> findTagsBySubCategoryId(@Param("subcategoryId") int subcategoryId);

    @Query("Select new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryIdTagIdDto(st.subcategoryId, st.tagId) from SubCategoryTag st where st.subcategoryId = :subcategoryId and st.tagId = :tagId ")
    Optional<SubcategoryIdTagIdDto> findRelationsBySubcategoryIdAndTagId(@Param("subcategoryId") int subcategoryId, @Param("tagId") int tagId);

    @Query("SELECT new vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagDto(st.subcategoryId, t.id, t.name) " +
            "FROM SubCategoryTag st " +
            "INNER JOIN Tag t ON st.tagId = t.id " +
            "WHERE st.subcategoryId IN :subcategoryIds ORDER BY st.subcategoryId")
    List<SubcategoryTagDto> findTagsBySubcategoryIds(@Param("subcategoryIds") List<Integer> subcategoryIds);

}
