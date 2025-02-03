package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.business.Category;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.ProductResponseByCategoryName;
import vn.graybee.services.business.CategoryService;
import vn.graybee.services.business.ProductService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin")
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public ResponseEntity<MessageResponse> getCategories(@RequestParam("limit") int limit, @RequestParam("page") int page) {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
                "200", "List Categories ", categories
        ));
    }

    @GetMapping("/category")
    public ResponseEntity<MessageResponse> findProductsByName(@RequestParam("name") String name) {
        List<ProductResponseByCategoryName> products = productService.findProductsByCategoryName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
                "200", "Products by category name: ", products
        ));
    }

    @PostMapping("/add-category")
    public ResponseEntity<MessageResponse> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        Category savedcategory = categoryService.insertCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("201", "Create category successfully", savedcategory));
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<MessageResponse> deleteCategoryById(@RequestParam("id") long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Delete category successfully", null));
    }

    @PutMapping("/update/status-delete")
    public ResponseEntity<MessageResponse> updateStatusDeleteRecord(@RequestParam("id") long id) {
        categoryService.updateStatusDeleteRecord(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Update status delete for Category with id = " + id + " successful", null));
    }


}
