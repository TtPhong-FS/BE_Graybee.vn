package vn.graybee.modules.account.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private Gender gender;

    public ProfileResponse(Profile profile) {
        this.fullName = profile.getFullName();
        this.phone = profile.getPhone();
        this.birthday = profile.getBirthday();
        this.gender = profile.getGender();
    }

}
