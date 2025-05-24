package vn.graybee.permission.controller;

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
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.permission.dto.request.PermissionRequest;
import vn.graybee.permission.model.Permission;
import vn.graybee.permission.service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/permissions")
public class AdminPermissionController {

    private final PermissionService permissionService;

    public AdminPermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Permission>>> fetchAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Permission>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(permissionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<Permission>> create(@RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(permissionService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<Permission>> update(@RequestParam("id") int id, @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(permissionService.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(permissionService.delete(id));
    }

}
