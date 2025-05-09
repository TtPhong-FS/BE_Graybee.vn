package vn.graybee.services.auth;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Permission;
import vn.graybee.requests.auth.PermissionRequest;

import java.util.List;

public interface PermissionService {

    BasicMessageResponse<List<Permission>> findAll();

    BasicMessageResponse<Permission> create(PermissionRequest request);

    BasicMessageResponse<Permission> update(int id, PermissionRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<Permission> findById(int id);

}
