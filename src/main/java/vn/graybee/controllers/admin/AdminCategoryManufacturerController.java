package vn.graybee.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryWithManufacturersResponse;
import vn.graybee.services.categories.CategoryManufacturerService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/categories-manufacturers")
public class AdminCategoryManufacturerController {

    private final CategoryManufacturerService categoryManufacturerService;

    public AdminCategoryManufacturerController(CategoryManufacturerService categoryManufacturerService) {
        this.categoryManufacturerService = categoryManufacturerService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryWithManufacturersResponse>>> fetchAll() {
        return ResponseEntity.ok(categoryManufacturerService.fetchAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<CategoryManufacturerIdResponse>> fetchAll(@RequestParam int manufacturerId, @RequestParam("categoryId") int categoryId) {
        return ResponseEntity.ok(categoryManufacturerService.deleteManufacturerByIdAndCategoryById(manufacturerId, categoryId));
    }

}
