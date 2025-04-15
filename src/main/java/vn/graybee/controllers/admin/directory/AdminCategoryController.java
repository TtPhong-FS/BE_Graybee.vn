package vn.graybee.controllers.admin.directory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.messages.BasicMessageResponse;
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
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> create(@RequestBody @Valid CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @DeleteMapping("/manufacturers/delete")
    public ResponseEntity<BasicMessageResponse<CategoryManufacturerIdResponse>> deleteRelationManufacturer(@RequestParam("categoryId") int categoryId, @RequestParam("manufacturerId") int manufacturerId) {
        return ResponseEntity.ok(categoryService.deleteRelationByCategoryIdAndManufacturerId(categoryId, manufacturerId));
    }

    @DeleteMapping("/subcategories/delete")
    public ResponseEntity<BasicMessageResponse<CategorySubcategoryIdResponse>> deleteRelationSubcategory(@RequestParam("categoryId") int categoryId, @RequestParam("subcategoryId") int subcategoryId) {
        return ResponseEntity.ok(categoryService.deleteRelationBySubcategoryByCategoryId(categoryId, subcategoryId));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> update(@RequestParam("id") int id, @RequestBody @Valid CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PutMapping("/update/status")
    public ResponseEntity<BasicMessageResponse<UpdateStatusResponse>> updateStatusById(@RequestParam("id") int id, @RequestParam("status") DirectoryStatus status) {
        return ResponseEntity.ok(categoryService.updateStatusById(id, status));
    }


}
