package vn.graybee.serviceImps.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantUser;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.User;
import vn.graybee.repositories.users.UserRepository;
import vn.graybee.requests.users.UpdateProfileRequest;
import vn.graybee.response.users.UserProfileResponse;
import vn.graybee.serviceImps.auth.JwtServices;
import vn.graybee.services.users.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtServices jwtServices;

    public UserServiceImpl(UserRepository userRepository, JwtServices jwtServices) {
        this.userRepository = userRepository;
        this.jwtServices = jwtServices;
    }


    @Override
    public Integer getUidByUsername(String username) {
        return userRepository.getUidByUsername(username).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.does_not_exists));

    }

    @Override
    public BasicMessageResponse<UserProfileResponse> getProfileByUid(Integer uid) {

        UserProfileResponse response = userRepository.getProfileByUid(uid);

        return new BasicMessageResponse<>(200, null, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UserProfileResponse> update(UpdateProfileRequest request, Integer uid) {

        checkExistsByUid(uid);

        User user = userRepository.findByUid(uid);
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());

        user = userRepository.save(user);

        UserProfileResponse response = new UserProfileResponse(
                user.getUid(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getGender(),
                user.getDateOfBirth());

        return new BasicMessageResponse<>(200, ConstantUser.success_update_profile, response);
    }

    @Override
    public void checkExistsByUid(Integer uid) {
        if (!userRepository.checkExistsByUid(uid)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantUser.does_not_exists);
        }
    }

}
