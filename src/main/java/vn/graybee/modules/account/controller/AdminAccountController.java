package vn.graybee.modules.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.dto.request.admin.AccountCreateRequest;
import vn.graybee.modules.account.dto.request.admin.ChangeUsernameRequest;
import vn.graybee.modules.account.dto.response.admin.AccountAuthResponse;
import vn.graybee.modules.account.dto.response.admin.AccountIdActiveResponse;
import vn.graybee.modules.account.dto.response.admin.UsernameResponse;
import vn.graybee.modules.account.service.AdminAccountService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.admin.accounts}")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<AccountAuthResponse>>> getAllAccountDto() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminAccountService.getAllAccountDto(),
                        "Lấy danh sách tài khoản người dùng thành công"
                )
        );
    }

    @GetMapping("/username/{id}")
    public ResponseEntity<BasicMessageResponse<UsernameResponse>> getUsernameById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminAccountService.getUsernameById(id),
                        null
                )
        );
    }


    @PutMapping("/active/{id}")
    public ResponseEntity<BasicMessageResponse<AccountIdActiveResponse>> toggleActive(
            @PathVariable long id
    ) {

        AccountIdActiveResponse account = adminAccountService.toggleActiveAccountById(id);

        final String msg = account.isActive() ? "Đã kích hoạt tài khoản người dùng thành công" : "Đã vô hiệu hoá tài khoản người dùng thành công";
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        account,
                        msg
                )
        );
    }


    @PostMapping
    public ResponseEntity<BasicMessageResponse<AccountAuthResponse>> createAccount(
            @RequestBody @Valid AccountCreateRequest request
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminAccountService.createAccount(request),
                        "Tạo tài khoản người dùng mới thành công"
                )
        );
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<BasicMessageResponse<?>> resetPassword(
            @PathVariable long id
    ) {
        adminAccountService.resetPassword(id);
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        null,
                        "Mật khẩu mới đã được gửi đến Email của người dùng"
                )
        );
    }

    @PutMapping("/change/username/{id}")
    public ResponseEntity<BasicMessageResponse<String>> changeUsername(
            @PathVariable long id,
            @RequestBody @Valid ChangeUsernameRequest request
    ) {

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        adminAccountService.changeUsername(id, request),
                        "Tên đăng nhập đã được cập nhật thành công"
                )
        );
    }

}
