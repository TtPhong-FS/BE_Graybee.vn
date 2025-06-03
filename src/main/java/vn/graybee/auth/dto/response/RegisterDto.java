package vn.graybee.auth.dto.response;

import vn.graybee.modules.account.model.Profile;

public class RegisterDto {

    private Profile profile;

    private AuthDto auth;

    public RegisterDto() {
    }

    public RegisterDto(Profile profile, AuthDto auth) {
        this.profile = profile;
        this.auth = auth;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public AuthDto getAuth() {
        return auth;
    }

    public void setAuth(AuthDto auth) {
        this.auth = auth;
    }

}
