package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.request.ProfileRequest;
import vn.graybee.modules.account.dto.response.ProfileResponse;
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
    public ProfileResponse findByAccountId(Long accountId) {

        Profile profile = profileRepository.findByAccountId(accountId).orElse(null);

        if (profile == null) {
            return null;
        } else {
            return new ProfileResponse(profile);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProfileResponse updateByAccountId(ProfileRequest request, Long accountId) {

        checkExistsByPhone(request.getPhone());


        Profile profile = profileRepository.findByAccountId(accountId).orElseGet(Profile::new);
        Gender gender = Gender.fromString(request.getGender(), messageSourceUtil);

        if (profile.getAccountId() == null) {
            profile.setAccountId(accountId);
        }

        profile.setPhone(request.getPhone());
        profile.setFullName(request.getFullName());
        profile.setGender(gender);
        profile.setBirthday(request.getBirthday());
        profile = profileRepository.save(profile);

        return new ProfileResponse(profile);
    }

    @Override
    public void checkExistsByPhone(String phone) {
        if (profileRepository.checkExistsPhone(phone)) {
            throw new BusinessCustomException(Constants.Account.profile_phone, messageSourceUtil.get("account.profile.phone.exists"));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProfileResponse saveProfileByAccountId(Long accountId, ProfileRequest request) {

        checkExistsByPhone(request.getPhone());

        Gender gender = Gender.fromString(request.getGender(), messageSourceUtil);

        Profile profile = new Profile();
        profile.setAccountId(accountId);
        profile.setPhone(request.getPhone());
        profile.setBirthday(request.getBirthday());
        profile.setFullName(request.getFullName());
        profile.setGender(gender);

        profile = profileRepository.save(profile);

        return new ProfileResponse(profile);
    }

}
