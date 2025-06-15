package vn.graybee.modules.permission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionForUpdateResponse {

    private int id;

    private String name;

    private String description;

    private boolean isActive;

}
