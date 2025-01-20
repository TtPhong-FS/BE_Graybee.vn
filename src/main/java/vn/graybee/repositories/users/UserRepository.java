package vn.graybee.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.users.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
