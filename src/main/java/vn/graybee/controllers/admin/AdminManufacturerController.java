package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
import vn.graybee.services.categories.ManufacturerService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/manufacturers")
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

    @PutMapping("/update")
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> update(@RequestParam("id") int id, @RequestBody @Valid ManufacturerUpdateRequest request) {
        return ResponseEntity.ok(manufacturerService.update(id, request));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(manufacturerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(manufacturerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ManufacturerProjection>>> getAllManufacturers() {
        BasicMessageResponse<List<ManufacturerProjection>> manufacturers = manufacturerService.getAllManufacturer();
        return ResponseEntity.ok(manufacturers);
    }

//    @PostMapping("/add-list")
//    public ResponseEntity<BasicMessageResponse<List<ManufacturerResponse>>> createManufacturers(@RequestBody @Valid List<ManufacturerCreateRequest> request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                manufacturerService.createManufacturers(request));
//    }
}
