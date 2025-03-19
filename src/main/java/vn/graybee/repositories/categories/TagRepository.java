package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.Tag;
import vn.graybee.projections.category.TagProjection;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT t FROM Tag t")
    List<TagProjection> fetchAll();
    
    @Query("select t.id from Tag t where t.tagName = :tagName ")
    Optional<Integer> validateNameExists(@Param("tagName") String tagName);

    @Query("SELECT t.id, t.tagName FROM Tag t WHERE t.tagName IN :tagNames")
    Map<String, Integer> getIdMapByName(@Param("tagNames") List<String> tagNames);

    @Query("Select t.id from Tag t where t.tagName In :tagName ")
    List<Integer> getIdByNames(@Param("tagName") List<String> tagName);

}
