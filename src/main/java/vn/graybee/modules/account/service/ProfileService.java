package vn.graybee.modules.account.service;

import org.springframework.http.ResponseEntity;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.dto.request.UpdateProfileRequest;
import vn.graybee.modules.account.model.Profile;

public interface ProfileService {

    ResponseEntity<BasicMessageResponse<Profile>> findByAccountId(Long accountId);

    ResponseEntity<BasicMessageResponse<Profile>> updateByAccountId(UpdateProfileRequest request, Long accountId);

    Long getAccountIdByEmail(String email);

    void checkExistsByEmail(String email);

    void checkExistsByPhone(String phone);

    Profile saveProfileByAccountId(Long accountId, CustomerRegisterRequest request);

}
