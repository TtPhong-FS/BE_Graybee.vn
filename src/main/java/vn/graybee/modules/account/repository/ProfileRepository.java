package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.account.model.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("Select exists (Select 1 from Profile p where p.email = :email)")
    boolean checkExistsEmail(@Param("email") String email);

    @Query("Select exists (Select 1 from Profile p where p.phone = :phone)")
    boolean checkExistsPhone(@Param("phone") String phone);

    Profile findByAccountId(@Param("accountId") Long accountId);

    @Query("Select p.accountId from Profile p where p.email = :email")
    Optional<Long> findAccountIdByEmail(@Param("email") String email);

}
