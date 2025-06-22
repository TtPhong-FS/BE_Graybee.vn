package vn.graybee.modules.permission.service;

import vn.graybee.modules.permission.dto.request.PermissionRequest;
import vn.graybee.modules.permission.dto.response.PermissionForUpdateResponse;
import vn.graybee.modules.permission.model.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getAllPermission();

    Permission createPermission(PermissionRequest request);

    Permission updatePermissionById(int id, PermissionRequest request);

    Integer deletePermissionById(int id);

    PermissionForUpdateResponse findPermissionForUpdateById(int id);

    List<Permission> checkExistsByNames(List<String> permissions);

}
