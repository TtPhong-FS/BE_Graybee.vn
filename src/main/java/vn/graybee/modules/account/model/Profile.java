package vn.graybee.modules.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.graybee.modules.account.enums.Gender;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "full_name")
    private String fullName;

    private String phone;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "birth_day")
    private Date birthDay;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
