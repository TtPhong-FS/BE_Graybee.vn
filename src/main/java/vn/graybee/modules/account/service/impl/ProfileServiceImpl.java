package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.common.constants.ConstantAccount;
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
    public Long getAccountIdByEmail(String email) {
        return profileRepository.findAccountIdByEmail(email)
                .orElseThrow(() -> new BusinessCustomException(ConstantAccount.email, messageSourceUtil.get("auth.mail.invalid")));
    }

    @Override
    public void checkExistsByEmail(String email) {
        if (profileRepository.checkExistsEmail(email)) {
            throw new BusinessCustomException(ConstantAccount.email.toLowerCase(), messageSourceUtil.get("account.profile.email.exists", new Object[]{ConstantAccount.email}));
        }
    }

    @Override
    public void checkExistsByPhone(String phone) {
        if (profileRepository.checkExistsPhone(phone)) {
            throw new BusinessCustomException(ConstantAccount.phone, messageSourceUtil.get("account.profile.phone.exists"));
        }
    }

    @Override
    public Profile saveProfileByAccountId(Long accountId, CustomerRegisterRequest request) {

        Gender gender = Gender.fromString(request.getGender(), messageSourceUtil);

        Profile profile = new Profile();
        profile.setAccountId(accountId);
        profile.setAvatarUrl(null);
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setBirthDay(request.getBirthDay());
        profile.setFullName(request.getFullName());
        profile.setGender(gender);

        return profileRepository.save(profile);
    }

}
