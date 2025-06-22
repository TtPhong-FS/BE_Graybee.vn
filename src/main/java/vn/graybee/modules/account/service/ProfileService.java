package vn.graybee.modules.account.service;

import vn.graybee.modules.account.dto.request.ProfileRequest;
import vn.graybee.modules.account.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse findByAccountId(Long accountId);

    ProfileResponse updateByAccountId(ProfileRequest request, Long accountId);

    void checkExistsByPhone(String phone);

    ProfileResponse saveProfileByAccountId(Long accountId, ProfileRequest request);

}
