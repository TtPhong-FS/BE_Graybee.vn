package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.TagProjection;
import vn.graybee.requests.categories.TagCreateRequest;
import vn.graybee.response.categories.TagResponse;
import vn.graybee.services.categories.TagServices;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/tags")
public class AdminTagController {

    private final TagServices tagServices;

    public AdminTagController(TagServices tagServices) {
        this.tagServices = tagServices;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<TagProjection>>> fetchAll() {
        return ResponseEntity.ok(tagServices.fetchAll());
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<TagResponse>> create(@RequestBody @Valid TagCreateRequest request) {
        return ResponseEntity.ok(tagServices.create(request));
    }

}
