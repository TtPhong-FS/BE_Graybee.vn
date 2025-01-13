package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

}
