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
import vn.graybee.models.directories.Tag;
import vn.graybee.requests.directories.TagRequest;
import vn.graybee.services.categories.TagServices;

import java.util.List;


@RestController
@RequestMapping("${api.tags}")
public class AdminTagController {

    private final TagServices tagServices;

    public AdminTagController(TagServices tagServices) {
        this.tagServices = tagServices;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Tag>>> findAll() {
        return ResponseEntity.ok(tagServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Tag>> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(tagServices.findById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<Tag>> create(@RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagServices.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<Tag>> update(@RequestParam("id") int id, @RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagServices.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(tagServices.delete(id));
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<BasicMessageResponse<List<Integer>>> deleteByIds(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(tagServices.deleteByIds(ids));
    }

}
