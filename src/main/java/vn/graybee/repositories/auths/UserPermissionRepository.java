package vn.graybee.repositories.auths;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.users.UserPermission;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {

    @Query("Select p.name from UserPermission up join Permission p on up.permissionId = p.id where up.userId = :userId ")
    List<String> getPermissionOfUserByUserId(@Param("userId") Integer userId);

}
