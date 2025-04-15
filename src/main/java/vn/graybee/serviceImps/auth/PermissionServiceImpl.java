package vn.graybee.serviceImps.auth;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.RolePermissionStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Permission;
import vn.graybee.repositories.auths.PermissionRepository;
import vn.graybee.requests.auth.PermissionCreateRequest;
import vn.graybee.requests.auth.PermissionUpdateRequest;
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
    public BasicMessageResponse<List<Permission>> findAll() {
        List<Permission> response = permissionRepository.findAll();
        String message = response.isEmpty() ? ConstantGeneral.empty_list : ConstantAuth.success_fetch_permissions;
        
        return new BasicMessageResponse<>(200, message, response);
    }

    @Override
    public BasicMessageResponse<Permission> create(PermissionCreateRequest request) {

        if (permissionRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        Permission permission = new Permission();

        permission.setName(request.getName().toUpperCase());
        permission.setStatus(RolePermissionStatus.ACTIVE);
        permission.setDescription(request.getDescription());
        permission.setUserCount(0);

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(201, ConstantAuth.success_create_permission, permission);
    }

    @Override
    public BasicMessageResponse<Permission> update(int id, PermissionUpdateRequest request) {

        RolePermissionStatus status = request.getStatusEnum();

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByNameNotId(request.getName(), permission.getId())) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        permission.setName(request.getName().toUpperCase());
        permission.setDescription(request.getDescription());
        permission.setStatus(status);

        permission.setUpdatedAt(LocalDateTime.now());

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(200, ConstantAuth.success_update_permission, permission);
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
    public BasicMessageResponse<Permission> findById(int id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        return new BasicMessageResponse<>(200, ConstantAuth.success_permission_find_by_id, permission);
    }

}
