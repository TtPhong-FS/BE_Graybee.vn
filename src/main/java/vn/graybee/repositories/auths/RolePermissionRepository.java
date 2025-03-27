package vn.graybee.repositories.auths;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.users.RolePermission;
import vn.graybee.response.admin.auth.RolePermissionBasicResponse;
import vn.graybee.response.admin.auth.RolePermissionIdResponse;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    @Query("SELECT new vn.graybee.response.admin.auth.RolePermissionBasicResponse(rp.roleId, p.id, p.name) " +
            "FROM RolePermission rp " +
            "INNER JOIN Permission p ON rp.permissionId = p.id " +
            "WHERE rp.roleId IN :roleIds")
    List<RolePermissionBasicResponse> findPermissionsByRoleIds(@Param("roleIds") List<Integer> roleIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM RolePermission rp WHERE rp.roleId = :roleId AND rp.permissionId NOT IN :permissionIds ")
    void deleteByRoleIdAndPermissionIdNotIn(@Param("roleId") int roleId, @Param("permissionIds") List<Integer> permissionIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM RolePermission rp WHERE rp.roleId = :roleId AND rp.permissionId = :permissionId")
    void deleteRelationByRoleIdAndPermissionId(@Param("roleId") int roleId, @Param("permissionId") int permissionId);

    @Query("SELECT rp.permissionId FROM RolePermission rp WHERE rp.roleId = :roleId")
    List<Integer> findPermissionIdsByRoleId(@Param("roleId") int roleId);

    @Query("SELECT new vn.graybee.response.admin.auth.RolePermissionIdResponse(rp.roleId, rp.permissionId) FROM RolePermission rp WHERE rp.roleId = :roleId and rp.permissionId = :permissionId")
    Optional<RolePermissionIdResponse> findRelationByRoleIdAndPermissionId(@Param("roleId") int roleId, @Param("permissionId") int permissionId);

}
