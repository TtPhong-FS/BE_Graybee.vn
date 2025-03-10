package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.categories.Tag;
import vn.graybee.projections.category.TagProjection;

import java.util.List;
import java.util.Map;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT t FROM Tag t")
    List<TagProjection> fetchAll();

    @Query("SELECT t.id, t.name FROM Tag t WHERE t.name IN :names")
    Map<String, Integer> getIdMapByName(@Param("names") List<String> names);


}
