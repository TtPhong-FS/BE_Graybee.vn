package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
