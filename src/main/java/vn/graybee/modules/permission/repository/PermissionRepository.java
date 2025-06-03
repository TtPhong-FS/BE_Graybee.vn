package vn.graybee.modules.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.permission.dto.response.PermissionBasicDto;
import vn.graybee.modules.permission.dto.response.PermissionUserCountDto;
import vn.graybee.modules.permission.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("Select new vn.graybee.modules.permission.dto.response.PermissionUserCountDto(p.id, p.userCount) from Permission p where p.id = :id")
    Optional<PermissionUserCountDto> getUserCountBeforeDelete(@Param("id") int id);

    @Query("Select p from Permission p where p.id = :id")
    Optional<Permission> getById(@Param("id") int id);

    @Query("Select p.name from Permission p where p.name = :name ")
    Optional<String> validateName(@Param("name") String name);

    @Query("Select exists (Select 1 from Permission p where p.name = :name and p.id <> :id)")
    boolean existsByNameNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select new vn.graybee.modules.permission.dto.response.PermissionBasicDto(p.id, p.name) from Permission p where p.id IN :ids")
    List<PermissionBasicDto> findByIds(@Param("ids") List<Integer> ids);

}
