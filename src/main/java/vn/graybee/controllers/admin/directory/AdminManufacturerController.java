package vn.graybee.controllers.admin.directory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
import vn.graybee.services.categories.ManufacturerService;

import java.util.List;


@RestController
@RequestMapping("${api.manufacturers}")
public class AdminManufacturerController {

    private final ManufacturerService manufacturerService;

    public AdminManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> create(@RequestBody @Valid ManufacturerCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                manufacturerService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> update(@PathVariable("id") Integer id, @RequestBody @Valid ManufacturerUpdateRequest request) {
        return ResponseEntity.ok(manufacturerService.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusResponse>> updateStatusById(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        return ResponseEntity.ok(manufacturerService.updateStatusById(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(manufacturerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ManufacturerResponse>>> findAll() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

}
