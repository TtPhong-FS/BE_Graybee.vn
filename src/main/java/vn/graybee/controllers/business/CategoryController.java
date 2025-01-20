package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.models.business.Category;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;
import vn.graybee.services.business.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/get_category{id}")
    public ResponseEntity<Optional<Category>> findCategoryById(@PathParam("id") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryCreateRequest request) {
        categoryService.insertCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created");
    }

}
