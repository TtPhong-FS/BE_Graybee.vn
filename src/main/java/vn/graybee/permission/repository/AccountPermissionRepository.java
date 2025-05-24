package vn.graybee.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.permission.model.AccountPermission;

import java.util.Set;

public interface AccountPermissionRepository extends JpaRepository<AccountPermission, Integer> {

    @Query("Select p.name from AccountPermission ap join Permission p on up.permissionId = p.id where ap.accountId = :accountId and p.isActive = :active ")
    Set<String> getPermissionByAccountUid(@Param("accountId") Long accountId, @Param("active") boolean active);

}
