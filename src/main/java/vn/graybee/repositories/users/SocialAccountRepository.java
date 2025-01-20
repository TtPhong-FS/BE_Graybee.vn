package vn.graybee.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.users.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

}
