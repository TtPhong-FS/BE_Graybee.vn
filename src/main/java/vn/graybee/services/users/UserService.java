package vn.graybee.services.users;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.users.UpdateProfileRequest;
import vn.graybee.response.users.UserProfileResponse;

public interface UserService {

    Integer getUidByToken(String token);

    BasicMessageResponse<UserProfileResponse> getProfileByUid(Integer uid);

    BasicMessageResponse<UserProfileResponse> update(UpdateProfileRequest request, Integer uid);

    void checkExistsByUid(Integer uid);


}
