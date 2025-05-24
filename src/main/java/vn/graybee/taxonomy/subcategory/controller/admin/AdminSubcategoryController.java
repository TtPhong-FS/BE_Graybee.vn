package vn.graybee.taxonomy.subcategory.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryCreateRequest;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryUpdateRequest;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryIdTagIdDto;
import vn.graybee.taxonomy.subcategory.service.SubcategoryService;

import java.util.List;


@RestController
@RequestMapping("${api.subcategories}")
public class AdminSubcategoryController {

    private final SubcategoryService subCategoryService;

    public AdminSubcategoryController(SubcategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<SubcategoryDto>> create(@RequestBody @Valid SubcategoryCreateRequest request) {
        return ResponseEntity.ok(subCategoryService.create(request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<SubcategoryDto>>> fetchAll() {
        return ResponseEntity.ok(subCategoryService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubcategoryDto>> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(subCategoryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubcategoryDto>> update(@PathVariable Integer id, @RequestBody @Valid SubcategoryUpdateRequest request) {

        return ResponseEntity.ok(subCategoryService.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusDto>> updateStatus(@PathVariable Integer id, @PathVariable("status") String status) {
        return ResponseEntity.ok(subCategoryService.updateStatusById(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(subCategoryService.delete(id));
    }

    @DeleteMapping("/{id}/{tagId}")
    public ResponseEntity<BasicMessageResponse<SubcategoryIdTagIdDto>> deleteRelationBySubcategoryIdAndTagId(@PathVariable("id") Integer id, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(subCategoryService.deleteRelationsBySubCategoryIdAndTagId(id, tagId));
    }


}
