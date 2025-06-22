package vn.graybee.modules.account.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private long accountId;

    private String fullName;

    private String email;

    private int totalOrders;

    private double totalSpent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastOrderAt;

}
