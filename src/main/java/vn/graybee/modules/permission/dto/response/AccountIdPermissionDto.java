package vn.graybee.modules.permission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountIdPermissionDto {

    private long accountId;

    private int permissionId;

    private String permissionName;

}
