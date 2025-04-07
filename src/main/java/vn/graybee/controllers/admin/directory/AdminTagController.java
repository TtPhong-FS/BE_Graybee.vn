package vn.graybee.controllers.admin.directory;

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
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.category.TagProjection;
import vn.graybee.requests.directories.TagRequest;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.services.categories.TagServices;

import java.util.List;


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

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<TagResponse>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(tagServices.getById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<TagResponse>> create(@RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagServices.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<TagResponse>> update(@RequestParam("id") int id, @RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagServices.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(tagServices.delete(id));
    }

}
