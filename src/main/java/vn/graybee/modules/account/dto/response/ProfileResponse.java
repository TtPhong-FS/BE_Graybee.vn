package vn.graybee.modules.account.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.account.enums.Gender;
import vn.graybee.modules.account.model.Profile;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ProfileResponse {

    private String fullName;

    private String phone;

    private String avatarUrl;

    private Date birthday;

    private Gender gender;

    public ProfileResponse(Profile profile) {
        this.fullName = profile.getFullName();
        this.phone = profile.getPhone();
        this.avatarUrl = profile.getAvatarUrl();
        this.birthday = profile.getBirthday();
        this.gender = profile.getGender();
    }

}
