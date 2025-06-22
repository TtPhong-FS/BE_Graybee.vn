package vn.graybee.modules.permission.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.permission.dto.request.PermissionRequest;
import vn.graybee.modules.permission.dto.response.PermissionForUpdateResponse;
import vn.graybee.modules.permission.model.Permission;
import vn.graybee.modules.permission.repository.PermissionRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final MessageSourceUtil messageSourceUtil;


    @Override
    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Permission createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("permission_name_exists"));
        }

        Permission permission = new Permission();

        permission.setName(request.getName().toUpperCase());
        permission.setActive(request.isActive());
        permission.setDescription(request.getDescription());

        return permissionRepository.save(permission);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Permission updatePermissionById(int id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Quyền không tồn tại"));

        if (!permission.getName().equals(request.getName()) && permissionRepository.existsByNameNotId(request.getName(), permission.getId())) {
            throw new BusinessCustomException(Constants.Common.name, "Tên quyền đã tồn tại");
        }

        permission.setName(request.getName().toUpperCase());
        permission.setDescription(request.getDescription());
        permission.setActive(request.isActive());

        permission.setUpdatedAt(LocalDateTime.now());

        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer deletePermissionById(int id) {

        permissionRepository.existsById(id);

        permissionRepository.deleteById(id);

        return id;
    }

    @Override
    public PermissionForUpdateResponse findPermissionForUpdateById(int id) {
        return permissionRepository.findPermissionForUpdateById(id).orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Quyền không tồn tại"));
    }

    @Override
    public List<Permission> checkExistsByNames(List<String> names) {
        List<Permission> permissions = permissionRepository.findAllByNames(names);

        Set<String> foundNamesLower = permissions.stream().map(p -> p.getName().trim().toLowerCase()).collect(Collectors.toSet());
        Set<String> notFoundNames = new HashSet<>(names);
        notFoundNames.removeAll(foundNamesLower);

        if (!notFoundNames.isEmpty()) {
            throw new CustomNotFoundException(Constants.Account.permissionNames, "Quyền " + String.join(",", notFoundNames) + " không tồn tại");
        }

        return permissions;
    }


}
