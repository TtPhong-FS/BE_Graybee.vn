package vn.graybee.serviceImps.auth;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Permission;
import vn.graybee.projections.admin.auth.PermissionProjection;
import vn.graybee.repositories.auths.PermissionRepository;
import vn.graybee.requests.auth.PermissionCreateRequest;
import vn.graybee.requests.auth.PermissionUpdateRequest;
import vn.graybee.response.admin.auth.PermissionResponse;
import vn.graybee.response.admin.auth.PermissionUserCountResponse;
import vn.graybee.services.auth.PermissionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public BasicMessageResponse<List<PermissionProjection>> fetchAll() {
        List<PermissionProjection> response = permissionRepository.fetchAll();
        if (response.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, response);
        }
        return new BasicMessageResponse<>(200, ConstantAuth.success_fetch_permissions, response);
    }

    @Override
    public BasicMessageResponse<PermissionResponse> create(PermissionCreateRequest request) {

        if (permissionRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        Permission permission = new Permission(request.getName().toUpperCase(), "ACTIVE");
        permission.setDescription(request.getDescription());
        permission.setUserCount(0);

        permission = permissionRepository.save(permission);

        PermissionResponse response = new PermissionResponse(permission);
        return new BasicMessageResponse<>(201, ConstantAuth.success_create_permission, response);
    }

    @Override
    public BasicMessageResponse<PermissionResponse> update(int id, PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByNameNotId(request.getName(), permission.getId())) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        permission.setName(request.getName().toUpperCase());
        permission.setDescription(request.getDescription());
        permission.setUpdatedAt(LocalDateTime.now());

        permission = permissionRepository.save(permission);

        PermissionResponse response = new PermissionResponse(permission);

        return new BasicMessageResponse<>(200, ConstantAuth.success_update_permission, response);
    }

    @Override
    public BasicMessageResponse<Integer> delete(int id) {
        PermissionUserCountResponse permission = permissionRepository.getUserCountBeforeDelete(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        if (permission.getUserCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.users_in_use);
        }

        permissionRepository.deleteById(permission.getId());
        return new BasicMessageResponse<>(200, ConstantAuth.success_delete_permission, permission.getId());
    }

    @Override
    public BasicMessageResponse<PermissionProjection> getById(int id) {
        PermissionProjection permission = permissionRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        return new BasicMessageResponse<>(200, ConstantAuth.success_permission_find_by_id, permission);
    }

}
