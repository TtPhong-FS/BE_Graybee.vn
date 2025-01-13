package vn.graybee.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.services.CategoryService;

@RestController
@RequestMapping("${api.categories}")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryCreateRequest request) {
        categoryService.insertCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created");
    }

}
