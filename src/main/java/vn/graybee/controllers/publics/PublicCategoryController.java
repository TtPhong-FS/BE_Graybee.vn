package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.services.categories.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    public PublicCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryBasicInfoProjection>>> getCategories() {
        BasicMessageResponse<List<CategoryBasicInfoProjection>> response = categoryService.getCategories_public();
        return ResponseEntity.ok(response);
    }

}
