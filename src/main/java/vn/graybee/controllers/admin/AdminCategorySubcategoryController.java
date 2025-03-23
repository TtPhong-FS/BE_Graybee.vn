package vn.graybee.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.serviceImps.categories.CategorySubCategoryServicesImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/categories-subcategories")
public class AdminCategorySubcategoryController {

    private final CategorySubCategoryServicesImpl services;

    public AdminCategorySubcategoryController(CategorySubCategoryServicesImpl services) {
        this.services = services;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<CategorySubcategoryIdResponse>> deleteBySubcategoryIdAndCategoryId(@RequestParam("categoryId") int categoryId, @RequestParam("subcategoryId") int subcategoryId) {
        return ResponseEntity.ok(services.deleteSubcategoryByCategoryId(categoryId, subcategoryId));
    }

}
