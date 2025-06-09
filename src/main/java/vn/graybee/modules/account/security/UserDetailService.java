package vn.graybee.modules.account.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.common.Constants;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.response.AccountPrincipal;
import vn.graybee.modules.account.repository.AccountRepository;
import vn.graybee.modules.permission.repository.AccountPermissionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final AccountPermissionRepository accountPermissionRepository;

    public UserDetailService(AccountRepository accountRepository, MessageSourceUtil messageSourceUtil, AccountPermissionRepository accountPermissionRepository) {
        this.accountRepository = accountRepository;
        this.messageSourceUtil = messageSourceUtil;
        this.accountPermissionRepository = accountPermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {

        AccountPrincipal user = accountRepository.findByUid(uid)
                .orElseThrow(() -> new AuthException(Constants.Common.root, messageSourceUtil.get("auth.user.invalid_credentials")));

        if (user.isSuperAdmin()) {
            return new UserDetail(user);
        }

        if (!user.isActive()) {
            throw new AuthException(Constants.Common.root, messageSourceUtil.get("auth.account_locked"));
        }


        List<String> permissions = getPermissions(user.getId());
        user.setPermissions(permissions);

        return new UserDetail(user);
    }

    private List<String> getPermissions(Long accountId) {

        Set<String> uniquePermissions = accountPermissionRepository.getPermissionByAccountUid(accountId, true);

        return new ArrayList<>(uniquePermissions);
    }

}
