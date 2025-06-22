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

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String phone;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;


}
