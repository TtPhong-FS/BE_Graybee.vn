package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.SubCategoryProjection;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;
import vn.graybee.response.categories.SubCategoryResponse;
import vn.graybee.services.categories.SubCategoryServices;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/subcategories")
public class AdminSubCategoryController {

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryServices subCategoryServices;

    public AdminSubCategoryController(SubCategoryRepository subCategoryRepository, SubCategoryServices subCategoryServices) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryServices = subCategoryServices;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<SubCategoryResponse>> createSubCategory_V1(@RequestBody @Valid SubCategoryCreateRequest request) {
        return ResponseEntity.ok(subCategoryServices.create(request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<SubCategoryProjection>>> fetchAll() {
        return ResponseEntity.ok(subCategoryServices.fetchAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubCategoryResponse>> updateSubCategory_V1(@PathVariable int id, @RequestBody @Valid SubCategoryUpdateRequest request) {
        return ResponseEntity.ok(subCategoryServices.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubCategoryProjection>> findSubCategoryById_V1(@PathVariable int id) {
        return ResponseEntity.ok(subCategoryServices.findById(id));
    }


}
