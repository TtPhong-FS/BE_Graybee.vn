package vn.graybee.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.account.dto.response.AccountPrincipal;
import vn.graybee.account.model.Account;
import vn.graybee.auth.dto.response.AccountAuthDto;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("Select new vn.graybee.account.dto.response.AccountPrincipal(a.id, a.uid, a.password, a.role, a.isActive, a.isSuperAdmin) from Account a where a.uid = :uid")
    Optional<AccountPrincipal> findByUid(@Param("uid") String uid);

    @Query("Select new vn.graybee.auth.dto.response.AccountAuthDto(a.uid, a.role) from Account a where a.phone = :phone")
    Optional<AccountAuthDto> findByUsername(@Param("phone") String phone);

    @Query("Select exists (Select 1 from Account a where a.id = :id)")
    boolean existsById(@Param("id") Long id);

}
