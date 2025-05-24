package vn.graybee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.auth.model.ForgotPassword;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("Select fp from ForgotPassword fp where fp.otp = ?1 and fp.userId = ?2")
    Optional<ForgotPassword> findByOtpAndUserId(Integer otp, Integer userId);

}
