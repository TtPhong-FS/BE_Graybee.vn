package vn.graybee.taxonomy.category.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.category.dto.request.CategoryCreateRequest;
import vn.graybee.taxonomy.category.dto.request.CategoryUpdateRequest;
import vn.graybee.taxonomy.category.dto.response.CategoryDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdManufacturerIdDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdSubcategoryIdDto;
import vn.graybee.taxonomy.category.service.CategoryService;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;

import java.util.List;


@RestController
@RequestMapping("${api.categories}")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CategoryDto>>> getCategories() {
        return ResponseEntity.ok(categoryService.fetchCategories_ADMIN());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CategoryDto>> create(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<BasicMessageResponse<List<Integer>>> deleteByIds(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(categoryService.deleteByIds(ids));
    }

    @DeleteMapping("/{id}/{manufacturerId}")
    public ResponseEntity<BasicMessageResponse<CategoryIdManufacturerIdDto>> deleteRelationManufacturer(@PathVariable("id") Integer id, @PathVariable("manufacturerId") Integer manufacturerId) {
        return ResponseEntity.ok(categoryService.deleteRelationByCategoryIdAndManufacturerId(id, manufacturerId));
    }

    @DeleteMapping("/{id}/{subcategoryId}")
    public ResponseEntity<BasicMessageResponse<CategoryIdSubcategoryIdDto>> deleteRelationSubcategory(@PathVariable("id") Integer id, @PathVariable("subcategoryId") Integer subcategoryId) {
        return ResponseEntity.ok(categoryService.deleteRelationBySubcategoryByCategoryId(id, subcategoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> update(@PathVariable("id") Integer id, @RequestBody @Valid CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusDto>> updateStatusById(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        return ResponseEntity.ok(categoryService.updateStatusById(id, status));
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<BasicMessageResponse<CategoryDto>> updateStatusById(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetail userDetail) {
        return ResponseEntity.ok(categoryService.restoreById(id, userDetail));
    }


}
