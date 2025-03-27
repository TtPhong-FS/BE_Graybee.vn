package vn.graybee.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.directories.Tag;
import vn.graybee.projections.admin.category.TagProjection;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT t FROM Tag t")
    List<TagProjection> fetchAll();

    @Transactional
    @Modifying
    @Query("delete from Tag t where t.id = :id ")
    void delete(@Param("id") int id);

    @Query("Select EXISTS (SELECT 1 FROM Tag t WHERE t.name = :name AND t.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select t.id from Tag t where t.id = :id ")
    Optional<Integer> checkExistById(@Param("id") int id);

    @Query("SELECT new vn.graybee.response.admin.directories.tag.TagResponse(t.id, t.name) FROM Tag t WHERE t.id = :id ")
    Optional<TagResponse> getById(@Param("id") int id);

    @Query("Select t.id from Tag t where t.id IN :ids")
    List<Integer> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("select t.id from Tag t where t.name = :name ")
    Optional<Integer> validateName(@Param("name") String name);

    @Query("SELECT new vn.graybee.response.admin.directories.tag.TagResponse(t.id, t.name) FROM Tag t WHERE t.id IN :ids")
    List<TagResponse> findByIds(@Param("ids") List<Integer> ids);

}
