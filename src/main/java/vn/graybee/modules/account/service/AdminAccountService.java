package vn.graybee.modules.account.service;

import vn.graybee.modules.account.dto.request.admin.AccountCreateRequest;
import vn.graybee.modules.account.dto.request.admin.ChangeUsernameRequest;
import vn.graybee.modules.account.dto.response.admin.AccountAuthResponse;
import vn.graybee.modules.account.dto.response.admin.AccountIdActiveResponse;
import vn.graybee.modules.account.dto.response.admin.UsernameResponse;

import java.util.List;

public interface AdminAccountService {

    List<AccountAuthResponse> getAllAccountDto();

    AccountIdActiveResponse toggleActiveAccountById(long id);

    AccountAuthResponse createAccount(AccountCreateRequest request);

    void resetPassword(long accountId);

    String changeUsername(long id, ChangeUsernameRequest request);

    UsernameResponse getUsernameById(long id);

    long deleteAccountById(long id);
}
