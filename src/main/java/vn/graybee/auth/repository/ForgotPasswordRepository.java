package vn.graybee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.auth.model.ForgotPassword;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("Select fp from ForgotPassword fp where fp.otp = :otp and fp.accountId = :accountId")
    Optional<ForgotPassword> findByAccountIdAndOtp(@Param("accountId") Long accountId, @Param("otp") Integer otp);

}
