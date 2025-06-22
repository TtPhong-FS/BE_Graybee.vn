package vn.graybee.modules.account.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.dto.request.ProfileRequest;
import vn.graybee.modules.account.dto.response.ProfileResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.account.service.ProfileService;

@RestController
@RequestMapping("${api.privateApi.profile}")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<ProfileResponse>> getProfileByUserUid(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        profileService.findByAccountId(accountId),
                        null
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<ProfileResponse>> updateProfile(
            @RequestBody @Valid ProfileRequest request,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(
                MessageBuilder.ok(profileService.updateByAccountId(request, accountId),
                        "Cập nhật thông tin thành công")
        );
    }

}
