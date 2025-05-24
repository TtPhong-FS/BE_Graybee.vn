package vn.graybee.account.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.graybee.account.dto.request.UpdateProfileRequest;
import vn.graybee.account.model.Profile;
import vn.graybee.account.repository.ProfileRepository;
import vn.graybee.account.service.ProfileService;
import vn.graybee.common.dto.BasicMessageResponse;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

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

}
