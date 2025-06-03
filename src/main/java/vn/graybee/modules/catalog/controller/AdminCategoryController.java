package vn.graybee.modules.catalog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.ChildrenIdAndParentId;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;
import vn.graybee.modules.catalog.service.CategoryService;

import java.util.List;


@RestController
@RequestMapping("${api.adminApi.categories}")
public class AdminCategoryController {

    private final CategoryService categoryService;


    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryDto>>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategoryDtoForDashboard());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CategorySimpleDto>> create(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Long>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<BasicMessageResponse<List<Long>>> deleteByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(categoryService.deleteByIds(ids));
    }

    @PutMapping("/relation/{parentId}/{childrenId}")
    public ResponseEntity<BasicMessageResponse<ChildrenIdAndParentId>> removeChildrenByParentIdAndChildrenId(
            @PathVariable("parentId") Long parentId,
            @PathVariable("childrenId") Long childrenId) {
        return ResponseEntity.ok(categoryService.removeChildrenByParentIdAndChildrenId(parentId, childrenId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategorySimpleDto>> update(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusDto>> updateStatusById(@PathVariable("id") Long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(categoryService.updateStatusById(id, status));
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> restoreById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetail userDetail) {
        return ResponseEntity.ok(categoryService.restoreById(id, userDetail));
    }


}
