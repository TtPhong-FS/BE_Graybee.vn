package vn.graybee.modules.permission.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.permission.dto.request.PermissionRequest;
import vn.graybee.modules.permission.model.Permission;
import vn.graybee.modules.permission.repository.PermissionRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final MessageSourceUtil messageSourceUtil;


    @Override
    public BasicMessageResponse<List<Permission>> findAll() {
        List<Permission> response = permissionRepository.findAll();
        String message = response.isEmpty() ? messageSourceUtil.get("") : messageSourceUtil.get("auth");

        return new BasicMessageResponse<>(200, message, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Permission> create(PermissionRequest request) {

        if (permissionRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("permission_name_exists"));
        }

        Permission permission = new Permission();

        permission.setName(request.getName().toUpperCase());
        permission.setActive(request.isActive());
        permission.setDescription(request.getDescription());

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(201, messageSourceUtil.get(""), permission);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Permission> update(int id, PermissionRequest request) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("")));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByNameNotId(request.getName(), permission.getId())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get(""));
        }

        permission.setName(request.getName().toUpperCase());
        permission.setDescription(request.getDescription());
        permission.setActive(request.isActive());

        permission.setUpdatedAt(LocalDateTime.now());

        permission = permissionRepository.save(permission);

        return new BasicMessageResponse<>(200, messageSourceUtil.get(""), permission);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        permissionRepository.existsById(id);

        permissionRepository.deleteById(id);

        return new BasicMessageResponse<>(200, messageSourceUtil.get(""), id);
    }

    @Override
    public BasicMessageResponse<Permission> findById(int id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("")));

        return new BasicMessageResponse<>(200, messageSourceUtil.get(""), permission);
    }

}
