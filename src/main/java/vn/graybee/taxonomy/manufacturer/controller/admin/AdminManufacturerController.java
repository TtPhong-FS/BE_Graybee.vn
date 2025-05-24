package vn.graybee.taxonomy.manufacturer.controller.admin;

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
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerCreateRequest;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerUpdateRequest;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerDto;
import vn.graybee.taxonomy.manufacturer.service.ManufacturerService;

import java.util.List;


@RestController
@RequestMapping("${api.manufacturers}")
public class AdminManufacturerController {

    private final ManufacturerService manufacturerService;

    public AdminManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<ManufacturerDto>> create(@RequestBody @Valid ManufacturerCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                manufacturerService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ManufacturerDto>> update(@PathVariable("id") Integer id, @RequestBody @Valid ManufacturerUpdateRequest request) {
        return ResponseEntity.ok(manufacturerService.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<UpdateStatusDto>> updateStatusById(@PathVariable("id") Integer id, @PathVariable("status") String status) {
        return ResponseEntity.ok(manufacturerService.updateStatusById(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(manufacturerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ManufacturerDto>> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ManufacturerDto>>> findAll() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

}
