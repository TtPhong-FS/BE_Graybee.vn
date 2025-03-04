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
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.response.categories.CategoryResponse;
import vn.graybee.response.categories.CategoryStatusResponse;
import vn.graybee.response.categories.SubCategorySummaryResponse;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.services.categories.CategorySubCategoryServices;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategorySubCategoryServices categorySubCategoryServices;

    public CategoryController(CategoryService categoryService, CategorySubCategoryServices categorySubCategoryServices) {
        this.categoryService = categoryService;
        this.categorySubCategoryServices = categorySubCategoryServices;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryProjection>>> getCategories() {

        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Category>> getCategoryById(@PathVariable("id") int id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategoryWithSubCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteCategoryById(@PathVariable("id") int id) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }

    @PutMapping("/{id}/status-delete")
    public ResponseEntity<BasicMessageResponse<CategoryStatusResponse>> updateStatusDeleteRecord(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateStatusDeleteRecord(id));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<BasicMessageResponse<List<SubCategorySummaryResponse>>> fetchSubCategoryByCategoryId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(categorySubCategoryServices.findByCategoryId(id));
    }

}
