package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.request.UpdateProfileRequest;
import vn.graybee.modules.account.enums.Gender;
import vn.graybee.modules.account.model.Profile;
import vn.graybee.modules.account.repository.ProfileRepository;
import vn.graybee.modules.account.service.ProfileService;

@AllArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final MessageSourceUtil messageSourceUtil;


    @Override
    public ResponseEntity<BasicMessageResponse<Profile>> findByAccountId(Long accountId) {

        Profile profile = profileRepository.findByAccountId(accountId);

        return new ResponseEntity<>(
                new BasicMessageResponse<>(200, "", profile),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<BasicMessageResponse<Profile>> updateByAccountId(UpdateProfileRequest request, Long accountId) {
        return null;
    }

    @Override
    public void checkExistsByPhone(String phone) {
        if (profileRepository.checkExistsPhone(phone)) {
            throw new BusinessCustomException(Constants.Common.phone, messageSourceUtil.get("account.profile.phone.exists"));
        }
    }

    @Override
    public Profile saveProfileByAccountId(Long accountId, CustomerRegisterRequest request) {

        Gender gender = Gender.fromString(request.getGender(), messageSourceUtil);

        Profile profile = new Profile();
        profile.setAccountId(accountId);
        profile.setAvatarUrl(null);
        profile.setPhone(request.getPhone());
        profile.setBirthDay(request.getBirthDay());
        profile.setFullName(request.getFullName());
        profile.setGender(gender);

        return profileRepository.save(profile);
    }

}
