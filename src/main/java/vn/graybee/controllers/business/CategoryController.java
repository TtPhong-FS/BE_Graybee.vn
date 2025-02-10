package vn.graybee.controllers.business;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.MessageResponse;
import vn.graybee.response.CategoryResponse;
import vn.graybee.services.business.CategoryService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<MessageResponse<List<CategoryResponse>>> getCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        MessageResponse<List<CategoryResponse>> response = categoryService.getCategories(page, size, sortBy, order);
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
//    @PostMapping("/add-category")
//    public ResponseEntity<MessageResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
//        CategoryResponse savedCategory = categoryService.insertCategory(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>(201, "Create category successfully", savedCategory));
//    }

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
