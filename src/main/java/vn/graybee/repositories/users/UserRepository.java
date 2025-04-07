package vn.graybee.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.users.User;
import vn.graybee.models.users.UserPrincipalDto;
import vn.graybee.response.users.UserProfileResponse;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select new vn.graybee.models.users.UserPrincipalDto(u.id,u.uid, u.isActive, u.roleId, u.username, u.password, r.name) from User u join Role r on u.roleId = r.id where u.username = :username")
    Optional<UserPrincipalDto> findByUserName(@Param("username") String username);

    @Query("Select u.phoneNumber from User u where u.phoneNumber = :phoneNumber")
    Optional<String> validatePhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("Select u.email from User u where u.email = :email")
    Optional<String> validateEmail(@Param("email") String email);

    @Query("Select u.id from User u where u.username = :username")
    Optional<String> checkExistsByUsername(@Param("username") String username);

    @Query("Select u.password from User u where u.username = :username")
    String getPasswordByUsername(@Param("username") String username);

    @Query("Select u.uid from User u where u.username = :username")
    Optional<Integer> getUidByUsername(@Param("username") String username);

    @Query("Select new vn.graybee.response.users.UserProfileResponse(u.uid, u.fullName, u.phoneNumber, u.email,u.gender, u.dateOfBirth) from User u where u.uid = :uid")
    UserProfileResponse getProfileByUid(@Param("uid") Integer uid);

    @Query("Select exists (Select 1 from User u where u.uid = :uid)")
    Boolean checkExistsByUid(@Param("uid") int uid);

    @Query("Select u from User u where u.uid = :uid")
    User findByUid(@Param("uid") int uid);

}
