package vn.graybee.serviceImps.auth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.RolePermissionStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Role;
import vn.graybee.models.users.RolePermission;
import vn.graybee.repositories.auths.PermissionRepository;
import vn.graybee.repositories.auths.RolePermissionRepository;
import vn.graybee.repositories.auths.RoleRepository;
import vn.graybee.requests.auth.RoleCreateRequest;
import vn.graybee.requests.auth.RoleUpdateRequest;
import vn.graybee.response.admin.auth.PermissionBasicResponse;
import vn.graybee.response.admin.auth.RolePermissionBasicResponse;
import vn.graybee.response.admin.auth.RolePermissionIdResponse;
import vn.graybee.response.admin.auth.RoleResponse;
import vn.graybee.response.admin.auth.RoleUserCountResponses;
import vn.graybee.services.auth.RoleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public BasicMessageResponse<List<RoleResponse>> fetchAll() {
        List<RoleResponse> roles = roleRepository.fetchAll();

        String message = roles.isEmpty() ? ConstantGeneral.empty_list : ConstantAuth.success_fetch_roles;

        List<Integer> roleIds = roles.stream().map(RoleResponse::getId).toList();

        List<RolePermissionBasicResponse> RolePermissionResponse = rolePermissionRepository.findPermissionsByRoleIds(roleIds);

        Map<Integer, List<PermissionBasicResponse>> rolePermissionMap = RolePermissionResponse
                .stream()
                .collect(Collectors.groupingBy(RolePermissionBasicResponse::getId,
                        Collectors.mapping(p -> new PermissionBasicResponse(p.getPermissionId(), p.getPermissionName()), Collectors.toList()
                        )));

        roles.forEach(role -> {
            role.setPermissions(rolePermissionMap.getOrDefault(role.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, message, roles);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<RoleResponse> create(RoleCreateRequest request) {

        if (roleRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.role_name_exists);
        }

        List<PermissionBasicResponse> permissions = permissionRepository.findByIds(request.getPermissions());
        Set<Integer> foundPermissionIds = permissions.stream().map(PermissionBasicResponse::getId).collect(Collectors.toSet());

        if (!foundPermissionIds.containsAll(request.getPermissions())) {
            throw new BusinessCustomException(ConstantAuth.permissions, ConstantAuth.permission_does_not_exists);
        }

        Role role = new Role();
        role.setName(request.getName().toUpperCase());
        role.setStatus(RolePermissionStatus.ACTIVE);

        role.setUserCount(0);


        role = roleRepository.save(role);

        int roleId = role.getId();

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            List<RolePermission> rolePermission = foundPermissionIds
                    .stream()
                    .map(p -> new RolePermission(roleId, p)).toList();

            if (!rolePermission.isEmpty()) {
                rolePermissionRepository.saveAll(rolePermission);
            }
        }

        RoleResponse response = new RoleResponse(role, permissions);

        return new BasicMessageResponse<>(201, ConstantAuth.success_create_role, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<RoleResponse> update(int id, RoleUpdateRequest request) {

        RolePermissionStatus status = request.getStatusEnum();

        List<PermissionBasicResponse> permissions = permissionRepository.findByIds(request.getPermissions());
        Set<Integer> foundPermissionIds = permissions.stream().map(PermissionBasicResponse::getId).collect(Collectors.toSet());

        if (!foundPermissionIds.containsAll(request.getPermissions())) {
            throw new BusinessCustomException(ConstantAuth.permissions, ConstantAuth.permission_does_not_exists);
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.role_does_not_exists));

        if (!role.getName().equals(request.getName()) && roleRepository.existsByNameNotId(request.getName(), role.getId())) {
            throw new BusinessCustomException(ConstantAuth.name, ConstantAuth.role_name_exists);
        }

        role.setStatus(status);
        role.setName(request.getName().toUpperCase());
        role.setUpdatedAt(LocalDateTime.now());

        role = roleRepository.save(role);

        int roleId = role.getId();

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {

            List<Integer> currentPermissionIds = rolePermissionRepository.findPermissionIdsByRoleId(roleId);

            Set<Integer> newPermissionIds = new HashSet<>(request.getPermissions());
            rolePermissionRepository.deleteByRoleIdAndPermissionIdNotIn(roleId, new ArrayList<>(newPermissionIds));

            List<RolePermission> rolePermission = newPermissionIds
                    .stream()
                    .filter(p -> !currentPermissionIds.contains(p))
                    .map(p -> new RolePermission(roleId, p)).toList();

            if (!rolePermission.isEmpty()) {
                rolePermissionRepository.saveAll(rolePermission);

            }
        }

        RoleResponse response = new RoleResponse(role, permissions);

        return new BasicMessageResponse<>(200, ConstantAuth.success_update_role, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {
        RoleUserCountResponses role = roleRepository.getUserCountBeforeDelete(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.role_does_not_exists));

        if (role.getUserCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.users_in_use);
        }

        roleRepository.deleteById(role.getId());
        return new BasicMessageResponse<>(200, ConstantAuth.success_delete_role, role.getId());
    }

    @Override
    public BasicMessageResponse<RoleResponse> getById(int id) {
//        RoleResponse role = roleRepository.getById(id)
//                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.role_does_not_exists));
//
//        return new BasicMessageResponse<>(200, ConstantAuth.success_role_find_by_id, role);

        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<RolePermissionIdResponse> deleteRelationByRoleIdAndPermissionId(int roleId, int permissionId) {

        RolePermissionIdResponse response = rolePermissionRepository.findRelationByRoleIdAndPermissionId(roleId, permissionId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.permission_relation_does_not_exists));

        rolePermissionRepository.deleteRelationByRoleIdAndPermissionId(response.getRoleId(), response.getPermissionId());

        return new BasicMessageResponse<>(200, ConstantAuth.success_delete_permission_relation, response);
    }

}
