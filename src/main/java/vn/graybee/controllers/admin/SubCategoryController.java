package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;
import vn.graybee.services.categories.SubCategoryServices;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/sub_categories")
public class SubCategoryController {

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryServices subCategoryServices;

    public SubCategoryController(SubCategoryRepository subCategoryRepository, SubCategoryServices subCategoryServices) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryServices = subCategoryServices;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<SubCategory>> createSubCategory_V1(@RequestBody @Valid SubCategoryCreateRequest request) {
        return ResponseEntity.ok(subCategoryServices.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<SubCategory>> updateSubCategory_V1(@PathVariable int id, @RequestBody @Valid SubCategoryUpdateRequest request) {
        return ResponseEntity.ok(subCategoryServices.update(id, request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<SubCategory>> findSubCategoryById_V1(@PathVariable int id) {
        return ResponseEntity.ok(subCategoryRepository.findById(id));
    }


}
