package vn.graybee.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.ManufacturerProjection;
import vn.graybee.requests.categories.ManufacturerCreateRequest;
import vn.graybee.response.categories.ManufacturerResponse;
import vn.graybee.services.categories.ManufacturerService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ManufacturerProjection>>> getAllManufacturers() {
        BasicMessageResponse<List<ManufacturerProjection>> manufacturers = manufacturerService.getAllManufacturer();
        return ResponseEntity.ok(manufacturers);
    }


    @PostMapping
    public ResponseEntity<BasicMessageResponse<ManufacturerResponse>> create(@RequestBody @Valid ManufacturerCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                manufacturerService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteManufacturerById(@PathVariable("id") int id) {
        return ResponseEntity.ok(manufacturerService.deleteById(id));
    }

//    @PutMapping("/update-manufacturer")
//    public ResponseEntity<MessageResponse> updateStatusDeleteRecord(@RequestParam("id") long id) {
//        manufacturerService.updateStatusDeleteRecord(id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Update status delete for Manufacturer with id = " + id + " successful", null));
//    }

}
