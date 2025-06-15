package vn.graybee.modules.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.permission.dto.response.PermissionForUpdateResponse;
import vn.graybee.modules.permission.model.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("Select exists (Select 1 from Permission p where p.name = :name )")
    boolean existsByName(@Param("name") String name);

    @Query("Select exists (Select 1 from Permission p where p.name = :name and p.id <> :id)")
    boolean existsByNameNotId(@Param("name") String name, @Param("id") int id);

    @Transactional
    @Modifying
    @Query("Delete from Permission p where p.id = :id")
    void deleteById(@Param("id") Integer id);

    @Query("""
            Select new vn.graybee.modules.permission.dto.response.PermissionForUpdateResponse(p.id, p.name, p.description, p.isActive)
            from Permission p
            where p.id = :id
            """)
    Optional<PermissionForUpdateResponse> findPermissionForUpdateById(int id);

}
