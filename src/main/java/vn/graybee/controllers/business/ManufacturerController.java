package vn.graybee.controllers.business;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.services.business.ManufacturerService;

@RestController
@RequestMapping("/api/v1/admin/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createManufacturer(@RequestBody @Valid ManufacturerCreateRequest request) {
        Manufacturer savedmanufacturer = manufacturerService.insertManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MessageResponse("201", "Create Manufacturer successfully", savedmanufacturer)
        );
    }

    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteManufacturerById(@RequestParam("id") long id) {
        manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MessageResponse("200", "Delete Manufacturer successfully", null)
        );
    }

    @PutMapping("")
    public ResponseEntity<MessageResponse> updateStatusDeleteRecord(@RequestParam("id") long id) {
        manufacturerService.updateStatusDeleteRecord(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("200", "Update status delete for Manufacturer with id = " + id + " successful", null));
    }

}
