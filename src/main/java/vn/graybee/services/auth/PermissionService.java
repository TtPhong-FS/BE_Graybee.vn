package vn.graybee.services.auth;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.auth.PermissionProjection;
import vn.graybee.requests.auth.PermissionCreateRequest;
import vn.graybee.requests.auth.PermissionUpdateRequest;
import vn.graybee.response.admin.auth.PermissionResponse;

import java.util.List;

public interface PermissionService {

    BasicMessageResponse<List<PermissionProjection>> fetchAll();

    BasicMessageResponse<PermissionResponse> create(PermissionCreateRequest request);

    BasicMessageResponse<PermissionResponse> update(int id, PermissionUpdateRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<PermissionProjection> getById(int id);

}
