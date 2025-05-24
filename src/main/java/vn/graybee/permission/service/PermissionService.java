package vn.graybee.permission.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.permission.dto.request.PermissionRequest;
import vn.graybee.permission.model.Permission;

import java.util.List;

public interface PermissionService {

    BasicMessageResponse<List<Permission>> findAll();

    BasicMessageResponse<Permission> create(PermissionRequest request);

    BasicMessageResponse<Permission> update(int id, PermissionRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<Permission> findById(int id);

}
