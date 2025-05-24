package vn.graybee.permission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantAuth;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.permission.dto.request.PermissionRequest;
import vn.graybee.permission.dto.response.PermissionUserCountDto;
import vn.graybee.permission.model.Permission;
import vn.graybee.permission.repository.PermissionRepository;

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
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Permission> create(PermissionRequest request) {

        if (permissionRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        Permission permission = new Permission();

        permission.setName(request.getName().toUpperCase());
        permission.setActive(request.isActive());
        permission.setDescription(request.getDescription());
        permission.setUserCount(0);

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(201, ConstantAuth.success_create_permission, permission);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Permission> update(int id, PermissionRequest request) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_does_not_exists));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByNameNotId(request.getName(), permission.getId())) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.permission_name_exists);
        }

        permission.setName(request.getName().toUpperCase());
        permission.setDescription(request.getDescription());
        permission.setActive(request.isActive());

        permission.setUpdatedAt(LocalDateTime.now());

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(200, ConstantAuth.success_update_permission, permission);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        PermissionUserCountDto permission = permissionRepository.getUserCountBeforeDelete(id)
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
