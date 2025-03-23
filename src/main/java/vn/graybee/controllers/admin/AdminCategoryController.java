package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import vn.graybee.projections.admin.category.SubCategorySummaryProject;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.services.categories.SubCategoryServices;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    private final SubCategoryServices subCategoryServices;

    public AdminCategoryController(CategoryService categoryService, SubCategoryServices subCategoryServices) {
        this.categoryService = categoryService;
        this.subCategoryServices = subCategoryServices;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(categoryService.fetchCategories_ADMIN());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> getCategoryById(@PathVariable("id") int id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    //
//    @GetMapping("/category")
//    public ResponseEntity<MessageResponse> findProductsByName(@RequestParam("name") String name) {
//        List<ProductResponseByCategoryName> products = productService.findProductsByCategoryName(name);
//        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
//                "200", "Products by category name: ", products
//        ));
//    }
//
    @PostMapping
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteCategoryById(@RequestParam("id") int id) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<CategoryResponse>> updateCategory(@RequestParam("id") int id, @RequestBody @Valid CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<BasicMessageResponse<List<SubCategorySummaryProject>>> fetchSubCategoryByCategoryId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(subCategoryServices.findByCategoryId(id));
    }

}
