package vn.graybee.serviceImps.users;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantUser;
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
                .orElseThrow(() -> new UsernameNotFoundException(ConstantUser.does_not_exists));

        if (!user.isUserActive()) {
            throw new DisabledException(ConstantAuth.account_locked);
        }

        if (user.isSuperAdmin()) {
            user.setRoleName("SUPER_ADMIN");
            return new UserPrincipal(user);
        }

        if (user.getRoleId() == null) {
            throw new BusinessCustomException(
                    ConstantGeneral.general, ConstantAuth.no_role_assigned);
        }

        List<String> permissions = getPermissions(user.getId(), user.getRoleId());
        user.setPermissions(permissions);

        return new UserPrincipal(user);
    }

    private List<String> getPermissions(Integer userId, Integer roleId) {
        Set<String> permissionSet = new LinkedHashSet<>();

        if (roleId != null) {
            List<String> rolePermissions = rolePermissionRepository.getPermissionOfRoleByRoleId(roleId);
            permissionSet.addAll(rolePermissions);
        }

        List<String> userPermissions = userPermissionRepository.getPermissionOfUserByUserId(userId);
        permissionSet.addAll(userPermissions);

        return new ArrayList<>(permissionSet);
    }


}
