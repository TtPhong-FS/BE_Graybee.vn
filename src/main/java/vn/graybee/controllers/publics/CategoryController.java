package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.publics.sidebar.SidebarDto;
import vn.graybee.services.categories.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/sidebar")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<SidebarDto>>> getSidebar() {
        return ResponseEntity.ok(categoryService.getSidebar());
    }

}
