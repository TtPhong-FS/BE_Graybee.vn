package vn.graybee.taxonomy.tag.controller.admin;

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
import vn.graybee.taxonomy.tag.dto.request.TagRequest;
import vn.graybee.taxonomy.tag.model.Tag;
import vn.graybee.taxonomy.tag.service.TagService;

import java.util.List;


@RestController
@RequestMapping("${api.tags}")
public class AdminTagController {

    private final TagService tagService;

    public AdminTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Tag>>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Tag>> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<Tag>> create(@RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagService.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<Tag>> update(@RequestParam("id") int id, @RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagService.update(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<BasicMessageResponse<List<Integer>>> deleteByIds(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(tagService.deleteByIds(ids));
    }

}
