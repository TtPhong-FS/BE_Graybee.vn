package vn.graybee.repositories.auths;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.users.Role;
import vn.graybee.response.admin.auth.RoleResponse;
import vn.graybee.response.admin.auth.RoleUserCountResponses;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("Select new vn.graybee.response.admin.auth.RoleResponse(r, null) from Role r ")
    List<RoleResponse> fetchAll();

    @Query("Select r.id from Role r where r.name = 'CUSTOMER' and r.isActive = true")
    Optional<Integer> getIdByRoleCustomer();

    @Query("Select r.name from Role r where r.id = :id ")
    String getNameById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("Delete from Role r where r.id = :id ")
    void deleteById(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.auth.RoleUserCountResponses(r.id, r.userCount) from Role r where r.id = :id")
    Optional<RoleUserCountResponses> getUserCountBeforeDelete(@Param("id") int id);

    @Query("Select new vn.graybee.response.admin.auth.RoleResponse(r, null) from Role r where r.id = :id")
    Optional<RoleResponse> getById(@Param("id") int id);

    @Query("Select exists (Select 1 from Role r where r.name = :name )")
    boolean existsByName(@Param("name") String name);

    @Query("Select exists (Select 1 from Role r where r.name = :name and r.id <> :id)")
    boolean existsByNameNotId(@Param("name") String name, @Param("id") int id);

}
