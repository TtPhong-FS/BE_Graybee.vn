package vn.graybee.services.auth;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Permission;
import vn.graybee.requests.auth.PermissionCreateRequest;
import vn.graybee.requests.auth.PermissionUpdateRequest;

import java.util.List;

public interface PermissionService {

    BasicMessageResponse<List<Permission>> findAll();

    BasicMessageResponse<Permission> create(PermissionCreateRequest request);

    BasicMessageResponse<Permission> update(int id, PermissionUpdateRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<Permission> findById(int id);

}
