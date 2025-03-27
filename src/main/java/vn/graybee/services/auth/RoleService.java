package vn.graybee.services.auth;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.auth.RoleCreateRequest;
import vn.graybee.requests.auth.RoleUpdateRequest;
import vn.graybee.response.admin.auth.RolePermissionIdResponse;
import vn.graybee.response.admin.auth.RoleResponse;

import java.util.List;

public interface RoleService {

    BasicMessageResponse<List<RoleResponse>> fetchAll();

    BasicMessageResponse<RoleResponse> create(RoleCreateRequest request);

    BasicMessageResponse<RoleResponse> update(int id, RoleUpdateRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<RoleResponse> getById(int id);

    BasicMessageResponse<RolePermissionIdResponse> deleteRelationByRoleIdAndPermissionId(int roleId, int permissionId);

}
