package vn.graybee.controllers.admin.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.auth.RoleCreateRequest;
import vn.graybee.requests.auth.RoleUpdateRequest;
import vn.graybee.response.admin.auth.RolePermissionIdResponse;
import vn.graybee.response.admin.auth.RoleResponse;
import vn.graybee.services.auth.RoleService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/roles")
public class AdminRoleController {

    private final RoleService roleService;

    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<RoleResponse>>> fetchAll() {
        return ResponseEntity.ok(roleService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<RoleResponse>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<RoleResponse>> create(@RequestBody @Valid RoleCreateRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<RoleResponse>> update(@RequestParam("id") int id, @RequestBody @Valid RoleUpdateRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(roleService.delete(id));
    }

    @DeleteMapping("/permissions/delete")
    public ResponseEntity<BasicMessageResponse<RolePermissionIdResponse>> deleteRelation(@RequestParam("roleId") int roleId, @RequestParam("permissionId") int permissionId) {
        return ResponseEntity.ok(roleService.deleteRelationByRoleIdAndPermissionId(roleId, permissionId));
    }

}
