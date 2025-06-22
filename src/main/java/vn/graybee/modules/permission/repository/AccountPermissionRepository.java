package vn.graybee.modules.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.permission.dto.response.AccountIdPermissionDto;
import vn.graybee.modules.permission.model.AccountPermission;

import java.util.List;
import java.util.Set;

public interface AccountPermissionRepository extends JpaRepository<AccountPermission, Integer> {

    @Query("Select p.name from AccountPermission ap join Permission p on ap.permissionId = p.id where ap.accountId = :accountId and p.isActive = :active ")
    Set<String> getPermissionByAccountUid(@Param("accountId") Long accountId, @Param("active") boolean active);

    @Query("""
            Select new vn.graybee.modules.permission.dto.response.AccountIdPermissionDto(ap.accountId, p.id, p.name)
            from AccountPermission ap
            join Permission p on p.id = ap.permissionId
            where ap.accountId in :accountIds
            """)
    List<AccountIdPermissionDto> findAllPermissionMapAccountIds(@Param("accountIds") List<Long> accountIds);

}
