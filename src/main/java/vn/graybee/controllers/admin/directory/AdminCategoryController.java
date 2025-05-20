package vn.graybee.controllers.admin.directory;

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
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.services.categories.CategoryService;

import java.util.List;


@RestController
@RequestMapping("${api.categories}")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(categoryService.fetchCategories_ADMIN());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> create(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<BasicMessageResponse<List<Integer>>> deleteByIds(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(categoryService.deleteByIds(ids));
    }

    @DeleteMapping("/{categoryId}/{manufacturerId}")
    public ResponseEntity<BasicMessageResponse<CategoryManufacturerIdResponse>> deleteRelationManufacturer(@PathVariable("categoryId") Integer categoryId, @PathVariable("manufacturerId") Integer manufacturerId) {
        return ResponseEntity.ok(categoryService.deleteRelationByCategoryIdAndManufacturerId(categoryId, manufacturerId));
    }

    @DeleteMapping("/{categoryId}/{subcategoryId}")
    public ResponseEntity<BasicMessageResponse<CategorySubcategoryIdResponse>> deleteRelationSubcategory(@PathVariable("categoryId") Integer categoryId, @PathVariable("subcategoryId") Integer subcategoryId) {
        return ResponseEntity.ok(categoryService.deleteRelationBySubcategoryByCategoryId(categoryId, subcategoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> update(@PathVariable("id") Integer id, @RequestBody @Valid CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusResponse>> updateStatusById(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        return ResponseEntity.ok(categoryService.updateStatusById(id, status));
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> updateStatusById(@PathVariable("id") Integer id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(categoryService.restoreById(id, userPrincipal));
    }


}
