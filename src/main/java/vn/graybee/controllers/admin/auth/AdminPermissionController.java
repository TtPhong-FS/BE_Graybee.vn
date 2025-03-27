package vn.graybee.controllers.admin.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import vn.graybee.projections.admin.auth.PermissionProjection;
import vn.graybee.requests.auth.PermissionCreateRequest;
import vn.graybee.requests.auth.PermissionUpdateRequest;
import vn.graybee.response.admin.auth.PermissionResponse;
import vn.graybee.services.auth.PermissionService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/permissions")
public class AdminPermissionController {

    private final PermissionService permissionService;

    public AdminPermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<PermissionProjection>>> fetchAll() {
        return ResponseEntity.ok(permissionService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<PermissionProjection>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<PermissionResponse>> create(@RequestBody @Valid PermissionCreateRequest request) {
        return ResponseEntity.ok(permissionService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<PermissionResponse>> update(@RequestParam("id") int id, @RequestBody @Valid PermissionUpdateRequest request) {
        return ResponseEntity.ok(permissionService.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(permissionService.delete(id));
    }

}
