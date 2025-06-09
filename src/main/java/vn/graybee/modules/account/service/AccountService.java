package vn.graybee.modules.account.service;

import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.modules.account.model.Account;

public interface AccountService {

    void isAccountExistById(Long accountId);

    void updatePasswordById(Long id, String password);

    Account saveAccount(CustomerRegisterRequest request);

    AccountAuthDto getAccountAuthDtoByEmail(String email);

    Long getAccountIdByEmail(String email);

    void checkExistsByEmail(String email);

}
