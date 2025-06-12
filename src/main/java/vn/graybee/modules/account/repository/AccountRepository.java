package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.modules.account.dto.response.AccountPrincipal;
import vn.graybee.modules.account.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("Select new vn.graybee.modules.account.dto.response.AccountPrincipal(a.id, a.uid, a.password, a.role, a.isActive, a.isSuperAdmin) from Account a where a.uid = :uid")
    Optional<AccountPrincipal> findByUid(@Param("uid") String uid);

    @Query("Select new vn.graybee.auth.dto.response.AccountAuthDto(a.id, a.uid, a.role, a.password) from Account a where a.email = :email")
    Optional<AccountAuthDto> findByEmail(@Param("email") String email);

    @Query("Select exists (Select 1 from Account a where a.id = :id)")
    boolean existsById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("Update Account a set a.password = :password where a.id = :id")
    void updatePasswordById(@Param("id") Long id, @Param("password") String password);

    @Query("Select a.id from Account a where a.email = :email")
    Optional<Long> findIdByEmail(String email);

    @Query("Select exists (Select 1 from Account a where a.email = :email)")
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("Update Account a set a.lastLoginAt = CURRENT_TIMESTAMP where a.id = :id")
    void updateLastLoginAt(@Param("id") long id);

}
