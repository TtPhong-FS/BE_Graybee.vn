package vn.graybee.taxonomy.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.taxonomy.tag.dto.response.TagDto;
import vn.graybee.taxonomy.tag.model.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer> {


    @Transactional
    @Modifying
    @Query("delete from Tag t where t.id = :id ")
    void delete(@Param("id") int id);

    @Query("Select EXISTS (SELECT 1 FROM Tag t WHERE t.name = :name AND t.id <> :id)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select t.id from Tag t where t.id = :id ")
    Optional<Integer> checkExistById(@Param("id") int id);

    @Query("SELECT new vn.graybee.taxonomy.tag.dto.response.TagDto(t.id, t.name) FROM Tag t WHERE t.id = :id ")
    Optional<TagDto> getById(@Param("id") int id);

    @Query("Select t.id from Tag t where t.id IN :ids")
    Set<Integer> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("select t.id from Tag t where t.name = :name ")
    Optional<Integer> validateName(@Param("name") String name);

    @Query("SELECT new vn.graybee.taxonomy.tag.dto.response.TagDto(t.id, t.name) FROM Tag t WHERE t.id IN :ids")
    List<TagDto> findByIds(@Param("ids") List<Integer> ids);

    @Query("Select t.id from Tag t where t.id IN :ids")
    List<Integer> findIdByIds(@Param("ids") List<Integer> ids);

}
