package vn.graybee.modules.account.service;

import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.modules.account.dto.request.UpdateProfileRequest;
import vn.graybee.modules.account.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse findByAccountId(Long accountId);

    ProfileResponse updateByAccountId(UpdateProfileRequest request, Long accountId);

    void checkExistsByPhone(String phone);

    ProfileResponse saveProfileByAccountId(Long accountId, CustomerRegisterRequest request);

}
