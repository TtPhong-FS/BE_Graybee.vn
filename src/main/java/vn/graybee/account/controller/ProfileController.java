package vn.graybee.account.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.account.dto.request.UpdateProfileRequest;
import vn.graybee.account.model.Profile;
import vn.graybee.account.security.UserDetail;
import vn.graybee.account.service.ProfileService;
import vn.graybee.common.dto.BasicMessageResponse;

@RestController
@RequestMapping("{api.profile}")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<BasicMessageResponse<Profile>> getProfileByUserUid(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.getUser().getId();
        return profileService.findByAccountId(accountId);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<BasicMessageResponse<Profile>> updateProfile(
            @RequestBody @Valid UpdateProfileRequest request,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.getUser().getId();
        return profileService.updateByAccountId(request, accountId);
    }

}
