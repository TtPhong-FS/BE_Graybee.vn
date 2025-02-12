package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.business.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;
import vn.graybee.services.business.CategoryService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<CategoryProjection>>> getCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        MessageResponse<List<CategoryProjection>> response = categoryService.getCategories(page, size, sortBy, order);
        return ResponseEntity.ok(response);
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
        CategoryResponse savedCategory = categoryService.insertCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BasicMessageResponse<>(201, "Create category successfully", savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> createCategory(@PathVariable long id) {
        Category savedCategory = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(savedCategory);
    }

//    @DeleteMapping("/delete-category")
//    public ResponseEntity<MessageResponse> deleteCategoryById(@RequestParam("id") long id) {
//        categoryService.deleteCategoryById(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Delete category successfully", null));
//    }

//    @PutMapping("/update-category_status-delete")
//    public ResponseEntity<MessageResponse> updateStatusDeleteRecord(@RequestParam("id") long id) {
//        categoryService.updateStatusDeleteRecord(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Update status delete for Category with id = " + id + " successful", null));
//    }


}
