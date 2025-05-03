package vn.graybee.serviceImps.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantUser;
import vn.graybee.enums.RolePermissionStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.models.users.UserPrincipalDto;
import vn.graybee.repositories.auths.RolePermissionRepository;
import vn.graybee.repositories.auths.UserPermissionRepository;
import vn.graybee.repositories.users.UserRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final UserPermissionRepository userPermissionRepository;

    public UserDetailServiceImpl(UserRepository userRepository, RolePermissionRepository rolePermissionRepository, UserPermissionRepository userPermissionRepository) {
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrincipalDto user = userRepository.findByUserName(username)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.does_not_exists));

        if (user.isSuperAdmin()) {
            user.setROLE_NAME("SUPER_ADMIN");
            return new UserPrincipal(user);
        }

        if (user.getStatus() == null || user.getStatus().equals(RolePermissionStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.role_inactive);
        }

        List<String> rolePermissions = rolePermissionRepository.getPermissionOfRoleByRoleId(user.getRoleId());
        List<String> userPermissions = userPermissionRepository.getPermissionOfUserByUserId(user.getId());

        Set<String> allPermission = new LinkedHashSet<>();
        if (!rolePermissions.isEmpty()) {
            allPermission.addAll(rolePermissions);
        }
        if (!userPermissions.isEmpty()) {
            allPermission.addAll(userPermissions);
        }

        if (!allPermission.isEmpty()) {
            List<String> permissions = new ArrayList<>(allPermission);
            user.setPermissions(permissions);
        }

        return new UserPrincipal(user);
    }

}
