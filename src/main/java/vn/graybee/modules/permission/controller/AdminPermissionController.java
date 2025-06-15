package vn.graybee.modules.permission.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.permission.dto.request.PermissionRequest;
import vn.graybee.modules.permission.dto.response.PermissionForUpdateResponse;
import vn.graybee.modules.permission.model.Permission;
import vn.graybee.modules.permission.service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("${api.adminApi.permissions}")
public class AdminPermissionController {

    private final PermissionService permissionService;

    public AdminPermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Permission>>> fetchAll() {
        List<Permission> permissions = permissionService.getAllPermission();

        final String msg = permissions.isEmpty() ? "Danh sách quyền đang trống!" : "Lấy tất cả quyền thành công";
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        permissions, msg
                )
        );
    }

    @GetMapping("/for-update/{id}")
    public ResponseEntity<BasicMessageResponse<PermissionForUpdateResponse>> findPermissionForUpdateById(@PathVariable("id") int id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        permissionService.findPermissionForUpdateById(id),
                        null
                )
        );
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<Permission>> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        permissionService.createPermission(request),
                        "Tạo quyền thành công"
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Permission>> updatePermissionById(@PathVariable("id") int id, @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        permissionService.updatePermissionById(id, request),
                        "Cập nhật quyền thành công"
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> deletePermissionById(@PathVariable("id") int id) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        permissionService.deletePermissionById(id),
                        "Xoá quyền thành công"
                )
        );
    }

}
