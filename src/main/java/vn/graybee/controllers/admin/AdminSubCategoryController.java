package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.directories.SubCategoryCreateRequest;
import vn.graybee.requests.directories.SubCategoryUpdateRequest;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.services.categories.SubCategoryServices;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/subcategories")
public class AdminSubCategoryController {

    private final SubCategoryServices subCategoryServices;

    public AdminSubCategoryController(SubCategoryServices subCategoryServices) {
        this.subCategoryServices = subCategoryServices;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<SubCategoryResponse>> create(@RequestBody @Valid SubCategoryCreateRequest request) {
        return ResponseEntity.ok(subCategoryServices.create(request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<SubCategoryResponse>>> fetchAll() {
        return ResponseEntity.ok(subCategoryServices.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubCategoryResponse>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(subCategoryServices.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<SubCategoryResponse>> update(@RequestParam int id, @RequestBody @Valid SubCategoryUpdateRequest request) {

        return ResponseEntity.ok(subCategoryServices.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(subCategoryServices.delete(id));
    }

    @DeleteMapping("/tags/delete")
    public ResponseEntity<BasicMessageResponse<SubcategoryTagIdResponse>> deleteRelationBySubcategoryIdAndTagId(@RequestParam("subcategoryId") int subcategoryId, @RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(subCategoryServices.deleteRelationsBySubCategoryIdAndTagId(subcategoryId, tagId));
    }


}
