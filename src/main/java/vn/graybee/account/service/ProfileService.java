package vn.graybee.account.service;

import org.springframework.http.ResponseEntity;
import vn.graybee.account.dto.request.UpdateProfileRequest;
import vn.graybee.account.model.Profile;
import vn.graybee.common.dto.BasicMessageResponse;

public interface ProfileService {

    ResponseEntity<BasicMessageResponse<Profile>> findByAccountId(Long accountId);

    ResponseEntity<BasicMessageResponse<Profile>> updateByAccountId(UpdateProfileRequest request, Long accountId);

}
